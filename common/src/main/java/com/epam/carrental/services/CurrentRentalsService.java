package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.dto.RentedCarDTO;

import java.util.List;

public interface CurrentRentalsService {

    List<CarDTO> findNotRentedInClass(RentalClassDTO rentalClassDTO);

    List<RentedCarDTO> findCurrentRentals();
}
