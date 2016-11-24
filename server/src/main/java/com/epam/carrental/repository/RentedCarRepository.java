package com.epam.carrental.repository;

import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCar;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;


public interface RentedCarRepository extends CrudRepository<RentedCar, Long> {
    @Override
    List<RentedCar> findAll();

    void deleteByCar(Car car);

    default RentedCar findByCarAndCustomerAndDateOfRent(Car car, Customer customer, ZonedDateTime dateOfRent) {
        return findAll().stream().filter(rentedCar -> rentedCar.getCar().equals(car) && rentedCar.getCustomer().equals(customer)
                && rentedCar.getDateOfRent().equals(dateOfRent)).findFirst().orElse(null);
    }
}

