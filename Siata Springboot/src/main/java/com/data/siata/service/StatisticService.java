package com.data.siata.service;

import com.data.siata.model.Statistic;
import com.data.siata.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

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
