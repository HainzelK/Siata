package com.data.siata.dto;

public class GalleryDTO {
    private Integer eventId;
    private Integer destinationId;
    private String mediaType;
    private String mediaUrl;
    
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
    public String getMediaType() {
        return mediaType;
    }
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    public String getMediaUrl() {
        return mediaUrl;
    }
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
}
