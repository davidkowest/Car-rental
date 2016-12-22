package com.epam.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentedCarDTO implements CarUsageDTO, Serializable {

    private CarDTO car;
    private CustomerDTO customer;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public RentedCarDTO(CarDTO car, CustomerDTO customer, ZonedDateTime plannedDateOfReturn) {
        this.car = car;
        this.customer = customer;
        this.endDate = plannedDateOfReturn;
    }
}
