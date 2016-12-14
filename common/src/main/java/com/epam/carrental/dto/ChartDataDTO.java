package com.epam.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChartDataDTO implements Serializable{

    private List<RentedCarDTO> currentRentals;
    private List<BookedCarDTO> bookedCars;
    private List<RentedCarHistoryDTO> rentedCarHistories;
}
