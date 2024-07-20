package com.example.backend.rest;

import com.example.backend.dto.PasswordDto;
import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.ResetPassword;
import com.example.backend.models.User;
import com.example.backend.repositories.ResetPasswordRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/resetPassword")
public class ResetPasswordController {

    @Autowired
    ResetPasswordRepository resetPasswordRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping
    public List<ResetPassword> getAll() {
        return resetPasswordRepository.findAll();
    }

    @PostMapping("/request")
    public ResponseEntity<ResetPassword> requestResetPassword(@RequestBody PasswordDto passwordDto) {

        List<User> users = userRepository.findByQuery("Users_find_by_email", passwordDto.getEmail());
        User user = users.size() == 1 ? users.get(0) : null;

        if (user == null) {
            throw new PreConditionFailedException("Email is not in use.");
        }

        ResetPassword existingResetPassword = user.getResetPassword();
        if (existingResetPassword != null) {
            user.setResetPassword(null);
            userRepository.save(user);
        }

        String urlParam = emailSenderService.generateUrl();

        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setUrl(urlParam);
        resetPasswordRepository.save(resetPassword);
        user.setResetPassword(resetPassword);
        userRepository.save(user);

        String url = passwordDto.getUrl() + urlParam;
        emailSenderService.sendResetPasswordEmail(passwordDto.getEmail(), url);

        return ResponseEntity.ok(resetPassword);
    }

    @PostMapping("/set")
    public ResponseEntity<User> resetPassword(@RequestBody PasswordDto passwordDto) {

        ResetPassword resetPassword = resetPasswordRepository.findByUrl(passwordDto.getUrl());
        if (resetPassword == null) {
            throw new PreConditionFailedException("Url not valid");
        } else {
            User user = userRepository.findByResetPassword(resetPassword);

            String password = passwordDto.getNewPassword();
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
                String hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);
                user.setPassword(hashedPassword);
                user.setResetPassword(null);
                userRepository.save(user);

            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error hashing password", e);
            }

            return ResponseEntity.ok(user);
        }
    }

}
