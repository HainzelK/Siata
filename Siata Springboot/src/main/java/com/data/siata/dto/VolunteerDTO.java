package com.data.siata.dto;

import com.data.siata.model.Event;
import com.data.siata.model.User;

public class VolunteerDTO {
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

}

