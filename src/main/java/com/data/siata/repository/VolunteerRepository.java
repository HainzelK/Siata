package com.data.siata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.siata.model.Volunteer;
import com.data.siata.model.Id.VolunteerId;

public interface VolunteerRepository extends JpaRepository<Volunteer, VolunteerId> {
    List<Volunteer> findByIdEventId(int eventId);
    List<Volunteer> findByIdUserId(int userId);
}

