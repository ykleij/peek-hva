package com.example.backend.rest;

import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.*;
import com.example.backend.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

//    @GetMapping("/{id}")
//    public Notification getById(@PathVariable long id) {
//        Notification notification = notificationRepository.findById(id);
//
//        if (notification == null) {
//            throw new ResourceNotFoundException("Notification with ID " + id + " not found.");
//        }
//
//        return ResponseEntity.ok(notification).getBody();
//    }

    @GetMapping("/{userId}")
    public List<Notification> getAllByUserId(@PathVariable long userId) {
        User user = this.userRepository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }

        List<Notification> notifications = notificationRepository.findAllByUserId(user.getId());

        if (notifications == null) {
            throw new ResourceNotFoundException("Notifications not found.");
        }

        return ResponseEntity.ok(notifications).getBody();
    }

    @PostMapping("/findByNotification")
    public Notification findNotification(@RequestBody Notification notification) {
        Notification foundNotification = notificationRepository.findByNotificationDetails(notification);

        if (foundNotification == null) {
            throw new ResourceNotFoundException("Notification not found.");
        }

        return ResponseEntity.ok(foundNotification).getBody();
    }


    @PostMapping("/saveForUser/{userId}")
    public ResponseEntity<Notification> saveNotification(@RequestBody Notification notification, @PathVariable long userId) {
        try {
            Notification savedNotification = notificationRepository.saveForUser(notification, userId);

            return ResponseEntity.ok(savedNotification);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        } catch (Exception e) {
            throw new RuntimeException("Error saving notification: " + e.getMessage());
        }
    }

    @PutMapping("/updateBatch")
    public ResponseEntity<List<Notification>> saveNotificationBatch(@RequestBody List<Notification> requestNotifications) {
        try {
            List<Notification> notifications = new ArrayList<>();

            for (Notification notification : requestNotifications) {
                long id = notification.getId();

                if (id != 0) {
                    Notification existingNotification = notificationRepository.findById(id);

                    if (existingNotification != null) {
                        existingNotification.setIsRead(true);
                        notifications.add(existingNotification);

                        notificationRepository.save(existingNotification);
                    }
                }
            }

            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            throw new RuntimeException("Error saving notification batch: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating batch of notifications: " + e.getMessage());
        }
    }
}
