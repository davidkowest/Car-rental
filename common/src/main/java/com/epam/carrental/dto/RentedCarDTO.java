package com.epam.carrental.dto;

import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentedCarDTO implements Serializable {

    private CarDTO car;
    private CustomerDTO customer;
    private ZonedDateTime dateOfRent;
    private ZonedDateTime dateOfReturn;
    private long duration;

    public RentedCarDTO(CarDTO car, CustomerDTO customer) {
        this.car = car;
        this.customer = customer;
    }
}
