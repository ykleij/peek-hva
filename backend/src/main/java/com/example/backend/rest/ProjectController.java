package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.*;
import com.example.backend.repositories.*;
import com.example.backend.service.LikingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    ProjectRoleRepository projectRoleRepository;

    @Autowired
    LikingService likingService;

    @Autowired
    ClientRepository clientRepository;

    @GetMapping
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @GetMapping("/amountOfProjectsYesterday")
    public List<Project> getProjectsCreatedBeforeToday() {
        return projectRepository.getProjectsCreatedBeforeToday();
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable long id) {
        Project project = projectRepository.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException("Project with ID " + id + " not found.");
        }

        return ResponseEntity.ok(project).getBody();
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Project> save(@RequestBody Map<String, Object> requestProject) {
        Project project = new Project();
        project.setName((String) requestProject.get("name"));
        project.setDescription((String) requestProject.get("description"));
        project.setDateCreated(new Date());

        SimpleDateFormat startDate = new SimpleDateFormat((String) requestProject.get("startDate"));
        SimpleDateFormat endDate = new SimpleDateFormat((String) requestProject.get("endDate"));
        project.setStartDate(startDate.get2DigitYearStart());
        project.setEndDate(endDate.get2DigitYearStart());
        project.setStatus(1);

        String imageDataObject = (String) requestProject.get("imageData");
        if (imageDataObject != null) {
            String base64Data = imageDataObject.replaceFirst("data:image\\/.*;base64,", "");
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            project.setImageData(decodedBytes);
        }

        Object clientObject = requestProject.get("client");
        ObjectMapper objectMapper = new ObjectMapper();
        Client client = objectMapper.convertValue(clientObject, Client.class);
        Client savedClient = clientRepository.save(client);
        project.setClient(savedClient);

        Object projectRolesObject = requestProject.get("projectRoles");
        if (projectRolesObject != null) {
            List<Map<String, Object>> rawDataList = (List<Map<String, Object>>) projectRolesObject;

            Set<ProjectRole> projectRolesRaw = rawDataList.stream()
                    .map(rawData -> objectMapper.convertValue(rawData, ProjectRole.class))
                    .collect(Collectors.toSet());

            Set<ProjectRole> projectRoles = projectRolesRaw.stream().map(requestProjectRole -> {
                Sector sector = sectorRepository.findByNameOrSave(requestProjectRole.getSector().getSectorName(), true);

                Set<Role> roles = new HashSet<>();
                requestProjectRole.getRoles().forEach(role -> {
                    Role newRole = roleRepository.findByNameOrSave(role.getRoleName(), true);
                    roles.add(newRole);
                });

                Set<Tool> tools = new HashSet<>();
                requestProjectRole.getTools().forEach(tool -> {
                    Tool newTool = toolRepository.findByNameOrSave(tool.getToolName(), true);
                    tools.add(newTool);
                });

                ProjectRole projectRole = new ProjectRole();
                projectRole.setSector(sector);
                projectRole.setRoles(roles);
                projectRole.setTools(tools);
                projectRole.setQualifierValue(requestProjectRole.getQualifierValue());

                return projectRole;
            }).collect(Collectors.toSet());


            projectRoles.forEach(projectRole -> projectRoleRepository.save(projectRole));
            project.setProjectRoles(projectRoles);

            // Afterwards save the project
            Project newProject = projectRepository.save(project);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newProject.getId())
                    .toUri();
            return ResponseEntity.created(location).body(project);
        } else {
            throw new PreConditionFailedException("Project roles should be of list type");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody Project updatedProject) {
        if (id != updatedProject.getId()) {
            throw new PreConditionFailedException("The ID in the request path does not match the ID in the request body.");
        }

        Project project = projectRepository.findById(id);

        if (project == null) {
            throw new ResourceNotFoundException("Project with ID " + id + " not found.");
        }

        project.setDescription(updatedProject.getDescription());
        project.setStartDate(updatedProject.getStartDate());
        project.setEndDate(updatedProject.getEndDate());
        project.setStatus(updatedProject.getStatus());
//        project.setClient(updatedProject.getClient());
//        project.setProjectRoles(updatedProject.getProjectRoles());

        Project updateProject = projectRepository.save(project);

        return ResponseEntity.ok(updateProject);
    }

    @PutMapping("/{projectId}/candidates/{userId}")
    public ResponseEntity<User> saveToCandidateList(@PathVariable long projectId, @PathVariable long userId) {
        Project project = projectRepository.findById(projectId);

        if (project == null) {
            throw new ResourceNotFoundException("Project with ID " + projectId + " not found.");
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }

        project.getCandidates().add(user);
        projectRepository.save(project);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{projectId}/candidates/{userId}")
    public ResponseEntity<User> deleteFromCandidateList(@PathVariable long projectId, @PathVariable long userId) {
        Project project = projectRepository.findById(projectId);

        if (project == null) {
            throw new ResourceNotFoundException("Project with ID " + projectId + " not found.");
        }

        User user = userRepository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }

        try {
            project.getCandidates().remove(user);
            projectRepository.save(project);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new PreConditionFailedException("Something went wrong trying to save the candidate list of the project.");
        }
    }

    @PutMapping("/{projectId}/users/{userId}")
    public Project assignUserToProject(@PathVariable long projectId, @PathVariable long userId) {
        return likingService.assignUserToProject(projectId, userId);
    }

    @DeleteMapping("/{projectId}/users/{userId}")
    public Project removeUserFromProject(@PathVariable long projectId, @PathVariable long userId) {
        return likingService.removeUserFromProject(projectId, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        Project project = projectRepository.deleteById(id);

        if (project == null) {
            throw new ResourceNotFoundException("Project with ID " + id + " not found.");
        }

        return ResponseEntity.ok(project);
    }
}
