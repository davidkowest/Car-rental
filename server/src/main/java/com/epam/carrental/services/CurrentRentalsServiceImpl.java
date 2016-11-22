package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.RentalClass;
import com.epam.carrental.entity.RentedCar;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.RentalClassRepository;
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
    RentalClassRepository rentalClassRepository;

    @Autowired
    RentedCarRepository rentedCarRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CarDTO> findNotRentedInClass(RentalClassDTO rentalClassDTO) {

        if(rentalClassDTO==null){
            throw new IllegalArgumentException("Rental class should be specified before");
        }

        RentalClass rentalClass=rentalClassRepository.findByName(rentalClassDTO.getName());
        List<Car> rentedCarList = rentedCarRepository.findAll().stream().map(RentedCar::getCar).collect(Collectors.toList());

        return carRepository.findAll()
                .stream()
                .filter(car -> !rentedCarList.contains(car))
                .filter(car -> car.getRentalClass().equals(rentalClass))
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
