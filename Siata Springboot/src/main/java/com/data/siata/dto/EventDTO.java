package com.data.siata.dto;

import javax.sql.rowset.serial.SerialBlob;

public class EventDTO {
    private String eventName;
    private String eventDescription;
    private String eventDate;
    private String eventTime;
    private String location;
    private SerialBlob eventImg;

    // Getters and Setters
    public String getEventName() {
        return eventName;
    }
    
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public SerialBlob getEventImg() {
        return eventImg;
    }

    public void setEventImg(SerialBlob eventImg) {
        this.eventImg = eventImg;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}