package com.epam.carrental.rental_classes;

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
public class RentalClassRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private RentalClassRepository rentalClassRepository;

    @BeforeMethod
    public void setUp() {
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        rentalClassRepository.save(rentalClass);
    }


    @Test(expectedExceptions = { DataIntegrityViolationException.class})
    public void savingExistingRentalClass() {
        //arrange
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        //act
        rentalClassRepository.save(rentalClass);
    }
}
