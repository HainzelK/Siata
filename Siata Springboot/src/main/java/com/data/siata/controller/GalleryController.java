package com.data.siata.controller;

import com.data.siata.dto.GalleryDTO;
import com.data.siata.model.Gallery;
import com.data.siata.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable int id) {
        return galleryService.getGalleryById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createGallery(@RequestBody GalleryDTO galleryDTO) {
        Gallery gallery = new Gallery();
        gallery.setMediaType(galleryDTO.getMediaType());
        gallery.setMediaUrl(galleryDTO.getMediaUrl());
        galleryService.saveGallery(gallery);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Gallery created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gallery> updateGallery(@PathVariable int id, @RequestBody Gallery galleryDetails) {
        return galleryService.getGalleryById(id)
            .map(gallery -> {
                gallery.setMediaType(galleryDetails.getMediaType());
                gallery.setMediaUrl(galleryDetails.getMediaUrl());
                Gallery updatedGallery = galleryService.saveGallery(gallery);
                return ResponseEntity.ok(updatedGallery);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGallery(@PathVariable int id) {
        galleryService.deleteGallery(id);
        return ResponseEntity.noContent().build();
    }
}
