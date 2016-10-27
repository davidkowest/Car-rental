package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;

import java.util.List;

public interface CarService {
    void create(CarDTO car);

    List<CarDTO> readAll();

}
