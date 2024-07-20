package com.example.backend.service;

import com.example.backend.models.Invitation;
import com.example.backend.repositories.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationService {
    private final InvitationRepository invitationRepository;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    public Invitation createInvitation(Invitation invitation) {
        // check if title is already used
        if (invitationRepository.existsByTitle(invitation.getTitle())) {
            throw new DataIntegrityViolationException("Event title already used!");
        }

        // check if title is empty
        if (invitation.getTitle().length() < 1) {
            throw new DataIntegrityViolationException("You have to fill in an event name!");
        }

        // create an url link by getting the first 8 chars of an uuid
        String shortUuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        // check if the uuid exists in the database, if not set to shortUrl
        do {
            invitation.setUrl(shortUuid);
        } while (invitationRepository.existsByUrl(shortUuid));


        return invitationRepository.save(invitation);
    }

    public List<Invitation> findAll() {
        return invitationRepository.findAll();
    }

    public Invitation findById(long id) {
        return invitationRepository.findById(id).orElse(null);
    }


    public void deleteAll() {
        invitationRepository.deleteAll();
    }

    public void deleteById(long id) {
        invitationRepository.deleteById(id);
    }

    public boolean validInvitationLink(String url) {
        return invitationRepository.existsByUrl(url);
    }

    public Invitation findByUrl(String url) {
        return invitationRepository.findByUrl(url).orElse(null);
    }





}
