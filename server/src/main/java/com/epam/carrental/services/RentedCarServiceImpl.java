package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCar;
import com.epam.carrental.repository.RentedCarRepository;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        Car car = carRepository.findByRegistrationNumber(rentedCarDTO.getCarDTO().getRegistrationNumber());
        Customer customer = customerRepository.findByEmail(rentedCarDTO.getCustomerDTO().getEmail());
        RentedCar rentedCar = new RentedCar(car, customer);

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
}

