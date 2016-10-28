package com.epam.carrental.repository;

import com.epam.carrental.config.DatabaseConfig;
import com.epam.carrental.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@ContextConfiguration(classes = {DatabaseConfig.class})
@Transactional
@TestExecutionListeners({TransactionalTestExecutionListener.class })
public class CustomerRepositoryTest  extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeMethod
    public void setUp() {
        Customer customer=new Customer("Donald Trump","trump@wp.pl");
        customerRepository.save(customer);
    }


    @Test(expectedExceptions = { DataIntegrityViolationException.class})
    @Rollback(true)
    public void savingTheSameCustomer() {
        //arrange
        Customer customer=new Customer("Witold Trump","trump@wp.pl");
        //act
        customerRepository.save(customer);
    }
}