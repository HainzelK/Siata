package com.data.siata.dto;

import java.io.Serializable;

import com.data.siata.model.Event;
import com.data.siata.model.User;

public class VolunteerDTO implements Serializable {
    private int userId;
    private int eventId;
    private User user;
    private Event event;
    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
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
