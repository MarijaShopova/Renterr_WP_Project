package com.finki.renterr.service;

import com.finki.renterr.model.Statistics;
import com.finki.renterr.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public List<Statistics> getAllStatistics(){
        return statisticsRepository.findAll();
    }
}
