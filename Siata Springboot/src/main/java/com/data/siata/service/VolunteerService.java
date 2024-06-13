package com.data.siata.service;

import com.data.siata.dto.VolunteerDTO;
import com.data.siata.model.Event;
import com.data.siata.model.User;
import com.data.siata.model.Volunteer;
import com.data.siata.model.Id.VolunteerId;
import com.data.siata.repository.EventRepository;
import com.data.siata.repository.UserRepository;
import com.data.siata.repository.VolunteerRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    public int countUsersByEventId(int eventId) {
        return volunteerRepository.countByEventId(eventId);
    }

    public List<VolunteerDTO> findAllVolunteers() {
        return volunteerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<VolunteerDTO> findVolunteerById(int userId, int eventId) {
        return volunteerRepository.findById(new VolunteerId(userId, eventId))
                .map(this::convertToDto);
    }

    public List<VolunteerDTO> findVolunteersByEventId(int eventId) {
        return volunteerRepository.findByIdEventId(eventId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<VolunteerDTO> findVolunteersByUserId(int userId) {
        return volunteerRepository.findByIdUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<VolunteerDTO> findEventsJoinedByUser(int userId) {
        return volunteerRepository.findByIdUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<VolunteerDTO> findUsersJoiningEvent(int eventId) {
        return volunteerRepository.findByIdEventId(eventId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public VolunteerDTO createVolunteer(VolunteerDTO volunteerDTO) {
        User user = userRepository.findById(volunteerDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + volunteerDTO.getUserId()));

        Event event = eventRepository.findById(volunteerDTO.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + volunteerDTO.getEventId()));

        Volunteer volunteer = new Volunteer();
        VolunteerId volunteerId = new VolunteerId(volunteerDTO.getUserId(), volunteerDTO.getEventId());
        volunteer.setId(volunteerId);
        volunteer.setUser(user);
        volunteer.setEvent(event);

        volunteerRepository.save(volunteer);

        return volunteerDTO;
    }

    public void deleteVolunteer(int userId, int eventId) {
        volunteerRepository.deleteById(new VolunteerId(userId, eventId));
    }

    private VolunteerDTO convertToDto(Volunteer volunteer) {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUserId(volunteer.getId().getUserId());
        volunteerDTO.setEventId(volunteer.getId().getEventId());

        return volunteerDTO;
    }

}
