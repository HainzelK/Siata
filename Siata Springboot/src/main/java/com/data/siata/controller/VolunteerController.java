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
@RequestMapping("/api/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping
    public List<VolunteerDTO> getAllVolunteers() {
        return volunteerService.findAllVolunteers();
    }

    @GetMapping("/{userId}/{eventId}")
    public ResponseEntity<VolunteerDTO> getVolunteerById(@PathVariable int userId, @PathVariable int eventId) {
        return volunteerService.findVolunteerById(userId, eventId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<VolunteerDTO>> getVolunteersByEventId(@PathVariable int eventId) {
        List<VolunteerDTO> volunteers = volunteerService.findVolunteersByEventId(eventId);
        if (volunteers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteers);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<VolunteerDTO>> getVolunteersByUserId(@PathVariable int userId) {
        List<VolunteerDTO> volunteers = volunteerService.findVolunteersByUserId(userId);
        if (volunteers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteers);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createVolunteer(@RequestBody VolunteerDTO volunteerDTO) {
        VolunteerDTO createdVolunteer = volunteerService.createVolunteer(volunteerDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Volunteer created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable int userId, @PathVariable int eventId) {
        volunteerService.deleteVolunteer(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}
