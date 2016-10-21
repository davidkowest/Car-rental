package com.epam.carrental.repository;

import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.RentedCar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface RentedCarRepository extends CrudRepository<RentedCar, Long>{
    @Override
    List<RentedCar> findAll();

    void deleteByCar(Car car);
}

