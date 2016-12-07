package com.epam.carrental.data_generator;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class CarGenerator {

    @Value("${car.models}")
    List<String> carModels;

    @Value("${car.prefixes}")
    List<String> carPrefixes;

    @Value("${cars.amount}")
    int amountOfCars;

    private int appender = 0;

    @Autowired
    private RandomNumberGenerator randomNumberGenerator;

    public List<CarDTO> generateCars(List<RentalClassDTO> rentalClassDTOs) {
        List<CarDTO> cars = new ArrayList<>(amountOfCars);
        for (int i = 0; i < amountOfCars; i++) {
            String model = carModels.get(randomNumberGenerator.generateWithin(0, carModels.size()));
            String number = carPrefixes.get(randomNumberGenerator.generateWithin(0, carPrefixes.size())) + appender + randomNumberGenerator.generateWithin(1000, 9999);
            RentalClassDTO rentalClass = rentalClassDTOs.get(randomNumberGenerator.generateWithin(0, rentalClassDTOs.size()));
            cars.add(new CarDTO(model, number, rentalClass));
            appender++;
        }
        return cars;
    }
}
