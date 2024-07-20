package com.example.backend.repositories;

import com.example.backend.models.ResetPassword;
import com.example.backend.models.User;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class UserRepository implements GenericRepository<User> {

    @PersistenceContext
    private EntityManager em;

    public List<User> findByQuery(String queryName, String email) {
        TypedQuery<User> query = this.em.createNamedQuery(queryName, User.class);
        query.setParameter(1, email);
        return query.getResultList();
    }

    @Override
    public List<User> findAll() {
        Query query = this.em.createQuery(
                "SELECT u FROM User u", User.class
        );
        return query.getResultList();
    }

    public List<User> getAllAdminUsers() {
        Query query = this.em.createQuery(
                "SELECT u FROM User u WHERE u.role = 'admin'", User.class
        );
        return query.getResultList();
    }



    public List<User> findUsersCreatedBeforeToday(){
        Date currentDate = new Date();
        TypedQuery<User> query = this.em.createQuery(
                "SELECT u FROM User u WHERE u.dateCreated < :currentDate ", User.class
        );
        query.setParameter("currentDate", currentDate);
        return query.getResultList();
    }

    public User findUserByUserProfileId(long id) {
        try {
            User user = em.createQuery(
                            "SELECT u FROM User u WHERE u.userProfile.id = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findByResetPassword(ResetPassword resetPassword) {
        try {
            User user = em.createQuery(
                    "SELECT u FROM User u WHERE u.resetPassword = :resetPassword", User.class)
                    .setParameter("resetPassword", resetPassword)
                    .getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public User findById(long id) {
        return em.find(User.class, id);
    }

    @Override
    @Transactional
    public User save(User user) {
        em.merge(user);
        return user;
    }
/*
    public List<User> updateUser() {
        Query query = this.em.createQuery(
                "UPDATE User u SET u.email = :email", User.class
        );
        return query.getResultList();
    }

    public List<UserProfile> updateUserProfile(UserProfile user) {
        Query query = this.em.createQuery(
                "UPDATE UserProfile up SET up.firstName = :firstName, up.addition = :addition, up.lastName = :lastName, up.bio = :bio, up.phoneNumber = :phoneNumber, up.residence = :residence, up.country = :country, up.address = :address, houseNumber = :houseNumber, up.postalCode = :postalCode, up.imageData = :imageData", User.class)
                .setParameter("firstName", user.getFirstName())
                .setParameter("addition", user.getAddition())
                .setParameter("lastName", user.getLastName())
                .setParameter("bio", user.getBio())
                .setParameter("phoneNumber", user.getPhoneNumber())
                .setParameter("residence", user.getResidence())
                .setParameter("country", user.getCountry())
                .setParameter("address", user.getAddress())
                .setParameter("houseNumber", user.getHouseNumber())
                .setParameter("postalCode", user.getPostalCode())
                .setParameter("imageData", user.getImageData())

        return query.getResultList();
    }

    public List<ExpertProfile> updateUserRoles(ExpertProfile user) {
        Query query = this.em.createQuery(
                        "UPDATE ExpertProfile up SET up.firstName = :firstName, up.addition = :addition, up.lastName = :lastName, up.bio = :bio, up.phoneNumber = :phoneNumber, up.residence = :residence, up.country = :country, up.address = :address, houseNumber = :houseNumber, up.postalCode = :postalCode, up.imageData = :imageData", User.class)
                .setParameter("firstName", user.getFirstName())
                .setParameter("addition", user.getAddition())
                .setParameter("lastName", user.getLastName())
                .setParameter("bio", user.getBio())
                .setParameter("phoneNumber", user.getPhoneNumber())
                .setParameter("residence", user.getResidence())
                .setParameter("country", user.getCountry())
                .setParameter("address", user.getAddress())
                .setParameter("houseNumber", user.getHouseNumber())
                .setParameter("postalCode", user.getPostalCode())
                .setParameter("imageData", user.getImageData())

        return query.getResultList();
    }

 */

    @Override
    public User deleteById(long id) {
        User user = this.findById(id);
        em.remove(user);
        return user;
    }

    public Boolean userIsTaken (String email) {
        try {
            User foundUser = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }


    public List<User> getUsersByInvitationURL(String invitationURL) {
        TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.invitationURL = :invitationURL", User.class);
        query.setParameter("invitationURL", invitationURL);
        return query.getResultList();
    }


}
