package com.data.siata.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "statistics")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stat_id", nullable = false)
    private int statId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", columnDefinition = "INT")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", columnDefinition = "INT")
    private Event event;

    @Column(name = "hours_volunteered", columnDefinition = "INT")
    private int hoursVolunteered;

    @Column(name = "date_recorded", columnDefinition = "DATE")
    private String dateRecorded;

    public Statistic() {
    }

    public Statistic(User user, Event event, int hoursVolunteered, String dateRecorded) {
        this.user = user;
        this.event = event;
        this.hoursVolunteered = hoursVolunteered;
        this.dateRecorded = dateRecorded;
    }

    public int getStatId() {
        return statId;
    }

    public void setStatId(int statId) {
        this.statId = statId;
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

    public int getHoursVolunteered() {
        return hoursVolunteered;
    }

    public void setHoursVolunteered(int hoursVolunteered) {
        this.hoursVolunteered = hoursVolunteered;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }
    
}
