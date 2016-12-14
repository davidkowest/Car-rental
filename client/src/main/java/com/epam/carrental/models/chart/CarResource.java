package com.epam.carrental.models.chart;

import lombok.EqualsAndHashCode;
import schedule.model.Resource;

@EqualsAndHashCode
public class CarResource implements Resource {
    final String name;

    public CarResource(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
