package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.dto.RentedCarHistoryDTO;

import java.time.ZonedDateTime;
import java.util.List;

public interface RentReturnService {

    void rentCarForCustomer(RentedCarDTO rentedCarDTO);

    void returnRentedCar(RentedCarDTO rentedCarDTO);
}
