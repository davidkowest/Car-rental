package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional()
    public Boolean create(CarDTO carDTO) {


        Car existingCar = carRepository.findByRegistrationNumber(carDTO.getRegistrationNumber());
        if (existingCar != null) {
            throw new IllegalArgumentException(carDTO + " exists in DB");
        }

        Car car = modelMapper.map(carDTO, Car.class);
        carRepository.save(car);

        return true;
    }

    @Override
    public List<CarDTO> readAll() {
        return carRepository.findAll().stream().map(c -> new CarDTO(c.getModel(), c.getRegistrationNumber())).collect(Collectors.toList());
    }
}
