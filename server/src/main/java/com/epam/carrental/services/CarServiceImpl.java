package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.model.Car;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.StreamSupport;

public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private Validator<CarDTO> carDTOValidator;

    @Override
    @Transactional
    public Boolean create(CarDTO carDTO) {

        if(!carDTOValidator.validate(carDTO)){
            throw new IllegalArgumentException("Input fields cannot be empty");
        }

        Car existingCar = carRepository.findByRegistrationNumberAndModel(carDTO.getRegistrationNumber(), carDTO.getModel());
        if (existingCar != null) {
            throw new IllegalArgumentException(carDTO + " exists in DB");
        }

        Car car = new Car();
        car.setModel(carDTO.getModel());
        car.setRegistrationNumber(carDTO.getRegistrationNumber());
        carRepository.save(car);
        return true;
    }

    @Override
    public Object[][] getAllCarsForJTable() {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false).map(c -> new String[]{c.getModel(), c.getRegistrationNumber()}).toArray(String[][]::new);
    }
}
