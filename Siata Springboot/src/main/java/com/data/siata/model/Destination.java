package com.data.siata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destination_id", nullable = false)
    private int destinationId;

    @Column(name = "destination_name", columnDefinition = "VARCHAR(100)")
    private String destinationName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", columnDefinition = "VARCHAR(255)")
    private String location;

    @Column(name = "photo_url", columnDefinition = "BLOB")
    private String photo;

    public Destination() {
    }

    public Destination(String destinationName, String description, String location, String photo) {
        this.destinationName = destinationName;
        this.description = description;
        this.location = location;
        this.photo = photo;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
