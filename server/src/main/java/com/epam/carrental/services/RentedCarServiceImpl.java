package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCar;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.CustomerRepository;
import com.epam.carrental.repository.RentedCarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentedCarServiceImpl implements RentedCarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentedCarRepository rentedCarRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Boolean rentCarForCustomer(RentedCarDTO rentedCarDTO) {

        Car car = carRepository.findByRegistrationNumber(rentedCarDTO.getCar().getRegistrationNumber());
        Customer customer = customerRepository.findByEmail(rentedCarDTO.getCustomer().getEmail());
        ZonedDateTime currentDateTime=ZonedDateTime.now(ZoneId.systemDefault());

        RentedCar rentedCar = new RentedCar(car, customer,currentDateTime);
        rentedCarRepository.save(rentedCar);
        return true;
    }

    @Override
    public List<CarDTO> findNotRented() {
        List<Car> rentedCarList = rentedCarRepository.findAll().stream().map(RentedCar::getCar).collect(Collectors.toList());

        return carRepository.findAll()
                .stream()
                .filter(car -> !rentedCarList.contains(car))
                .map(car -> modelMapper.map(car, CarDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<RentedCarDTO> findAll() {
        return rentedCarRepository.findAll()
                .stream()
                .map(rentedCar -> modelMapper.map(rentedCar,RentedCarDTO.class))
                .collect(Collectors.toList());
    }
}


