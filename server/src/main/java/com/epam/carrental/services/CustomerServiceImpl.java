package com.epam.carrental.services;

import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Boolean create(CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findByEmail(customerDTO.getEmail());
        if (existingCustomer != null) {
            throw new IllegalArgumentException(customerDTO + " exists in DB");
        }

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public List<CustomerDTO> readAll() {
        return customerRepository.findAll().stream().map(c -> new CustomerDTO(c.getName(), c.getEmail())).collect(Collectors.toList());
    }
}

