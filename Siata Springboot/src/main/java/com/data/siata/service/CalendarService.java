package com.data.siata.service;

import com.data.siata.model.Calendar;
import com.data.siata.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public List<Calendar> getAllCalendar() {
        return calendarRepository.findAll();
    }

    public Optional<Calendar> getCalendarById(int id) {
        return calendarRepository.findById(id);
    }

    public Calendar saveCalendar(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    public void deleteCalendar(int id) {
        calendarRepository.deleteById(id);
    }
}
