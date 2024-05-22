package com.data.siata.controller;

import com.data.siata.dto.VolunteerDTO;
import com.data.siata.model.Volunteer;
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
    public List<Volunteer> getAllVolunteers() {
        return volunteerService.findAllVolunteers();
    }

    @GetMapping("/{userId}/{eventId}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable int userId, @PathVariable int eventId) {
        return volunteerService.findVolunteerById(userId, eventId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createVolunteer(@RequestBody VolunteerDTO volunteerDTO) {
        Volunteer volunteer = new Volunteer();
        volunteer.setEvent(volunteerDTO.getEvent());
        volunteer.setUser(volunteerDTO.getUser());
        volunteerService.saveVolunteer(volunteer);
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
