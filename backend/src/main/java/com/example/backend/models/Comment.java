package com.example.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 1000)
    private String body;

    @ManyToOne
    @JoinColumn(name = "poster_id")
    private User poster;

    @ManyToOne
    @JoinColumn(name = "posted_on_id")
    private User postedOn;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public Comment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public User getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(User postedOn) {
        this.postedOn = postedOn;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", poster='" + poster + '\'' +
                ", postedOn='" + postedOn + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
