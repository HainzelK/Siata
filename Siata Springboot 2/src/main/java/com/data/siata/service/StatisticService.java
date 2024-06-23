package com.data.siata.service;

import com.data.siata.dto.StatisticDTO;
import com.data.siata.model.Event;
import com.data.siata.model.Statistic;
import com.data.siata.model.User;
import com.data.siata.repository.EventRepository;
import com.data.siata.repository.StatisticRepository;
import com.data.siata.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

        @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public StatisticDTO createStatistic(StatisticDTO statisticDTO) {
        User user = userRepository.findById(statisticDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + statisticDTO.getUserId()));

        Event event = eventRepository.findById(statisticDTO.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + statisticDTO.getEventId()));

        Statistic statistic = new Statistic();
        statistic.setUser(user);
        statistic.setEvent(event);
        statistic.setHoursVolunteered(statisticDTO.getHoursVolunteered());
        statistic.setDateRecorded(statisticDTO.getDateRecorded());

        statisticRepository.save(statistic);

        return statisticDTO;
    }

    public List<Statistic> getAllStatistic() {
        return statisticRepository.findAll();
    }

    public Optional<Statistic> getStatisticById(int id) {
        return statisticRepository.findById(id);
    }

    public Statistic saveStatistic(Statistic statistic) {
        return statisticRepository.save(statistic);
    }

    public void deleteStatistic(int id) {
        statisticRepository.deleteById(id);
    }
}
