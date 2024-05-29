package com.data.siata.service;

import com.data.siata.dto.VolunteerDTO;
import com.data.siata.model.Volunteer;
import com.data.siata.model.Id.VolunteerId;
import com.data.siata.repository.VolunteerRepository;
import com.data.siata.repository.UserRepository;
import com.data.siata.repository.EventRepository;
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

    public VolunteerDTO createVolunteer(VolunteerDTO volunteerDTO) {
        Volunteer volunteer = convertToEntity(volunteerDTO);
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return convertToDto(savedVolunteer);
    }

    public void deleteVolunteer(int userId, int eventId) {
        volunteerRepository.deleteById(new VolunteerId(userId, eventId));
    }

    private VolunteerDTO convertToDto(Volunteer volunteer) {
        VolunteerDTO volunteerDTO = new VolunteerDTO();
        volunteerDTO.setUserId(volunteer.getId().getUserId());
        volunteerDTO.setEventId(volunteer.getId().getEventId());

        // Fetch and set user details
        userRepository.findById(volunteer.getId().getUserId()).ifPresent(volunteerDTO::setUser);

        // Fetch and set event details
        eventRepository.findById(volunteer.getId().getEventId()).ifPresent(volunteerDTO::setEvent);

        return volunteerDTO;
    }

    private Volunteer convertToEntity(VolunteerDTO volunteerDTO) {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(new VolunteerId(volunteerDTO.getUserId(), volunteerDTO.getEventId()));
        return volunteer;
    }
}
