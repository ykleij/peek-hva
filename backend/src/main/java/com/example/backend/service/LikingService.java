package com.example.backend.service;

import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.Project;
import com.example.backend.models.User;
import com.example.backend.repositories.ProjectRepository;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class LikingService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;
    public Project assignUserToProject(long projectId, long userId) {

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("user not found");
        }
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new ResourceNotFoundException("project not found");
        }

        project.addUser(user);
        projectRepository.save(project);
        return projectRepository.save(project);
    }

    public Project removeUserFromProject(long projectId, long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("user not found");
        }
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new ResourceNotFoundException("project not found");
        }

        project.getUsers().remove(user);
        projectRepository.save(project);
        return projectRepository.save(project);
    }

    public void removeAllLikesOfUser(long id) {
        User user = userRepository.findById(id);
        Set<Project> projects = user.getProjects();

        for (Project project: projects) {
            project.getUsers().remove(user);
            projectRepository.save(project);
        }
    }

    public ArrayList<Long> getAllLikesFromUser(long id) {
        User user = userRepository.findById(id);
        Set<Project> likedProjects = user.getProjects();
        ArrayList<Long> likes = new ArrayList<>();
        for (Project project : likedProjects) {
            likes.add(project.getId());
        }
        return likes;
    }

}
