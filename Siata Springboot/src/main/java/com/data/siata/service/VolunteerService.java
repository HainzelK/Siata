package com.data.siata.service;

import com.data.siata.model.Volunteer;
import com.data.siata.model.Id.VolunteerId;
import com.data.siata.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    // Method to create a new volunteer
    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    // Method to find a volunteer by its composite key
    public Optional<Volunteer> findVolunteerById(int userId, int eventId) {
        VolunteerId volunteerId = new VolunteerId(userId, eventId);
        return volunteerRepository.findById(volunteerId);
    }

    // Method to get all volunteers
    public List<Volunteer> findAllVolunteers() {
        return volunteerRepository.findAll();
    }

    // Method to delete a volunteer by its composite key
    public void deleteVolunteer(int userId, int eventId) {
        VolunteerId volunteerId = new VolunteerId(userId, eventId);
        volunteerRepository.deleteById(volunteerId);
    }
}
