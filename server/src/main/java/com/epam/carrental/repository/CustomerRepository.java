package com.epam.carrental.repository;

import com.epam.carrental.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository  extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
    List<Customer> findAll();
}

