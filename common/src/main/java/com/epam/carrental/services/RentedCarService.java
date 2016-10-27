package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;

import java.time.ZonedDateTime;
import java.util.List;

public interface RentedCarService {

    void rentCarForCustomer(RentedCarDTO rentedCarDTO);

    List<CarDTO> findNotRented();

    List<RentedCarDTO> findCurrentRentals();

    void returnRentedCar(RentedCarDTO rentedCarDTO);

    List<RentedCarDTO> findByDateOfRentAndDateOfReturn(ZonedDateTime dateOfRent, ZonedDateTime dateOfReturn);
}
