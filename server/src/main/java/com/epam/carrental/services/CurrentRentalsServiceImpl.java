package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.RentedCar;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.RentedCarRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrentRentalsServiceImpl implements CurrentRentalsService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    RentedCarRepository rentedCarRepository;

    @Autowired
    ModelMapper modelMapper;

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
    public List<RentedCarDTO> findCurrentRentals() {
        Type listType = new TypeToken<List<RentedCarDTO>>() {
        }.getType();
        return modelMapper.map(rentedCarRepository.findAll(), listType);
    }
}
