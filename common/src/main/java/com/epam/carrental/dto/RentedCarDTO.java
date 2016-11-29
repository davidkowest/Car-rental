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
    private ZonedDateTime plannedDateOfReturn;

    public RentedCarDTO(CarDTO car, CustomerDTO customer) {
        this.car = car;
        this.customer = customer;
    }

    public RentedCarDTO(CarDTO car, CustomerDTO customer,ZonedDateTime plannedDateOfReturn) {
        this(car,customer);
        this.plannedDateOfReturn=plannedDateOfReturn;
    }
}
