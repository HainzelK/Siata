package com.data.siata.model.Id;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VolunteerId implements Serializable {
    private int userId;
    private int eventId;

    public VolunteerId() {}

    public VolunteerId(int userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteerId that = (VolunteerId) o;
        return userId == that.userId && eventId == that.eventId;
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

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
