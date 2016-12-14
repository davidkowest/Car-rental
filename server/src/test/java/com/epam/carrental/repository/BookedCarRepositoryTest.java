package com.epam.carrental.repository;

import com.epam.carrental.config.DatabaseConfig;
import com.epam.carrental.entity.BookedCar;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentalClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

@ContextConfiguration(classes = {DatabaseConfig.class})
public class BookedCarRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private BookedCarRepository bookedCarRepository;
    @Autowired
    private RentalClassRepository rentalClassRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private BookedCar bookedCar;

    @BeforeMethod
    public void setUp() {
        RentalClass rentalClass = new RentalClass("Economy", 2.45f);
        rentalClassRepository.save(rentalClass);
        Car car = new Car("VW GOLF 4", "KR12345", rentalClass);
        carRepository.save(car);
        Customer customer = new Customer("Robin Bobbin", "Bobbin@gmail.com");
        customerRepository.save(customer);
        bookedCar = new BookedCar(car, customer, ZonedDateTime.parse("2016-08-20T10:10:00Z"), ZonedDateTime.parse("2016-08-25T10:10:00Z"));
        bookedCarRepository.save(bookedCar);
    }

    @Test(expectedExceptions = DataIntegrityViolationException.class)
    public void savingTheSameCarInSamePeriod() {
        //arrange
        Car car = carRepository.findByRegistrationNumber("KR12345");
        Customer customer = customerRepository.findByEmail("Bobbin@gmail.com");
        BookedCar bookedCar2 = new BookedCar(car, customer, ZonedDateTime.parse("2016-08-20T10:10:00Z"), ZonedDateTime.parse("2016-08-25T10:10:00Z"));

        //act
        bookedCarRepository.save(bookedCar2);

        // assert expectedExceptions
    }

    @Test
    public void findAllTest() {
        // arrange dd
        List<BookedCar> expectedBookedCars = Collections.singletonList(bookedCar);

        //act
        List<BookedCar> actualBookedCars = bookedCarRepository.findAll();

        //assert
        assertEquals(actualBookedCars, expectedBookedCars);
    }

}
