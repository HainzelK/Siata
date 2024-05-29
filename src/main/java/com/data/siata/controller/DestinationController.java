package com.data.siata.controller;

import com.data.siata.dto.DestinationDTO;
import com.data.siata.model.Destination;
import com.data.siata.service.DestinationService;
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
@RequestMapping("/api/destination")
public class DestinationController {

    @Autowired
    private DestinationService destinationService;

    @GetMapping
    public List<Destination> getAllDestination() {
        return destinationService.getAllDestination();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> getDestinationById(@PathVariable int id) {
        return destinationService.getDestinationById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createDestination(@RequestBody DestinationDTO destinationDTO) {
        Destination destination = new Destination();
        destination.setDestinationName(destinationDTO.getDestinationName());
        destination.setDescription(destinationDTO.getDescription());
        destination.setLocation(destinationDTO.getLocation());
        byte[] decodedBytes = Base64.getDecoder().decode(destinationDTO.getPhoto().split(",")[1]);
        destination.setPhoto(decodedBytes);        
        destinationService.saveDestination(destination);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Destination created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Destination> updateDestination(@PathVariable int id, @RequestBody DestinationDTO destinationDetails) {
        return destinationService.getDestinationById(id)
            .map(destination -> {
                destination.setDestinationName(destinationDetails.getDestinationName());
                destination.setDescription(destinationDetails.getDescription());
                destination.setLocation(destinationDetails.getLocation());
                byte[] decodedBytes = Base64.getDecoder().decode(destinationDetails.getPhoto().split(",")[1]);
                destination.setPhoto(decodedBytes);        
                Destination updatedDestination = destinationService.saveDestination(destination);
                return ResponseEntity.ok(updatedDestination);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable int id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }
}
