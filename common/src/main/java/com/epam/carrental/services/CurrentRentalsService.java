package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.dto.RentedCarDTO;

import java.time.ZonedDateTime;
import java.util.List;

public interface CurrentRentalsService {

    List<CarDTO> findAvailableToRent(RentalClassDTO rentalClassDTO, ZonedDateTime plannedDateOfReturn);

    List<RentedCarDTO> findCurrentRentals();
}
