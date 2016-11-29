package com.epam.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentedCarHistoryDTO extends RentedCarDTO {

    private ZonedDateTime dateOfReturn;

    public RentedCarHistoryDTO(CarDTO car, CustomerDTO customer,ZonedDateTime plannedDateOfReturn) {
        super(car, customer,plannedDateOfReturn);
    }

    public String getDuration() {
        return Duration.between(super.getDateOfRent(), dateOfReturn).toHours()+"hours";
    }
}
