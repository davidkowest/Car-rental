package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;

import java.util.List;

public interface CurrentRentalsService {

    List<CarDTO> findNotRented();

    List<RentedCarDTO> findCurrentRentals();
}
