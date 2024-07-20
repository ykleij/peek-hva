package com.example.backend.dto;

import com.example.backend.models.User;

public class MatchResult {
    private User user;
    private double matchingScore;

    public MatchResult(User user, double matchingScore) {
        this.user = user;
        this.matchingScore =  Math.ceil(matchingScore);
    }

    public User getUser() {
        return user;
    }

    public double getMatchingScore() {
        return matchingScore;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMatchingScore(double matchingScore) {
        this.matchingScore = matchingScore;
    }
}