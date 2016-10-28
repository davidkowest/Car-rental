package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void create(CarDTO carDTO) {
        Car existingCar = carRepository.findByRegistrationNumber(carDTO.getRegistrationNumber());
        if (existingCar != null) {
            throw new IllegalArgumentException(carDTO + " exists in DB");
        }
        Car car = modelMapper.map(carDTO, Car.class);
        carRepository.save(car);
    }

    @Override
    public List<CarDTO> readAll() {
        Type listType = new TypeToken<List<CarDTO>>() {}.getType();
        return modelMapper.map(carRepository.findAll(),listType);
    }


}
