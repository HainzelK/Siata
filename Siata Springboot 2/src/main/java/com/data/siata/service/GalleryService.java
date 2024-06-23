package com.data.siata.service;

import com.data.siata.dto.GalleryDTO;
import com.data.siata.model.Destination;
import com.data.siata.model.Event;
import com.data.siata.model.Gallery;
import com.data.siata.repository.DestinationRepository;
import com.data.siata.repository.EventRepository;
import com.data.siata.repository.GalleryRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

        @Autowired
    private EventRepository eventRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Transactional
    public GalleryDTO createGallery(GalleryDTO galleryDTO) {
        Event event = null;
        if (galleryDTO.getEventId() != null) {
            event = eventRepository.findById(galleryDTO.getEventId())
                    .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + galleryDTO.getEventId()));
        }

        Destination destination = null;
        if (galleryDTO.getDestinationId() != null) {
            destination = destinationRepository.findById(galleryDTO.getDestinationId())
                    .orElseThrow(() -> new IllegalArgumentException("Destination not found with id: " + galleryDTO.getDestinationId()));
        }

        if (event == null && destination == null) {
            throw new IllegalArgumentException("Either eventId or destinationId must be provided");
        }

        Gallery gallery = new Gallery();
        gallery.setEvent(event);
        gallery.setDestination(destination);
        gallery.setMediaType(galleryDTO.getMediaType());
        byte[] decodedBytes = Base64.getDecoder().decode(galleryDTO.getMediaUrl().split(",")[1]);
        gallery.setMediaUrl(decodedBytes);        


        galleryRepository.save(gallery);

        return galleryDTO;
    }

    public List<Gallery> getAllGallery() {
        return galleryRepository.findAll();
    }

    public Optional<Gallery> getGalleryById(int id) {
        return galleryRepository.findById(id);
    }

    public List<Gallery> getGalleryByMediaType(String mediaType) {
        return galleryRepository.findByMediaType(mediaType);
    }

    public Gallery saveGallery(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    public void deleteGallery(int id) {
        if (galleryRepository.existsById(id)) {
            galleryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Gallery with id " + id + " does not exist.");
        }
    }

}
