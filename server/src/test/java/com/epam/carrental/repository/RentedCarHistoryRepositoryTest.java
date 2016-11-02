package com.epam.carrental.repository;

import com.epam.carrental.config.DatabaseConfig;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCarHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = {DatabaseConfig.class})
@Transactional
@TestExecutionListeners({TransactionalTestExecutionListener.class })
public class RentedCarHistoryRepositoryTest  extends AbstractTestNGSpringContextTests {

    @Autowired
    private RentedCarHistoryRepository rentedCarHistoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarRepository carRepository;

    @BeforeMethod
    public void setUp() {
        Customer customer=new Customer("Donald Trump","trump@wp.pl");
        customerRepository.save(customer);
        Car car = new Car("VW GOLF 4", "KR12345");
        carRepository.save(car);
    }

    @Test(expectedExceptions = { DataIntegrityViolationException.class})
    @Rollback(true)
    public void savingTheSameHistoryTwice() {
        //arrange
        Car car=carRepository.findByRegistrationNumber("KR12345");
        Customer customer=customerRepository.findByEmail("trump@wp.pl");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime returningDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 10, 0), ZoneId.of("Europe/Warsaw"));

        RentedCarHistory firstHistory=new RentedCarHistory(car,customer,rentingDate,returningDate);
        rentedCarHistoryRepository.save(firstHistory);

        //act
        RentedCarHistory secondHistory=new RentedCarHistory(car,customer,rentingDate,returningDate);
        rentedCarHistoryRepository.save(secondHistory);



    }

    @Test(expectedExceptions = { DataIntegrityViolationException.class})
    @Rollback(true)
    public void savingHistoryWithDateOfRentGreaterThanDateOfReturn() {
        //arrange
        Car car=carRepository.findByRegistrationNumber("KR12345");
        Customer customer=customerRepository.findByEmail("trump@wp.pl");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 19, 10, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime returningDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 19, 9, 0), ZoneId.of("Europe/Warsaw"));
        //act
        RentedCarHistory rentedCarHistory=new RentedCarHistory(car,customer,rentingDate,returningDate);
        rentedCarHistoryRepository.save(rentedCarHistory);

    }



}