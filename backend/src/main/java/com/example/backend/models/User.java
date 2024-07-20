package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Users_find_by_email",
                query = "select u from User u where u.email = ?1")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "invitation_id")
    @JsonBackReference
    private Invitation invitation;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonBackReference
    private String password;

    private String role = "expert";
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @OneToOne(cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expert_profile_id", referencedColumnName = "id")
    private ExpertProfile expertProfile;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    @JsonIncludeProperties({"id"})
    private Set<Project> projects;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private ResetPassword resetPassword;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private Set<Notification> notifications = new HashSet<>();

    public User() {
    }

    public boolean verifyPassword(String plainTextPassword) {
        String hashedPassword = hashPassword(plainTextPassword);
        return hashedPassword.equals(this.password);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ExpertProfile getExpertProfile() {
        return expertProfile;
    }

    public void setExpertProfile(ExpertProfile expertProfile) {
        this.expertProfile = expertProfile;
    }

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Notification> getNotifications() {
        return this.notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public ResetPassword getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(ResetPassword resetPassword) {
        this.resetPassword = resetPassword;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Date();
        this.password = hashPassword(this.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", dateCreated=" + dateCreated +
                ", userProfile=" + userProfile +
                ", expertProfile=" + expertProfile +
                ", projects=" + projects +
                ", resetPassword=" + resetPassword +
                ", notifications=" + notifications +
                '}';
    }
}
