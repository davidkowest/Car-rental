package com.epam.carrental.rentals;


import com.epam.carrental.cars.Car;
import com.epam.carrental.cars.CarRepository;
import com.epam.carrental.config.DatabaseConfig;
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

import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {DatabaseConfig.class})
public class RentedCarRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private RentedCarRepository rentedCarRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RentalClassRepository rentalClassRepository;

    @BeforeMethod
    public void setUp() {
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        rentalClassRepository.save(rentalClass);

        Car car = new Car("VW GOLF 4", "KR12345",rentalClass);
        carRepository.save(car);

        Customer customer = new Customer("Tom Cruse", "tom.cruse@wp.pl");
        customerRepository.save(customer);
    }

    @Test
    public void timeZoneMoscowToWarsawTest() {
        //arrange
        Car car = carRepository.findByRegistrationNumber("KR12345");
        Customer customer = customerRepository.findByEmail("tom.cruse@wp.pl");

        ZonedDateTime moscowStartRentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 10, 0), ZoneId.of("Europe/Moscow"));
        ZonedDateTime expectedPolandRentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime plannedReturnDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 23, 10, 0), ZoneId.of("Europe/Moscow"));

        //act
        RentedCar rentedCar = new RentedCar(car, customer, moscowStartRentingDate,plannedReturnDate);
        rentedCarRepository.save(rentedCar);

        //assert
        ZonedDateTime resultDate = rentedCarRepository.findAll().get(0).getStartDate().withZoneSameInstant(ZoneId.of("Europe/Warsaw"));
        assertEquals(resultDate, expectedPolandRentingDate);
    }

    @Test(expectedExceptions = {DataIntegrityViolationException.class})
    public void rentingTheSameCarTwiceTest() {
        //arrange
        Car car = carRepository.findByRegistrationNumber("KR12345");
        Customer customer = customerRepository.findByEmail("tom.cruse@wp.pl");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime plannedReturnDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 23, 10, 0), ZoneId.of("Europe/Moscow"));

        RentedCar firstRenting = new RentedCar(car, customer, rentingDate,plannedReturnDate);
        rentedCarRepository.save(firstRenting);

        //act
        RentedCar secondRenting = new RentedCar(car, customer, rentingDate,plannedReturnDate);
        rentedCarRepository.save(secondRenting);

        //assert ExpectedException
    }

    @Test
    public void findByCarAndCustomerAndDateOfRentTest() {
        //arrange
        Car car = carRepository.findByRegistrationNumber("KR12345");
        Customer customer = customerRepository.findByEmail("tom.cruse@wp.pl");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime plannedReturnDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 23, 10, 0), ZoneId.of("Europe/Moscow"));

        RentedCar rentedCar = new RentedCar(car, customer, rentingDate,plannedReturnDate);
        rentedCarRepository.save(rentedCar);

        //act
        RentedCar retrievedCar = rentedCarRepository.findByCarAndCustomerAndDateOfRent(car, customer, rentingDate);

        //assert
        assertEquals(retrievedCar,rentedCar);
    }
}
