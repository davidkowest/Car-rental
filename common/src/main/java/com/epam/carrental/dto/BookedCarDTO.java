package com.epam.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookedCarDTO implements CarUsageDTO, Serializable {

    private CarDTO car;
    private CustomerDTO customer;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

}

