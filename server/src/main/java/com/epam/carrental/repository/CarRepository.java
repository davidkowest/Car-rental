package com.epam.carrental.repository;

import com.epam.carrental.entity.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    @Override
    List<Car> findAll();

    default Car findByRegistrationNumber(String registrationNumber){
        return findAll()
                .stream()
                .filter(c -> c.getRegistrationNumber().equals(registrationNumber))
                .findFirst().orElse(null);
    }
}
