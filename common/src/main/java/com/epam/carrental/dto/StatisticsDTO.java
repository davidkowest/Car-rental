package com.epam.carrental.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StatisticsDTO implements Serializable {
    private List<TotalStatisticsDTO> totalStatisticsDTO;
    private List<DayStatisticsDTO> dayStatisticsDTO;
}