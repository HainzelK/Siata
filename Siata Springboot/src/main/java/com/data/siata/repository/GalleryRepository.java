package com.data.siata.repository;

import com.data.siata.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    List<Gallery> findByMediaType(String mediaType);
}
