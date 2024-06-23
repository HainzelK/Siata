package com.data.siata.dto;

public class StatisticDTO {
    private int userId;
    private int eventId;
    private int hoursVolunteered;
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
    private String dateRecorded;
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
