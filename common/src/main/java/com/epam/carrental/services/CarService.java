package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;

import java.util.List;

public interface CarService {
    Boolean create(CarDTO car);

    List<CarDTO> readAll();

}
