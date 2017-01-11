package com.epam.carrental.customers;

import com.epam.carrental.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {DatabaseConfig.class})
public class CustomerRepositoryTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeMethod
    public void setUp() {
        Customer customer=new Customer("Donald Trump","trump@wp.pl");
        customerRepository.save(customer);
    }


    @Test(expectedExceptions = { DataIntegrityViolationException.class})
    public void savingTheSameCustomer() {
        //arrange
        Customer customer=new Customer("Witold Trump","trump@wp.pl");
        //act
        customerRepository.save(customer);
    }
}