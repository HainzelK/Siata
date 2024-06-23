package com.data.siata.dto;

public class CommentDTO {
    private int userId;
    // pakai Integer spy bisa null
    private Integer eventId;
    private Integer destinationId;
    private String commentText;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Integer getEventId() {
        return eventId;
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    public Integer getDestinationId() {
        return destinationId;
    }
    public void setDestinationId(Integer destinationId) {
        this.destinationId = destinationId;
    }
    private String createdAt;
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
