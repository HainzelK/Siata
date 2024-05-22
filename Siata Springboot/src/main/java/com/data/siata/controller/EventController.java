package com.data.siata.controller;

import com.data.siata.dto.EventDTO;
import com.data.siata.model.Event;
import com.data.siata.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable int id) {
        return eventService.getEventById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createEvent(@RequestBody EventDTO eventDTO) {
        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setEventDescription(eventDTO.getEventDescription());
        event.setEventDate(eventDTO.getEventDate());
        event.setEventTime(eventDTO.getEventTime());
        event.setLocation(eventDTO.getLocation());
        byte[] decodedBytes = Base64.getDecoder().decode(eventDTO.getEventImg().split(",")[1]);
        event.setEventImg(decodedBytes);        
        eventService.saveEvent(event);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Event created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable int id, @RequestBody EventDTO eventDetails) {
        return eventService.getEventById(id)
            .map(event -> {
                event.setEventName(eventDetails.getEventName());
                event.setEventDescription(eventDetails.getEventDescription());
                event.setEventDate(eventDetails.getEventDate());
                event.setEventTime(eventDetails.getEventTime());
                event.setLocation(eventDetails.getLocation());
                byte[] decodedBytes = Base64.getDecoder().decode(eventDetails.getEventImg().split(",")[1]);
                event.setEventImg(decodedBytes);                
                Event updatedEvent = eventService.saveEvent(event);
                return ResponseEntity.ok(updatedEvent);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
