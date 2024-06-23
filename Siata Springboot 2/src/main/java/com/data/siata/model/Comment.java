package com.data.siata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private int commentId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", columnDefinition = "INT")
    private User user;

    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "destination_id", columnDefinition = "INT")
    private Destination destination;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", columnDefinition = "INT")
    private Event event;

    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String commentText;

    @Column(name = "created_at", columnDefinition = "DATETIME")
    private String createdAt;

    public Comment() {
    }

    public Comment(User user, Destination destination, Event event, String commentText, String createdAt) {
        this.user = user;
        this.destination = destination;
        this.event = event;
        this.commentText = commentText;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    
}
