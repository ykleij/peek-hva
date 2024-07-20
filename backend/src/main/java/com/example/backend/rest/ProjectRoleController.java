package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.*;
import com.example.backend.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/project-roles")
public class ProjectRoleController {

    @Autowired
    ProjectRoleRepository projectRoleRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpertProfileRepository expertRepository;


    @GetMapping
    public List<ProjectRole> getAll() {
        return projectRoleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProjectRole getById(@PathVariable long id) {
        ProjectRole projectRole = projectRoleRepository.findById(id);

        if (projectRole == null) {
            throw new ResourceNotFoundException("ProjectRole with ID " + id + " not found.");
        }

        return ResponseEntity.ok(projectRole).getBody();
    }

    @GetMapping("/sectors")
    public ResponseEntity<List<Sector>> getAllSectors() {
        List<Sector> sectors = sectorRepository.findAll();

        return ResponseEntity.ok(sectors);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<ProjectRole> getAllRolesByeExpertId(@PathVariable long id) {

        ExpertProfile expertProfile = this.expertRepository.findById(id);

        if (expertProfile == null) {
            throw new ResourceNotFoundException("Expert with ID " + id + " not found.");
        }

        //ProjectRole projectRole = this.projectRoleRepository.findById();


        return null; //ResponseEntity.ok(roles);
    }

    @GetMapping("/tools")
    public ResponseEntity<List<Tool>> getAllTools() {
        List<Tool> tools = toolRepository.findAll();

        return ResponseEntity.ok(tools);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProjectRole(@RequestBody Map<String, Object> request) {
        try {
            String type = (String) request.get("type");
            String title = (String) request.get("title");


            if(title.length() == 0) {
                return new ResponseEntity<>("Title is empty!", HttpStatus.CONFLICT);

            }

            if(type.equals("sectors")) {
                sectorRepository.findByNameOrSave(title, true);
            }

            if(type.equals("roles")) {
                roleRepository.findByNameOrSave(title, true);
            }

            if(type.equals("tools")) {
                toolRepository.findByNameOrSave(title, true);
            }


            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Error error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateProjectRole(@RequestBody Map<String, Object> request) {
        try {
            Long id = Long.parseLong((String) request.get("id"));
            String type = (String) request.get("type");
            String title = (String) request.get("title");
            Boolean newInList = (Boolean) request.get("newInList");




            if(title.length() == 0) {
                return new ResponseEntity<>("Title is empty!", HttpStatus.CONFLICT);

            }

            if(type.equals("sectors")) {
                sectorRepository.findByIdAndUpdate(id, title, newInList);
            }

            if(type.equals("roles")) {
                roleRepository.findByIdAndUpdate(id, title, newInList);
            }

            if(type.equals("tools")) {
                toolRepository.findByIdAndUpdate(id, title, newInList);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);



        } catch (Error error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PutMapping("/roles/{id}")
    public ResponseEntity<ExpertProfile> update(@PathVariable long id, @RequestBody Object[] updatedUserRoles) {

        User user = userRepository.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
/*
        List<Object[]> pr = expertRepository.findAllRolesByUserId(user.getId());

        for (Object[] array : pr) {
            System.out.println("ARRAY: " + array);
            for (Object element : array) {
                System.out.print(element + " ");
            }
            System.out.println();  // Move to the next line after printing each array
        }

 */

        /*
        Set<ProjectRole> projectRoles = new HashSet<>();

        for (Object updatedRole : updatedUserRoles) {
            if (updatedRole instanceof Object[]) {
                Object[] roleData = (Object[]) updatedRole;

                if (roleData.length >= 2) {
                    String roleName = (String) roleData[0];
                    String qualifierValue = (String) roleData[1];

                    Role role = roleRepository.findByNameOrSave(roleName);

                    ProjectRole projectRole = new ProjectRole();
                    projectRole.setRoles(Collections.singleton(role)); // Assuming a ProjectRole has a Set<Role>
                    projectRole.setQualifierValue(Integer.parseInt(qualifierValue));

                    projectRoles.add(projectRole);
                }
            }
        }

        ExpertProfile expertProfile = new ExpertProfile();
        expertProfile.setProjectRoles(projectRoles);

        ExpertProfile savedUserRoles = expertRepository.save(expertProfile);

        return ResponseEntity.ok(savedUserRoles);

         */
        return null;
    }


//    @PostMapping
//    public ResponseEntity<ProjectRole> save(@RequestBody ProjectRole projectRole) {
//        ProjectRole newProjectRole = projectRoleRepository.save(projectRole);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(newProjectRole.getId())
//                .toUri();
//        return ResponseEntity.created(location).body(newProjectRole);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody ProjectRole updatedProjectRole) {
//        if (id != updatedProjectRole.getId()) {
//            throw new PreConditionFailedException("The ID in the request path does not match the ID in the request body.");
//        }
//
//        ProjectRole projectRole = projectRoleRepository.findById(id);
//
//        if (projectRole == null) {
//            throw new ResourceNotFoundException("ProjectRole with ID " + id + " not found.");
//        }
//
//        projectRole.setQualifierValue(updatedProjectRole.getQualifierValue());
//
//
//        ProjectRole updateProject = projectRoleRepository.save(projectRole);
//
//        return ResponseEntity.ok(updateProject);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> delete(@PathVariable long id) {
//        ProjectRole projectRole = projectRoleRepository.deleteById(id);
//
//        if (projectRole == null) {
//            throw new ResourceNotFoundException("ProjectRole with ID " + id + " not found.");
//        }
//
//        return ResponseEntity.ok(projectRole);
//    }
}
