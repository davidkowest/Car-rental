package com.epam.carrental.services;

import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.repository.CustomerRepository;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

public class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerRepository customerRepositoryMock;
    private ModelMapper modelMapper=new ModelMapper();

    @BeforeMethod
    public void setUp(){
        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
        this.customerRepositoryMock=createStrictMock(CustomerRepository.class);
        customerServiceImpl.modelMapper=modelMapper;
        customerServiceImpl.customerRepository=customerRepositoryMock;
        this.customerService=customerServiceImpl;
    }

    @Test
    public void customerCreateTest() {
        //arrange
        CustomerDTO customerDTO=new CustomerDTO("Adam Malysz","adam.malysz@gmail.com");
        Customer customer=modelMapper.map(customerDTO, Customer.class);

        EasyMock.expect(customerRepositoryMock.findByEmail(customerDTO.getEmail())).andReturn(null);
        EasyMock.expect(customerRepositoryMock.save(customer)).andReturn(customer);
        replay(customerRepositoryMock);

        //act
        customerService.create(customerDTO);
        //assert
        verify(customerRepositoryMock);
    }


    @Test(expectedExceptions = { IllegalArgumentException.class})
    public void theSameCarCreationTwiceTest() {
        //arrange
        CustomerDTO customerDTO=new CustomerDTO("Adam Malysz","adam.malysz@gmail.com");
        Customer customer=modelMapper.map(customerDTO, Customer.class);

        EasyMock.expect(customerRepositoryMock.findByEmail(customerDTO.getEmail())).andReturn(null);
        EasyMock.expect(customerRepositoryMock.save(customer)).andReturn(customer);
        EasyMock.expect(customerRepositoryMock.findByEmail(customerDTO.getEmail())).andReturn(customer);
        EasyMock.expect(customerRepositoryMock.save(customer)).andThrow(new IllegalArgumentException());
        replay(customerRepositoryMock);

        //act
        customerService.create(customerDTO);
        customerService.create(customerDTO);

        //assert
        verify(customerRepositoryMock);
    }

    @Test
    public void readAllCustomersTest()  {
        //arrange
        EasyMock.expect(customerRepositoryMock.findAll()).andReturn(Arrays.asList(new Customer("Adam Malysz","adam.malysz@gmail.com")));
        replay(customerRepositoryMock);
        List<CustomerDTO> expectedCustomers=Arrays.asList(new CustomerDTO("Adam Malysz","adam.malysz@gmail.com"));

        //act
        List<CustomerDTO> resultCustomers=customerService.readAll();

        //assert
        verify(customerRepositoryMock);
        Assert.assertEquals(resultCustomers,expectedCustomers);
    }

}