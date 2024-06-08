package com.data.siata.service;

import com.data.siata.model.Gallery;
import com.data.siata.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

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
