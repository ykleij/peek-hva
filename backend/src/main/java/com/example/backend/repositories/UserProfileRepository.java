package com.example.backend.repositories;

import com.example.backend.models.ResetPassword;
import com.example.backend.models.User;
import com.example.backend.models.UserProfile;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class UserProfileRepository implements GenericRepository<UserProfile> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserProfile> findAll() {
        Query query = this.em.createQuery(
                "SELECT u FROM UserProfile u", UserProfile.class
        );
        return query.getResultList();
    }

    @Override
    @Transactional
    public UserProfile findById(long id) {
        return em.find(UserProfile.class, id);
    }

    @Override
    @Transactional
    public UserProfile save(UserProfile user) {
        em.merge(user);
        return user;
    }

    @Override
    public UserProfile deleteById(long id) {
        UserProfile user = this.findById(id);
        em.remove(user);
        return user;
    }
}