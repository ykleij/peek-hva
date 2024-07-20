package com.example.backend.rest;

import com.example.backend.exceptions.PreConditionFailedException;
import com.example.backend.exceptions.ResourceNotFoundException;
import com.example.backend.models.Comment;
import com.example.backend.models.ExpertProfile;
import com.example.backend.models.Project;
import com.example.backend.models.User;
import com.example.backend.repositories.CommentRepository;
import com.example.backend.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Transactional
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Comment getCommentById(@PathVariable long id) {
        Comment foundComment = commentRepository.findById(id);

        if (foundComment != null) {
            return foundComment;
        } else {
            throw new ResourceNotFoundException("Id not found for Comment");
        }
    }

    @GetMapping(path = "/posted-on/{id}")
    public List<Comment> getCommentByPostedOnId(@PathVariable long id) {
       // System.out.println("PING");

        List<Comment> foundComments = commentRepository.findByPostedOnId(id);

        if (foundComments != null) {
            return foundComments;
        } else {
            throw new ResourceNotFoundException("No comments found posted on the User{id=" + id + "}");
        }
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Map<String, Object> comment) {
        Comment newComment = new Comment();

        newComment.setBody(comment.get("body").toString());

        User postedOnUser = userRepository.findById(Integer.parseInt(comment.get("postedOn").toString()));
        User posterUser = userRepository.findById(Integer.parseInt(comment.get("poster").toString()));


        newComment.setPostedOn(postedOnUser);
        newComment.setPoster(posterUser);


        Comment savedComment = commentRepository.save(newComment);

        // Build the URI for the newly created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newComment.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedComment);
    }


    @PutMapping("/{id}")
    public Comment updateOffer(@RequestBody Comment comment, @PathVariable long id) {
        Comment oldComment = getCommentById(id);

        if (!Objects.equals(oldComment.getId(), comment.getId())) {
            throw new PreConditionFailedException("Comment(id=" + comment.getId() + ") does not match path parameter(id=" + id + ")");
        }
        return commentRepository.save(comment);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long id) {
        Comment comment = commentRepository.deleteById(id);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found.");
        }

        return ResponseEntity.ok(comment);
    }
}
