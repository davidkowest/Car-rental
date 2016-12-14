package com.epam.carrental.services;

import com.epam.carrental.dto.ChartDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChartServiceImpl implements ChartService {

    @Autowired
    CurrentRentalsService currentRentalsService;
    @Autowired
    BookCarService bookCarService;
    @Autowired
    RentalsHistoryService rentalsHistoryService;

    @Override
    public ChartDataDTO findAllData() {

        ChartDataDTO chartDataDTO=new ChartDataDTO();
        chartDataDTO.setBookedCars(bookCarService.findAll());
        chartDataDTO.setCurrentRentals(currentRentalsService.findAll());
        chartDataDTO.setRentedCarHistories(rentalsHistoryService.findAll());
        return chartDataDTO;
    }
}