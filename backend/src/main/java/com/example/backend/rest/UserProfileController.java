package com.example.backend.rest;

import com.example.backend.models.Notification;
import com.example.backend.dto.UserRegistrationDTO;
import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.ProjectRole;
import com.example.backend.models.User;
import com.example.backend.models.UserProfile;
import com.example.backend.repositories.NotificationRepository;
import com.example.backend.repositories.ProjectRoleRepository;
import com.example.backend.repositories.UserProfileRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.service.InvitationService;
import com.example.backend.service.LikingService;
import com.example.backend.service.NotificationDistributor;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Transactional
@RequestMapping("/user-profile")
public class UserProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikingService likingService;

    @Autowired
    NotificationDistributor notificationDistributor;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    ProjectRoleRepository projectRoleRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    public UserProfileController() {

    }

    @GetMapping
    public List<UserProfile> getAll() {
        return userProfileRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserProfile getById(@PathVariable long id) {
        UserProfile userProfile = userProfileRepository.findById(id);

        if (userProfile == null) {
            throw new ResourceNotFoundException("UserProfile with ID " + id + " not found.");
        }

        return ResponseEntity.ok(userProfile).getBody();
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> update(@PathVariable long id, @RequestBody UserProfile updatedUser) {
        if (id != updatedUser.getId()) {
            throw new PreConditionFailedException("The ID in the request path does not match the ID in the request body.");
        }

        UserProfile userProfile = userProfileRepository.findById(id);

        if (userProfile == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        UserProfile savedUser = userProfileRepository.save(updatedUser);

        return ResponseEntity.ok(savedUser);
    }
}

