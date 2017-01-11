package com.epam.carrental.rentals.history;

import com.epam.carrental.cars.CarRepository;
import com.epam.carrental.config.DatabaseConfig;
import com.epam.carrental.cars.Car;
import com.epam.carrental.customers.Customer;
import com.epam.carrental.customers.CustomerRepository;
import com.epam.carrental.rental_classes.RentalClass;
import com.epam.carrental.rental_classes.RentalClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ContextConfiguration(classes = {DatabaseConfig.class})
public class RentalsHistoryRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private RentalsHistoryRepository rentalsHistoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentalClassRepository rentalClassRepository;

    @BeforeMethod
    public void setUp() {
        Customer customer = new Customer("Donald Trump", "trump@wp.pl");
        customerRepository.save(customer);
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        rentalClassRepository.save(rentalClass);
        Car car = new Car("VW GOLF 4", "KR12345",rentalClass);
        carRepository.save(car);
    }

    @Test(expectedExceptions = {DataIntegrityViolationException.class})
    public void savingTheSameHistoryTwice() {
        //arrange
        Car car = carRepository.findByRegistrationNumber("KR12345");
        Customer customer = customerRepository.findByEmail("trump@wp.pl");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime returningDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 10, 0), ZoneId.of("Europe/Warsaw"));

        RentedCarHistory firstHistory = new RentedCarHistory(car, customer, rentingDate, returningDate);
        rentalsHistoryRepository.save(firstHistory);

        //act
        RentedCarHistory secondHistory = new RentedCarHistory(car, customer, rentingDate, returningDate);
        rentalsHistoryRepository.save(secondHistory);


    }

    @Test(expectedExceptions = {DataIntegrityViolationException.class})
    public void savingHistoryWithDateOfRentGreaterThanDateOfReturn() {
        //arrange
        Car car = carRepository.findByRegistrationNumber("KR12345");
        Customer customer = customerRepository.findByEmail("trump@wp.pl");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 19, 10, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime returningDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 19, 9, 0), ZoneId.of("Europe/Warsaw"));
        //act
        RentedCarHistory rentedCarHistory = new RentedCarHistory(car, customer, rentingDate, returningDate);
        rentalsHistoryRepository.save(rentedCarHistory);

    }


}