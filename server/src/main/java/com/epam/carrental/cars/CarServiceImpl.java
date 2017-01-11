package com.epam.carrental.cars;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.rental_classes.RentalClass;
import com.epam.carrental.rental_classes.RentalClassRepository;
import com.epam.carrental.services.CarService;
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
    RentalClassRepository rentalClassRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void create(CarDTO carDTO) {
        if(carDTO.getRentalClass()==null){
            throw new IllegalArgumentException("Rental class should be specified before");
        }
        RentalClass rentalClass=rentalClassRepository.findByName(carDTO.getRentalClass().getName());
        Car existingCar = carRepository.findByRegistrationNumber(carDTO.getRegistrationNumber());
        if (existingCar != null) {
            throw new IllegalArgumentException(carDTO + " exists in DB");
        }

        Car car = modelMapper.map(carDTO, Car.class);
        car.setRentalClass(rentalClass);
        carRepository.save(car);
    }

    @Override
    public List<CarDTO> readAll() {
        Type listType = new TypeToken<List<CarDTO>>() {}.getType();
        return modelMapper.map(carRepository.findAll(),listType);
    }


}
