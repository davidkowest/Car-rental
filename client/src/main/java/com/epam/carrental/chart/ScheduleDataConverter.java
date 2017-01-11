package com.epam.carrental.chart;

import com.epam.carrental.dto.BookedCarDTO;
import com.epam.carrental.dto.ChartDataDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.dto.RentedCarHistoryDTO;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleDataConverter {

    public Multimap<CarResource, CarUsage> convert(ChartDataDTO chartDataDTO) {

        Multimap<CarResource, CarUsage> assignments = ArrayListMultimap.create();

        assignments.putAll(convertBookedCars(chartDataDTO.getBookedCars()));
        assignments.putAll(convertRentedHistory(chartDataDTO.getRentedCarHistories()));
        assignments.putAll(convertCurrentRentals(chartDataDTO.getCurrentRentals()));

        return assignments;
    }

    private Multimap<CarResource, CarUsage> convertBookedCars(List<BookedCarDTO> bookedCarDTOS) {
        Multimap<CarResource, CarUsage> assignments = ArrayListMultimap.create();

        bookedCarDTOS
                .forEach(bookedCarDTO -> assignments.put(
                        new CarResource(bookedCarDTO.getCar()),
                        new CarUsage<BookedCarDTO>(bookedCarDTO, CarState.BOOKED))
                );

        return assignments;
    }

    private Multimap<CarResource, CarUsage> convertRentedHistory(List<RentedCarHistoryDTO> rentedCarHistoryDTOS) {
        Multimap<CarResource, CarUsage> assignments = ArrayListMultimap.create();

        rentedCarHistoryDTOS
                .forEach(rentedCarHistoryDTO -> assignments.put(
                        new CarResource(rentedCarHistoryDTO.getCar()),
                        new CarUsage<RentedCarHistoryDTO>(rentedCarHistoryDTO, CarState.HISTORY))
                );

        return assignments;
    }

    private Multimap<CarResource, CarUsage> convertCurrentRentals(List<RentedCarDTO> currentRentals) {
        Multimap<CarResource, CarUsage> assignments = ArrayListMultimap.create();

        currentRentals
                .forEach(currentRentalDTO -> assignments.put(
                        new CarResource(currentRentalDTO.getCar()),
                        new CarUsage<RentedCarDTO>(currentRentalDTO, CarState.RENTED))
                );

        return assignments;
    }
}