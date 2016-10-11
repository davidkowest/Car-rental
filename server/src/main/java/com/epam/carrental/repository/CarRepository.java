package com.epam.carrental.repository;

import com.epam.carrental.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CarRepository extends CrudRepository<Car, Long> {

    Car findByRegistrationNumberAndModel(String registrationNumber,String model);
}
