package com.epam.carrental.chart;

import com.epam.carrental.dto.CarDTO;
import lombok.EqualsAndHashCode;
import schedule.model.Resource;

@EqualsAndHashCode
public class CarResource implements Resource {
    final CarDTO carDTO;

    public CarResource(CarDTO carDTO) {
        this.carDTO = carDTO;
    }

    @Override
    public String toString() {
        return carDTO.getRegistrationNumber() + ", " + carDTO.getModel();
    }
}
