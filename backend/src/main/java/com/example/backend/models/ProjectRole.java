package com.example.backend.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.Set;

@Entity
@Transactional
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private int qualifierValue;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "project_role_roles",
            joinColumns = @JoinColumn(name = "project_role_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "project_role_tools",
            joinColumns = @JoinColumn(name = "project_role_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id"))
    private Set<Tool> tools;

    public ProjectRole() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQualifierValue() {
        return qualifierValue;
    }

    public void setQualifierValue(int qualifierValue) {
        this.qualifierValue = qualifierValue;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Tool> getTools() {
        return tools;
    }

    public void setTools(Set<Tool> tools) {
        this.tools = tools;
    }

    @Override
    public String toString() {
        return "ProjectRole{" +
                "id=" + id +
                ", qualifierValue=" + qualifierValue +
                ", sector=" + sector +
                ", roles=" + roles +
                ", tools=" + tools +
                '}';
    }
}
