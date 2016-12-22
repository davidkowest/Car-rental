package com.epam.carrental.dto;

import java.time.Duration;


public class RentedCarHistoryDTO extends RentedCarDTO {

    public String getDuration() {
        return Duration.between(super.getStartDate(), getEndDate()).toHours() + "hours";
    }
}
