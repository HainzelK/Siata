package com.data.siata.controller;

import com.data.siata.dto.VolunteerDTO;
import com.data.siata.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/volunteer")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping
    public List<VolunteerDTO> getAllVolunteers() {
        return volunteerService.findAllVolunteers();
    }

    @GetMapping("/countuser/{eventId}")
    public long countUsersByEventId(@PathVariable int eventId) {
        return volunteerService.countUsersByEventId(eventId);
    }

    @GetMapping("/id/{userId}/{eventId}")
    public ResponseEntity<VolunteerDTO> getVolunteerById(@PathVariable int userId, @PathVariable int eventId) {
        return volunteerService.findVolunteerById(userId, eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createVolunteer(@RequestBody VolunteerDTO volunteerDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            volunteerService.createVolunteer(volunteerDTO);
            response.put("message", "Volunteer created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/id/{userId}/{eventId}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable int userId, @PathVariable int eventId) {
        volunteerService.deleteVolunteer(userId, eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}/events")
    public ResponseEntity<List<VolunteerDTO>> getEventsJoinedByUser(@PathVariable int userId) {
        List<VolunteerDTO> eventsJoinedByUser = volunteerService.findEventsJoinedByUser(userId);
        if (eventsJoinedByUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(eventsJoinedByUser);
    }

    @GetMapping("/event/{eventId}/users")
    public ResponseEntity<List<VolunteerDTO>> getUsersJoiningEvent(@PathVariable int eventId) {
        List<VolunteerDTO> usersJoiningEvent = volunteerService.findUsersJoiningEvent(eventId);
        if (usersJoiningEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usersJoiningEvent);
    }
}
