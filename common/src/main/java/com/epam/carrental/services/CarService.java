package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;

public interface CarService {
    Boolean create(CarDTO car);

    Object [][] getAllCarsForJTable();
}
