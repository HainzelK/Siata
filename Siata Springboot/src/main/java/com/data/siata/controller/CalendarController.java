package com.data.siata.controller;

import com.data.siata.dto.CalendarDTO;
import com.data.siata.model.Calendar;
import com.data.siata.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping
    public List<Calendar> getAllCalendar() {
        return calendarService.getAllCalendar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Calendar> getCalendarById(@PathVariable int id) {
        return calendarService.getCalendarById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createCalendar(@RequestBody CalendarDTO calendarDTO) {
        Calendar calendar = new Calendar();
        calendar.setStartTime(calendarDTO.getStartTime());
        calendar.setEndTime(calendarDTO.getEndTime());
        calendar.setSummary(calendarDTO.getSummary());
        calendarService.saveCalendar(calendar);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Calendar created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Calendar> updateCalendar(@PathVariable int id, @RequestBody Calendar calendarDetails) {
        return calendarService.getCalendarById(id)
            .map(calendar -> {
                calendar.setStartTime(calendarDetails.getStartTime());
                calendar.setEndTime(calendarDetails.getEndTime());
                calendar.setSummary(calendarDetails.getSummary());
                Calendar updatedCalendar = calendarService.saveCalendar(calendar);
                return ResponseEntity.ok(updatedCalendar);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendar(@PathVariable int id) {
        calendarService.deleteCalendar(id);
        return ResponseEntity.noContent().build();
    }
}
