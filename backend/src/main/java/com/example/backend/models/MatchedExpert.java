package com.example.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "matched_experts")
public class MatchedExpert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_history_id")
    private MatchHistory matchHistory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "matching_score")
    private Double matchingScore;

    public MatchedExpert() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MatchHistory getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(MatchHistory matchHistory) {
        this.matchHistory = matchHistory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getMatchingScore() {
        return matchingScore;
    }

    public void setMatchingScore(Double matchingScore) {
        this.matchingScore = matchingScore;
    }

    @Override
    public String toString() {
        return "MatchedExpert{" +
                "id=" + id +
                ", matchHistory=" + matchHistory +
                ", user=" + user +
                ", matchingScore=" + matchingScore +
                '}';
    }
}

