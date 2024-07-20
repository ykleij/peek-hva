package com.example.backend.repositories;

import com.example.backend.models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    boolean existsByUrl(String uuid);
    boolean existsByTitle(String title);

    Optional<Invitation> findByUrl(String url);
}
