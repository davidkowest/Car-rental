package com.epam.carrental.dto;


import java.time.ZonedDateTime;

public interface CarUsageDTO {
    CarDTO getCar();
    CustomerDTO getCustomer();
    ZonedDateTime getStartDate();
    ZonedDateTime getEndDate();
}