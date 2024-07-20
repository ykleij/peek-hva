package com.example.backend.dto;

import com.example.backend.models.ProjectRole;
import com.example.backend.models.UserProfile;

import java.util.Set;

public class UserProfileDTO {

    UserProfile userProfile;
    Set<ProjectRole> projectRoles;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<ProjectRole> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @Override
    public String toString() {
        return "UserProfileDTO{" +
                "userProfile=" + userProfile +
                ", projectRoles=" + projectRoles +
                '}';
    }
}
