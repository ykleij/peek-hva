package com.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sectorName;

    @NotNull
    private boolean inList = false;


    public Sector() {
    }

    public Sector(String sectorName, boolean inList) {
        this.sectorName = sectorName;
        this.inList = inList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public boolean isInList() {
        return inList;
    }

    public void setInList(boolean inList) {
        this.inList = inList;
    }

    @Override
    public String toString() {
        return "Sector{" +
                "id=" + id +
                ", sectorName='" + sectorName + '\'' +
                ", inList=" + inList +
                '}';
    }
}
