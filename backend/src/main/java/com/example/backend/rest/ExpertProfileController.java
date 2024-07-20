package com.example.backend.rest;

import com.example.backend.models.*;
import com.example.backend.dto.UserRegistrationDTO;
import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.repositories.*;
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
@RequestMapping("/expert-profile")
public class ExpertProfileController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRoleRepository projectRoleRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    ExpertProfileRepository expertProfileRepository;

    public ExpertProfileController() {

    }

    @GetMapping
    public List<ExpertProfile> getAll() {
        return expertProfileRepository.findAll();
    }

    @GetMapping("/{id}")
    public ExpertProfile getById(@PathVariable long id) {
        ExpertProfile expertProfile = expertProfileRepository.findById(id);

        if (expertProfile == null) {
            throw new ResourceNotFoundException("ExpertProfile with ID " + id + " not found.");
        }

        return ResponseEntity.ok(expertProfile).getBody();
    }

//    @GetMapping("/user/{id}")
//    public ExpertProfile getByUserId(@PathVariable long id) {
//        ExpertProfile expertProfile = expertProfileRepository.findByUserId(id);
//
//        if (expertProfile == null) {
//            throw new ResourceNotFoundException("User with ID " + id + " not found.");
//        }
//
//        return ResponseEntity.ok(expertProfile).getBody();
//    }

//    @Transactional
//    @PutMapping("/{id}")
//    public ResponseEntity<ExpertProfile> update(@PathVariable long id, @RequestBody ExpertProfile updatedUser) {
//        if (id != updatedUser.getId()) {
//            throw new PreConditionFailedException("The ID in the request path does not match the ID in the request body.");
//        }
//
//        // TODO: FIX DEZE SHIT HIERONDER SWA
//        ExpertProfile expertProfile = expertProfileRepository.findByUserId(updatedUser.getId());
//
//        if (expertProfile == null) {
//            throw new ResourceNotFoundException("Expert with ID " + id + " not found.");
//        }
//
//        ExpertProfile savedUser = expertProfileRepository.save(updatedUser);
//
//        return ResponseEntity.ok(savedUser);
//    }
}


