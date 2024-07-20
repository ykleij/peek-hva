package com.example.backend.rest;

import com.example.backend.models.Invitation;
import com.example.backend.service.EmailSenderService;
import com.example.backend.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invitations")
public class InvitationController {

    private final InvitationService invitationService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping
    public ResponseEntity<Object> createInvitation(@RequestBody Map<String, Object> request) {
        try {
            String title = (String) request.get("title");
            Integer maxUsers = (Integer) request.get("maxUsers");
            Invitation invitation = new Invitation(title, maxUsers);
            Invitation newInvitation = invitationService.createInvitation(invitation);
            return new ResponseEntity<>(newInvitation, HttpStatus.CREATED);
        } catch (Error error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/send-invitation")
    public ResponseEntity<Object> sendInvitationToEmail(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");
            String url = (String) request.get("url");
            System.out.println(email);
            System.out.println(url);
            emailSenderService.sendInvitationEmail(email, url);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Error error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllInvitations() {
        try {
            List<Invitation> invitations = invitationService.findAll();
            return ResponseEntity.ok(invitations);
        } catch (Exception e) {
            return new ResponseEntity<>("Er is een fout opgetreden bij het ophalen van uitnodigingen.", HttpStatus.CONFLICT);

        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Invitation> getInvitationById(@PathVariable long id) {
        Invitation invitation = invitationService.findById(id);
        return ResponseEntity.ok(invitation);
    }

    @GetMapping("/validation/{url}")
    public ResponseEntity<Object> checkUrlValidity(@PathVariable String url) {
        invitationService.validInvitationLink(url);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable long id) {
        invitationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteAll() {
        invitationService.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
