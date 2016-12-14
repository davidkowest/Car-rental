package com.epam.carrental.gui.utils;

import com.epam.carrental.dto.*;
import com.epam.carrental.models.chart.CarResource;
import com.epam.carrental.models.chart.CarState;
import com.epam.carrental.models.chart.CarUsage;
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

        bookedCarDTOS.stream()
                .forEach(bookedCarDTO -> assignments.put(
                        new CarResource(convertCar(bookedCarDTO.getCar())),
                        new CarUsage(bookedCarDTO.getStartDate(), bookedCarDTO.getEndDate(), convertCustomer(bookedCarDTO.getCustomer()), CarState.BOOKED))
                );

        return assignments;
    }

    private Multimap<CarResource, CarUsage> convertRentedHistory(List<RentedCarHistoryDTO> rentedCarHistoryDTOS) {
        Multimap<CarResource, CarUsage> assignments = ArrayListMultimap.create();

        rentedCarHistoryDTOS.stream()
                .forEach(rentedCarHistoryDTO -> assignments.put(
                        new CarResource(convertCar(rentedCarHistoryDTO.getCar())),
                        new CarUsage(rentedCarHistoryDTO.getDateOfRent(), rentedCarHistoryDTO.getDateOfReturn(), convertCustomer(rentedCarHistoryDTO.getCustomer()), CarState.HISTORY))
                );

        return assignments;
    }

    private Multimap<CarResource, CarUsage> convertCurrentRentals(List<RentedCarDTO> currentRentals) {
        Multimap<CarResource, CarUsage> assignments = ArrayListMultimap.create();

        currentRentals.stream()
                .forEach(currentRentalDTO -> assignments.put(
                        new CarResource(convertCar(currentRentalDTO.getCar())),
                        new CarUsage(currentRentalDTO.getDateOfRent(), currentRentalDTO.getPlannedDateOfReturn(), convertCustomer(currentRentalDTO.getCustomer()), CarState.RENTED))
                );

        return assignments;
    }

    private String convertCustomer(CustomerDTO customerDTO) {
        return customerDTO.getName();
    }

    private String convertCar(CarDTO carDTO) {
        return carDTO.getRegistrationNumber() + ", " + carDTO.getModel();
    }
}