package com.data.siata.model.Id;

import java.io.Serializable;
import java.util.Objects;

public class VolunteerId implements Serializable {

    private int user;
    private int event;

    public VolunteerId() {}

    public VolunteerId(int user, int event) {
        this.user = user;
        this.event = event;
    }

    // getters, setters, equals, and hashCode methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteerId that = (VolunteerId) o;
        return Objects.equals(user, that.user) && Objects.equals(event, that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, event);
    }
}
