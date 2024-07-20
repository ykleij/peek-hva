package com.example.backend.rest;

import com.example.backend.dto.UserProfileDTO;
import com.example.backend.models.*;
import com.example.backend.dto.UserRegistrationDTO;
import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.repositories.*;
import com.example.backend.service.InvitationService;
import com.example.backend.service.LikingService;
import com.example.backend.service.NotificationDistributor;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LikingService likingService;

    @Autowired
    NotificationDistributor notificationDistributor;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AuthenticationController authenticationController;

    @Autowired
    ProjectRoleRepository projectRoleRepository;

    @Autowired
    ExpertProfileRepository expertProfileRepository;

    @Autowired
    SectorRepository sectorRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ToolRepository toolRepository;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable long id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        return ResponseEntity.ok(user).getBody();
    }

    @GetMapping("/user-profile/{id}")
    public User getUserByUserProfileId(@PathVariable long id) {
        User user = userRepository.findUserByUserProfileId(id);

        if (user == null) {
            throw new ResourceNotFoundException("User with UserProfile ID " + id + " not found.");
        }

        return ResponseEntity.ok(user).getBody();
    }

    @GetMapping("/amountOfUsersYesterday")
    public List<User> getProjectsCreatedBeforeToday() {
        return userRepository.findUsersCreatedBeforeToday();
    }

    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody UserRegistrationDTO userData) {
        // validate invitation code

//        boolean invitationURLIsValid = invitationService.validInvitationLink(userData.getInvitationURL());

        Invitation invitation = invitationService.findByUrl(userData.getInvitationURL());

        if (invitation == null) {
            return new ResponseEntity<>("Invite is invalid!", HttpStatus.BAD_REQUEST);
        }

        if (invitation.getTimesUsed() >= invitation.getMaxUsers()) {
            return new ResponseEntity<>("Invite is invalid, reached limit!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(userData.getEmail());
        user.setInvitation(invitation);
        user.setPassword(userData.getPassword());


        if (userRepository.userIsTaken(user.getEmail())) {
            return new ResponseEntity<>("User with this email address already exists.", HttpStatus.BAD_REQUEST);
        }
        try {
            User createdUser = userRepository.save(user);

            invitation.addUserUsage(createdUser);

            Notification registerNotification = createNewExpertNotification(user);
            notificationDistributor.notify("userRegistration", registerNotification);
            notificationRepository.saveNotificationForAllAdmins(registerNotification);

//            Map<String, String> loginInfo = new HashMap<>() {{
//                put("email", userData.getEmail());
//                put("password", userData.getPassword());
//            }};
//            return authenticationController.authenticateAccount(loginInfo);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Couldn't register, try again later.", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Helper method for creating an expert notification
     *
     * @param user
     * @return
     */
    private Notification createNewExpertNotification(User user) {
        return new Notification(
                "New Expert Registration",
                String.format("'%s' has just registered an account.", user.getEmail()),
                new Date(),
                false
        );
    }

    @Transactional
    @PostMapping("/userProfile/{userId}")
    public ResponseEntity<?> saveUserProfile(@RequestBody UserProfileDTO userProfileDTO, @PathVariable long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + userId + " not found.");
        }

        try {
            ExpertProfile expertProfile = new ExpertProfile();

            Set<ProjectRole> projectRoles = userProfileDTO.getProjectRoles().stream().map(requestProjectRole -> {
                Sector sector = sectorRepository.findByNameOrSave(requestProjectRole.getSector().getSectorName(), false);

                Set<Role> roles = new HashSet<>();
                requestProjectRole.getRoles().forEach(role -> {
                    Role newRole = roleRepository.findByNameOrSave(role.getRoleName(), false);
                    roles.add(newRole);
                });

                Set<Tool> tools = new HashSet<>();
                requestProjectRole.getTools().forEach(tool -> {
                    Tool newTool = toolRepository.findByNameOrSave(tool.getToolName(), false);
                    tools.add(newTool);
                });

                ProjectRole projectRole = new ProjectRole();
                projectRole.setSector(sector);
                projectRole.setRoles(roles);
                projectRole.setTools(tools);
                projectRole.setQualifierValue(requestProjectRole.getQualifierValue());

                return projectRole;
            }).collect(Collectors.toSet());

            expertProfile.setProjectRoles(projectRoles);
            projectRoles.forEach(projectRole -> projectRoleRepository.save(projectRole));

            expertProfileRepository.save(expertProfile);

            user.setExpertProfile(expertProfile);
            user.setUserProfile(userProfileDTO.getUserProfile());
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Database constraint violation");
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation constraint violation");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }

        return ResponseEntity.ok(user);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody User updatedUser) {
        if (id != updatedUser.getId()) {
            throw new PreConditionFailedException("The ID in the request path does not match the ID in the request body.");
        }

        User user = userRepository.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        User savedUser = userRepository.save(updatedUser);
        //ProjectRole savedRoles = projectRoleRepository.save(updatedUser.);

        System.out.println("AT CONTROLLER: " + savedUser);

        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {

        likingService.removeAllLikesOfUser(id);

        User user = userRepository.findById(id);

        if (user == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }

        user.setResetPassword(null);
        userRepository.deleteById(id);

        return ResponseEntity.ok(user);
    }

    @JsonView(UserSummaryView.class)
    @GetMapping("/summary")
    public List<User> getScootersSummary() {
        return userRepository.findAll();
    }
}
