package com.epam.carrental.repository;


import com.epam.carrental.config.DatabaseConfig;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ContextConfiguration(classes = {DatabaseConfig.class})
public class RentedCarRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private RentedCarRepository rentedCarRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeMethod
    public void setUp() {
        Car car = new Car("VW GOLF 4", "KR12345");
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

        //act
        RentedCar rentedCar = new RentedCar(car, customer, moscowStartRentingDate);
        rentedCarRepository.save(rentedCar);

        //assert
        ZonedDateTime resultDate = rentedCarRepository.findAll().get(0).getDateOfRent().withZoneSameInstant(ZoneId.of("Europe/Warsaw"));
        Assert.assertEquals(resultDate, expectedPolandRentingDate);
    }

    @Test(expectedExceptions = {DataIntegrityViolationException.class})
    public void rentingTheSameCarTwiceTest() {
        //arrange
        Car car = carRepository.findByRegistrationNumber("KR12345");
        Customer customer = customerRepository.findByEmail("tom.cruse@wp.pl");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));

        RentedCar firstRenting = new RentedCar(car, customer, rentingDate);
        rentedCarRepository.save(firstRenting);

        //act
        RentedCar secondRenting = new RentedCar(car, customer, rentingDate);
        rentedCarRepository.save(secondRenting);
    }

}
