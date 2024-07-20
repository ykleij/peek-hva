package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String url;

    private int maxUsers;
    private int timesUsed = 0;

    @OneToMany(mappedBy = "invitation")
    @JsonManagedReference
    private Set<User> usedByUsers = new HashSet<>();

    public Invitation(String title, int maxUsers) {
        this.id = 0L;
        this.title = title;
        this.url = "";
        this.maxUsers = maxUsers;
    }

    public void addUserUsage (User user) {

        if(usedByUsers == null) {
            usedByUsers = new HashSet<>();
        }
        usedByUsers.add(user);
        timesUsed++;
    }

    public Invitation() {

    }

    public void generateInvitationLink () {
        this.url =  UUID.randomUUID().toString().replace("-", "").substring(0,8);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public Set<User> getUsedByUsers() {
        return usedByUsers;
    }

    public void setUsedByUsers(Set<User> usedByUsers) {
        this.usedByUsers = usedByUsers;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", maxUsers=" + maxUsers +
                '}';
    }
}
