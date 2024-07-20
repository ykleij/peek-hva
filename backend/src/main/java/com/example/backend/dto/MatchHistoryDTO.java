package com.example.backend.dto;

import java.util.Date;

public class MatchHistoryDTO {
    private long id;
    private long adminId;
    private String adminName;
    private Date dateTimeMatched;
    private long matchedExpertsCount;

    public MatchHistoryDTO(long id, long adminId, String adminName, Date dateTimeMatched, long matchedExpertsCount) {
        this.id = id;
        this.adminId = adminId;
        this.adminName = adminName;
        this.dateTimeMatched = dateTimeMatched;
        this.matchedExpertsCount = matchedExpertsCount;
    }

    public MatchHistoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getDateTimeMatched() {
        return dateTimeMatched;
    }

    public void setDateTimeMatched(Date dateTimeMatched) {
        this.dateTimeMatched = dateTimeMatched;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMatchedExpertsCount() {
        return matchedExpertsCount;
    }

    public void setMatchedExpertsCount(long matchedExpertsCount) {
        this.matchedExpertsCount = matchedExpertsCount;
    }
}
