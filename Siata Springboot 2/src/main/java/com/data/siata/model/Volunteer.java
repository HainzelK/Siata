package com.data.siata.model;

import com.data.siata.model.Id.VolunteerId;

import jakarta.persistence.*;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @EmbeddedId
    private VolunteerId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", columnDefinition = "INT")
    private User user;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", columnDefinition = "INT")
    private Event event;

    public VolunteerId getId() {
        return id;
    }

    public void setId(VolunteerId id) {
        this.id = id;
    }

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
