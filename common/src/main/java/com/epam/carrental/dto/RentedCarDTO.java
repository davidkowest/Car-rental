package com.epam.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentedCarDTO implements Serializable {

    private CarDTO car;
    private CustomerDTO customer;
    private ZonedDateTime dateOfRent;

    public RentedCarDTO(CarDTO car, CustomerDTO customer) {
        this.car = car;
        this.customer = customer;
    }
}
