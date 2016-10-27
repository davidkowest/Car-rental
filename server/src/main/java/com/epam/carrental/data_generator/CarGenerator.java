package com.epam.carrental.data_generator;

import com.epam.carrental.dto.CarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarGenerator {

    @Value("#{'${car.models}'.split(',')}")
    private List<String> carModels;

    @Value("#{'${car.prefixes}'.split(',')}")
    private List<String> carPrefixes;

    @Value("${cars.amount}")
    private int amountOfCars;

    private int appender = 0;

    @Autowired
    RandomNumberGenerator randomNumberGenerator;

    public List<CarDTO> generateCars() {
        List<CarDTO> cars = new ArrayList<>(amountOfCars);
        for (int i = 0; i < amountOfCars; i++) {
            String model = carModels.get(randomNumberGenerator.generateWithin(0, carModels.size()));
            String number = carPrefixes.get(randomNumberGenerator.generateWithin(0, carPrefixes.size())) + appender + randomNumberGenerator.generateWithin(1000, 9999);
            cars.add(new CarDTO(model, number));
            appender++;
        }
        return cars;
    }
}
