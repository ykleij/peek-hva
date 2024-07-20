package com.example.backend.rest;

import com.example.backend.config.APIConfig;
import com.example.backend.models.Notification;
import com.example.backend.jwt.JWToken;
import com.example.backend.models.User;
import com.example.backend.repositories.NotificationRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.service.NotificationDistributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.NotAcceptableStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    APIConfig apiConfig;

    @Autowired
    NotificationDistributor notificationDistributor;

    @Autowired
    NotificationRepository notificationRepository;

    @PostMapping(path = "/login")
    public ResponseEntity<User> authenticateAccount(@RequestBody Map<String, String> signInInfo) {
        String email = signInInfo.get("email");
        String password = signInInfo.get("password");

        List<User> users = userRepository.findByQuery("Users_find_by_email", email);
        User user = users.size() == 1 ? users.get(0) : null;
        // verify with paramater hash
        if (user == null || !user.verifyPassword(password)) {
            throw new NotAcceptableStatusException("Cannot authenticate account with email=" + email);
        }

        JWToken jwToken = new JWToken(user.getId(), user.getRole());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(), this.apiConfig.getTokenDurationOfValidity());

        Notification loginNotification = new Notification(
                "New User Login",
                String.format("'%s' has just logged in.", user.getEmail()),
                new Date(),
                false
        );
        notificationDistributor.notify("userLogin", loginNotification);
//        notificationRepository.saveNotificationForAllAdmins(loginNotification);

        return ResponseEntity.accepted().header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                .body(user);
    }
}
