package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private Date dateCreated;
    private boolean actionRequired;
    private boolean isRead;

    @ManyToMany(mappedBy = "notifications", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    public Notification() {
    }

    public Notification(String title, String description, Date dateCreated, boolean actionRequired) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.actionRequired = actionRequired;
        this.isRead = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isActionRequired() {
        return actionRequired;
    }

    public void setActionRequired(boolean actionRequired) {
        this.actionRequired = actionRequired;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + dateCreated +
                ", actionRequired=" + actionRequired +
                ", isRead=" + isRead +
                ", users=" + users +
                '}';
    }
}
