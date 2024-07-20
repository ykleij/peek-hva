package com.example.backend.models;

import jakarta.persistence.*;

@Entity
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String url;

    public ResetPassword() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ResetPassword{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
