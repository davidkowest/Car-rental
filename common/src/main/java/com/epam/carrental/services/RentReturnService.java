package com.epam.carrental.services;

import com.epam.carrental.dto.RentedCarDTO;

public interface RentReturnService {

    void rentCarForCustomer(RentedCarDTO rentedCarDTO);

    void returnRentedCar(RentedCarDTO rentedCarDTO);
}
