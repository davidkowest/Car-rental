package com.epam.carrental.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TotalStatisticsDTO implements Serializable {
    private String rentalClassName;
    private float earnings;
    private float utilization;
}