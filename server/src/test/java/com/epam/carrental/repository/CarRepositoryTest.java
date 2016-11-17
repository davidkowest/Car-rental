package com.epam.carrental.repository;

import com.epam.carrental.config.DatabaseConfig;
import com.epam.carrental.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {DatabaseConfig.class})
public class CarRepositoryTest  extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CarRepository carRepository;

    @BeforeMethod
    public void setUp() {
        Car car = new Car("VW GOLF 4", "KR12345");
        carRepository.save(car);
    }

    @Test(expectedExceptions = { DataIntegrityViolationException.class})
    public void savingTheSameCar() {
        //arrange
        Car car = new Car("VW GOLF 5", "KR12345");
        //act
        carRepository.save(car);
    }

}