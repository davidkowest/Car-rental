package com.epam.carrental.customers;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository  extends CrudRepository<Customer, Long> {

    @Override
    List<Customer> findAll();

    default Customer findByEmail(String email){
        return findAll().stream().filter(c -> c.getEmail().equals(email)).findFirst().orElse(null);
    }
}

