package com.data.siata.dto;

import java.io.Serializable;

public class VolunteerDTO implements Serializable {
    private int userId;
    private int eventId;
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getEventId() {
        return eventId;
    }
    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
