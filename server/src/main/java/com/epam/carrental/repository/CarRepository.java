package com.epam.carrental.repository;

import com.epam.carrental.entity.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CarRepository extends CrudRepository<Car, Long> {

    Car findByRegistrationNumber(String registrationNumber);
    List<Car> findAll();
}
