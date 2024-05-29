package com.data.siata.controller;

import com.data.siata.dto.CommentDTO;
import com.data.siata.model.Comment;
import com.data.siata.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getAllComment() {
        return commentService.getAllComment();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable int id) {
        return commentService.getCommentById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setCommentText(commentDTO.getCommentText());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        commentService.saveComment(comment);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Comment created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable int id, @RequestBody Comment commentDetails) {
        return commentService.getCommentById(id)
            .map(comment -> {
                comment.setCommentText(commentDetails.getCommentText());
                comment.setCreatedAt(commentDetails.getCreatedAt());
                Comment updatedComment = commentService.saveComment(comment);
                return ResponseEntity.ok(updatedComment);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
