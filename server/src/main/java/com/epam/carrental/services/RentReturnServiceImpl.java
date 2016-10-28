package com.epam.carrental.services;

import com.epam.carrental.data_generator.CurrentTimeUtil;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.dto.RentedCarHistoryDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCar;
import com.epam.carrental.entity.RentedCarHistory;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.CustomerRepository;
import com.epam.carrental.repository.RentedCarHistoryRepository;
import com.epam.carrental.repository.RentedCarRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RentReturnServiceImpl implements RentReturnService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentedCarRepository rentedCarRepository;
    @Autowired
    private RentedCarHistoryRepository rentedCarHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CurrentTimeUtil currentTimeUtil;

    @Override
    @Transactional
    public  void rentCarForCustomer(RentedCarDTO rentedCarDTO) {

        Car car = carRepository.findByRegistrationNumber(rentedCarDTO.getCar().getRegistrationNumber());
        Customer customer = customerRepository.findByEmail(rentedCarDTO.getCustomer().getEmail());
        ZonedDateTime currentDateTime=currentTimeUtil.getCurrentTime();

        RentedCar rentedCar = new RentedCar(car, customer,currentDateTime);
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
            throw new IllegalArgumentException(car + " not exists in DB");
        }

        Customer customer = customerRepository.findByEmail(rentedCarDTO.getCustomer().getEmail());
        if (customer == null) {
            throw new IllegalArgumentException(customer + " not exists in DB");
        }

        RentedCar rentedCar = rentedCarRepository.findByCarAndCustomerAndDateOfRent(car, customer, rentedCarDTO.getDateOfRent());

        ZonedDateTime dateOfReturn=currentTimeUtil.getCurrentTime();
        RentedCarHistory rentedCarHistory = modelMapper.map(rentedCar, RentedCarHistory.class);
        rentedCarHistory.setDateOfReturn(dateOfReturn);

        rentedCarHistory.setId(null);

        rentedCarHistoryRepository.save(rentedCarHistory);
        rentedCarRepository.deleteByCar(car);
    }
}