package com.finki.renterr.api.controller;

import com.finki.renterr.model.Statistics;
import com.finki.renterr.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping
    public List<Statistics> getStatistics() {
        return service.getAllStatistics();
    }
}
