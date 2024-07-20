package com.example.backend.repositories;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.models.Notification;
import com.example.backend.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public class NotificationRepository implements GenericRepository<Notification> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Notification> findAll() {
        TypedQuery<Notification> query = this.em.createQuery(
                "SELECT p from Notification p", Notification.class);
        return query.getResultList();
    }

    public List<Notification> findAllByUserId(Long userId) {
        TypedQuery<Notification> query = this.em.createQuery(
                "SELECT n FROM Notification n JOIN n.users u WHERE u.id = :userId", Notification.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public Notification findByNotificationDetails(Notification notification) {
        TypedQuery<Notification> query = this.em.createQuery(
                "SELECT n FROM Notification n WHERE n.title = :title AND n.description = :description AND n.dateCreated BETWEEN :start AND :end",
                Notification.class);

        Timestamp start = new Timestamp(notification.getDateCreated().getTime() - 10000);
        Timestamp end = new Timestamp(notification.getDateCreated().getTime() + 10000);

        query.setParameter("title", notification.getTitle());
        query.setParameter("description", notification.getDescription());
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getSingleResult();
    }

//    public Notification setToRead(long notificationId) {
//        TypedQuery<Notification> query = this.em.createQuery(
//                "SELECT n FROM Notification n WHERE n.title = :title AND n.description = :description AND n.dateCreated BETWEEN :start AND :end",
//                Notification.class);
//
//        Timestamp start = new Timestamp(notification.getDateCreated().getTime() - 10000);
//        Timestamp end = new Timestamp(notification.getDateCreated().getTime() + 10000);
//
//        query.setParameter("title", notification.getTitle());
//        query.setParameter("description", notification.getDescription());
//        query.setParameter("start", start);
//        query.setParameter("end", end);
//        return query.getSingleResult();
//    }

    @Override
    public Notification findById(long id) {
        return em.find(Notification.class, id);
    }

    @Override
    public Notification save(Notification notification) {
        if (notification.getId() == 0) {
            em.persist(notification);
        } else {
            notification = em.merge(notification);
        }
        return notification;
    }

    public Notification saveForUser(Notification notification, long userId) {
        try {
            User user = em.find(User.class, userId);

            if (user != null) {
                // Check if a similar notification already exists
                Notification existingNotification = findExistingNotification(notification);

                if (existingNotification == null) {
                    // The notification does not exist, save it
//                    notification.getUsers().add(user);
                    user.getNotifications().add(notification);
                    em.persist(notification);
                } else {
                    // The notification already exists, associate it with the user
                    user.getNotifications().add(existingNotification);
                }
            } else {
                throw new EntityNotFoundException("User not found with ID: " + userId);
            }
        } catch (Exception e) {
            throw new PreConditionFailedException("Error saving notification: " + e.getMessage());
        }
        return notification;
    }

    public void saveNotificationForAllAdmins(Notification notification) {
        em.persist(notification);

        List<User> admins = userRepository.getAllAdminUsers();

        for (User admin : admins) {
//            notification.getUsers().add(admin);
            admin.getNotifications().add(notification);
        }
    }

    private Notification findExistingNotification(Notification newNotification) {
        TypedQuery<Notification> query = em.createQuery(
                "SELECT n FROM Notification n WHERE n.title = :title AND n.description = :description AND n.dateCreated = :dateCreated", Notification.class);

        query.setParameter("title", newNotification.getTitle());
        query.setParameter("description", newNotification.getDescription());
        query.setParameter("dateCreated", newNotification.getDateCreated());

        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Notification deleteById(long id) {
        Notification notification = this.findById(id);
        em.remove(notification);
        return notification;
    }
}
