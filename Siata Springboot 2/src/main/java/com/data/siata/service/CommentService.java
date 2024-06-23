package com.data.siata.service;

import com.data.siata.dto.CommentDTO;
import com.data.siata.model.Comment;
import com.data.siata.model.Destination;
import com.data.siata.model.Event;
import com.data.siata.model.User;
import com.data.siata.repository.CommentRepository;
import com.data.siata.repository.DestinationRepository;
import com.data.siata.repository.EventRepository;
import com.data.siata.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

        @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + commentDTO.getUserId()));

        Event event = null;
        if (commentDTO.getEventId() != null) {
            event = eventRepository.findById(commentDTO.getEventId())
                    .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + commentDTO.getEventId()));
        }

        Destination destination = null;
        if (commentDTO.getDestinationId() != null) {
            destination = destinationRepository.findById(commentDTO.getDestinationId())
                    .orElseThrow(() -> new IllegalArgumentException("Destination not found with id: " + commentDTO.getDestinationId()));
        }

        if (event == null && destination == null) {
            throw new IllegalArgumentException("Either eventId or destinationId must be provided");
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setEvent(event);
        comment.setDestination(destination);
        comment.setCommentText(commentDTO.getCommentText());
        comment.setCreatedAt(commentDTO.getCreatedAt());

        commentRepository.save(comment);

        return commentDTO;
    }

    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}
