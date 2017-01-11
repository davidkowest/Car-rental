package com.epam.carrental.rentals;

import com.epam.carrental.cars.Car;
import com.epam.carrental.customers.Customer;
import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;


public interface RentedCarRepository extends CrudRepository<RentedCar, Long> {
    @Override
    List<RentedCar> findAll();

    void deleteByCar(Car car);

    default RentedCar findByCarAndCustomerAndDateOfRent(Car car, Customer customer, ZonedDateTime dateOfRent) {
        return findAll().stream().filter(rentedCar -> rentedCar.getCar().equals(car) && rentedCar.getCustomer().equals(customer)
                && rentedCar.getStartDate().equals(dateOfRent)).findFirst().orElse(null);
    }
}

