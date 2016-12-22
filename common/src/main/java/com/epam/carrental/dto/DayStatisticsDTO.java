package com.epam.carrental.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Map;

@Data
public class DayStatisticsDTO implements Serializable {
    private String rentalClassName;
    private Map<DayOfWeek, Float> utilizationPerDay;
}