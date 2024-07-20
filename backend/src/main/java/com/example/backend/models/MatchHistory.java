package com.example.backend.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class MatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date dateTimeMatched;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_user_id")
    private User admin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

//    @ManyToMany
//    @JoinTable(name = "matched_experts",
//            joinColumns = @JoinColumn(name = "match_history_id"),
//            inverseJoinColumns = @JoinColumn(name = "expert_user_id"))
//    private List<User> matchedUsers;

    @OneToMany(mappedBy = "matchHistory", cascade = CascadeType.ALL)
    private List<MatchedExpert> matchedExperts;

    public MatchHistory() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateTimeMatched() {
        return dateTimeMatched;
    }

    public void setDateTimeMatched(Date dateTimeMatched) {
        this.dateTimeMatched = dateTimeMatched;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

//    public List<User> getMatchedUsers() {
//        return matchedUsers;
//    }
//
//    public void setMatchedUsers(List<User> matchedUsers) {
//        this.matchedUsers = matchedUsers;
//    }

    public List<MatchedExpert> getMatchedExperts() {
        return matchedExperts;
    }

    public void setMatchedExperts(List<MatchedExpert> matchedExperts) {
        this.matchedExperts = matchedExperts;
    }

    @Override
    public String toString() {
        return "MatchHistory{" +
                "id=" + id +
                ", dateTimeMatched=" + dateTimeMatched +
                ", admin=" + admin +
                ", project=" + project +
//                ", matchedUsers=" + matchedUsers +
                ", matchedExperts=" + matchedExperts +
                '}';
    }
}
