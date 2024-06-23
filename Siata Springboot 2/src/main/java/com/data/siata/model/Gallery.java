package com.data.siata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "media_gallery")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_id", nullable = false)
    private int mediaId;

    @ManyToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "destination_id", columnDefinition = "INT")
    private Destination destination;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", columnDefinition = "INT")
    private Event event;

    @Column(name = "media_type", columnDefinition = "ENUM('Image','Video')")
    private String mediaType;

    @Lob
    @Column(name = "media_url", columnDefinition = "MEDIUMBLOB")
    private byte[] mediaUrl;

    public Gallery() {
    }

    public Gallery(Destination destination, Event event, String mediaType, byte[] mediaUrl) {
        this.destination = destination;
        this.event = event;
        this.mediaType = mediaType;
        this.mediaUrl = mediaUrl;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
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

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(byte[] mediaUrl) {
        this.mediaUrl = mediaUrl;
    }
    
}
