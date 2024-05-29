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
@Table(name = "calendar")
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id", nullable = false)
    private int calendarId;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", columnDefinition = "INT")
    private Event eventId;

    @Column(name = "start_time", columnDefinition = "DATETIME")
    private String startTime;

    @Column(name = "end_time", columnDefinition = "DATETIME")
    private String endTime;

    @Column(name = "summary", columnDefinition = "VARCHAR(255)")
    private String summary;

    public Calendar(Event eventId, String startTime, String endTime, String summary) {
        this.eventId = eventId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.summary = summary;
    }

    public Calendar() {
    }

    public int getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public Event getEventId() {
        return eventId;
    }

    public void setEventId(Event eventId) {
        this.eventId = eventId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    
}
