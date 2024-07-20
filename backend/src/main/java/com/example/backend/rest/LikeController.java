package com.example.backend.rest;

import com.example.backend.models.Project;
import com.example.backend.service.LikingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikingService likingService;

    @GetMapping("/{userId}")
    public ResponseEntity<ArrayList<Long>> getAllLikesFromUser(@PathVariable long userId) {
        return ResponseEntity.ok(likingService.getAllLikesFromUser(userId));
    }

    @PutMapping("/user/{userId}/project/{projectId}")
    public Project assignUserToProject(@PathVariable long userId, @PathVariable long projectId){
        return likingService.assignUserToProject(projectId, userId);
    }

    @DeleteMapping("/user/{userId}/project/{projectId}")
    public Project removeUserFromProject(@PathVariable long userId, @PathVariable long projectId){
        return likingService.removeUserFromProject(projectId, userId);
    }


}
