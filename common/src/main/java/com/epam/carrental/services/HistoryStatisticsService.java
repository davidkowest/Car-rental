package com.epam.carrental.services;

import com.epam.carrental.dto.StatisticsDTO;

import java.time.ZonedDateTime;

public interface HistoryStatisticsService {
    StatisticsDTO filter(ZonedDateTime startDate, ZonedDateTime endDate);
}