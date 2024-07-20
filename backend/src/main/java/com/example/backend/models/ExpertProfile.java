package com.example.backend.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "ExpertProfile.findAllRolesByUserId",
                query = "SELECT r.roleName, pr.qualifierValue FROM ExpertProfile ep JOIN ep.projectRoles pr JOIN pr.roles r"
        )
})
public class ExpertProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "expert_profile_project_roles",
            joinColumns = @JoinColumn(name = "expert_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "project_roles_id"))
    private Set<ProjectRole> projectRoles;

    public ExpertProfile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<ProjectRole> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @Override
    public String toString() {
        return "ExpertProfile{" +
                "id=" + id +
                ", projectRoles=" + projectRoles +
                '}';
    }
}
