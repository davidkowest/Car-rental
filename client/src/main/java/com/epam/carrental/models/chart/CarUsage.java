package com.epam.carrental.models.chart;

import com.epam.carrental.dto.CarUsageDTO;
import schedule.model.Task;

import java.time.ZonedDateTime;


public class CarUsage<T extends CarUsageDTO> implements Task {

    private final T carUsageDTO;
    private final CarState carState;


    public CarUsage(T carUsageDTO, CarState carState) {
        this.carUsageDTO = carUsageDTO;
        this.carState = carState;
    }

    @Override
    public String toString() {
        return carUsageDTO.getCustomer().getName();
    }

    @Override
    public ZonedDateTime getStart() {
        return carUsageDTO.getStartDate();
    }

    @Override
    public ZonedDateTime getEnd() {
        return carUsageDTO.getEndDate();
    }

    public CarState getCarState() {
        return carState;
    }
}