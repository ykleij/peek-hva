package com.example.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String toolName;

    @NotNull
    private boolean inList = false;


    public Tool() {
    }

    public Tool(String toolName, boolean inList) {
        this.toolName = toolName;
        this.inList = inList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public boolean isInList() {
        return inList;
    }

    public void setInList(boolean inList) {
        this.inList = inList;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "id=" + id +
                ", toolName='" + toolName + '\'' +
                ", inList=" + inList +
                '}';
    }
}
