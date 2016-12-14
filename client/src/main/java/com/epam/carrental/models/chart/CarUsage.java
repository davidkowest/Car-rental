package com.epam.carrental.models.chart;

import lombok.Getter;
import schedule.model.Task;

import java.time.ZonedDateTime;

@Getter
public class CarUsage implements Task {
    final ZonedDateTime start;
    final ZonedDateTime end;
    final String customer;
    final CarState carState;

    public CarUsage(ZonedDateTime start, ZonedDateTime end, String customer,CarState carState) {
        this.start = start;
        this.end = end;
        this.customer = customer;
        this.carState=carState;
    }

    @Override
    public String toString() {
        return customer;
    }
}