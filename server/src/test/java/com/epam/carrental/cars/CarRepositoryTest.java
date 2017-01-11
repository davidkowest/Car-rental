package com.epam.carrental.cars;

import com.epam.carrental.config.DatabaseConfig;
import com.epam.carrental.rental_classes.RentalClass;
import com.epam.carrental.rental_classes.RentalClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {DatabaseConfig.class})
public class CarRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RentalClassRepository rentalClassRepository;

    @BeforeMethod
    public void setUp() {
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        rentalClassRepository.save(rentalClass);

        Car car = new Car("VW GOLF 4", "KR12345",rentalClass);
        carRepository.save(car);
    }

    @Test(expectedExceptions = { DataIntegrityViolationException.class})
    public void savingTheSameCar() {
        //arrange
        RentalClass rentalClass=rentalClassRepository.findByName("Economy");
        Car car = new Car("VW GOLF 5", "KR12345",rentalClass);
        //act
        carRepository.save(car);
    }
}