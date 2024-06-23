package com.data.siata.controller;

import com.data.siata.dto.StatisticDTO;
import com.data.siata.model.Statistic;
import com.data.siata.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3306")
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @GetMapping
    public List<Statistic> getAllStatistic() {
        return statisticService.getAllStatistic();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Statistic> getStatisticById(@PathVariable int id) {
        return statisticService.getStatisticById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createStatistic(@RequestBody StatisticDTO statisticDTO) {
        statisticService.createStatistic(statisticDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Statistic entry created successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/id/{id}")
    public ResponseEntity<Statistic> updateStatistic(@PathVariable int id, @RequestBody Statistic statisticDetails) {
        return statisticService.getStatisticById(id)
            .map(statistic -> {
                statistic.setHoursVolunteered(statisticDetails.getHoursVolunteered());
                statistic.setDateRecorded(statisticDetails.getDateRecorded());
                Statistic updatedStatistic = statisticService.saveStatistic(statistic);
                return ResponseEntity.ok(updatedStatistic);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteStatistic(@PathVariable int id) {
        statisticService.deleteStatistic(id);
        return ResponseEntity.noContent().build();
    }
}
