package com.epam.carrental.rentals;

import com.epam.carrental.data_generator.CurrentTimeUtil;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.cars.Car;
import com.epam.carrental.customers.Customer;
import com.epam.carrental.rentals.history.RentedCarHistory;
import com.epam.carrental.cars.CarRepository;
import com.epam.carrental.customers.CustomerRepository;
import com.epam.carrental.rentals.history.RentalsHistoryRepository;
import com.epam.carrental.services.CurrentRentalsService;
import com.epam.carrental.services.RentReturnService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Component
public class RentReturnServiceImpl implements RentReturnService {

    @Autowired
    CarRepository carRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RentedCarRepository rentedCarRepository;
    @Autowired
    RentalsHistoryRepository rentalsHistoryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CurrentTimeUtil currentTimeUtil;
    @Autowired
    CurrentRentalsService currentRentalsService;

    @Override
    @Transactional
    public void rentCarForCustomer(RentedCarDTO rentedCarDTO) {

        Car car = carRepository.findByRegistrationNumber(rentedCarDTO.getCar().getRegistrationNumber());
        Customer customer = customerRepository.findByEmail(rentedCarDTO.getCustomer().getEmail());
        ZonedDateTime currentDateTime = currentTimeUtil.getCurrentTime();

        RentedCar rentedCar = new RentedCar(car, customer, currentDateTime, rentedCarDTO.getEndDate());

        List<CarDTO> avaiableCars = currentRentalsService.findAvailableToRent(rentedCarDTO.getCar().getRentalClass(), rentedCarDTO.getEndDate());
        if (!avaiableCars.contains(rentedCarDTO.getCar())) {
            throw new IllegalArgumentException("This car is not available for this period");
        }


//// TODO: For demo concurrent modifications purpose only
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            log.error(e);
//        }
        rentedCarRepository.save(rentedCar);
    }

    @Override
    @Transactional
    public  void returnRentedCar(RentedCarDTO rentedCarDTO) {

        Car car = carRepository.findByRegistrationNumber(rentedCarDTO.getCar().getRegistrationNumber());
        if (car == null) {
            throw new IllegalArgumentException(rentedCarDTO.getCar()+"does not exist in DB");
        }

        Customer customer = customerRepository.findByEmail(rentedCarDTO.getCustomer().getEmail());
        if (customer == null) {
            throw new IllegalArgumentException(rentedCarDTO.getCustomer()+"does not exist in DB");
        }

        RentedCar rentedCar = rentedCarRepository.findByCarAndCustomerAndDateOfRent(car, customer, rentedCarDTO.getStartDate());

        ZonedDateTime dateOfReturn=currentTimeUtil.getCurrentTime();
        RentedCarHistory rentedCarHistory = modelMapper.map(rentedCar, RentedCarHistory.class);
        rentedCarHistory.setEndDate(dateOfReturn);

        rentedCarHistory.setId(null);

        rentalsHistoryRepository.save(rentedCarHistory);
        rentedCarRepository.deleteByCar(car);
    }
}