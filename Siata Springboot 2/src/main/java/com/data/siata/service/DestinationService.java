package com.data.siata.service;

import com.data.siata.model.Destination;
import com.data.siata.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    public List<Destination> getAllDestination() {
        return destinationRepository.findAll();
    }

    public Optional<Destination> getDestinationById(int id) {
        return destinationRepository.findById(id);
    }

    public Destination saveDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public void deleteDestination(int id) {
        destinationRepository.deleteById(id);
    }
}
