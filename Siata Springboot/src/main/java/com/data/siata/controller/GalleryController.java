package com.data.siata.controller;

import com.data.siata.dto.GalleryDTO;
import com.data.siata.model.Gallery;
import com.data.siata.service.GalleryService;
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
@RequestMapping("/api/gallery")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @GetMapping
    public List<Gallery> getAllGallery() {
        return galleryService.getAllGallery();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable int id) {
        return galleryService.getGalleryById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{mediaType}")
    public ResponseEntity<List<Gallery>> getGalleryByMediaType(@PathVariable String mediaType) {
        List<Gallery> galleries = galleryService.getGalleryByMediaType(mediaType);
        if (galleries.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(galleries);
    }

    // di body masukkan cuma salah satu (eventId atau destinationId)
    @PostMapping
    public ResponseEntity<Map<String, String>> createGallery(@RequestBody GalleryDTO galleryDTO) {
        galleryService.createGallery(galleryDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Gallery entry created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Gallery> updateGallery(@PathVariable int id, @RequestBody GalleryDTO galleryDetails) {
        return galleryService.getGalleryById(id)
            .map(gallery -> {
                gallery.setMediaType(galleryDetails.getMediaType());
                byte[] decodedBytes = Base64.getDecoder().decode(galleryDetails.getMediaUrl().split(",")[1]);
                gallery.setMediaUrl(decodedBytes);        
                Gallery updatedGallery = galleryService.saveGallery(gallery);
                return ResponseEntity.ok(updatedGallery);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable int id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.noContent().build();
    }
}
