package com.epam.carrental.services;

import com.epam.carrental.data_generator.CurrentTimeUtil;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.*;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.CustomerRepository;
import com.epam.carrental.repository.RentedCarHistoryRepository;
import com.epam.carrental.repository.RentedCarRepository;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.easymock.EasyMock.*;

public class RentReturnServiceImplTest {

    private CarRepository carRepositoryMock;
    private CustomerRepository customerRepositoryMock;
    private RentedCarRepository rentedCarRepositoryMock;
    private RentedCarHistoryRepository rentedCarHistoryRepositoryMock;
    private CurrentTimeUtil currentTimeUtilMock;
    private RentReturnService rentReturnService;
    private ModelMapper modelMapper=new ModelMapper();

    private Car car;
    private Customer customer;
    private ZonedDateTime dateOfRent;

    @BeforeMethod
    public void setUp(){
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        this.car=new Car ("VW GOL IV","KR12345",rentalClass);
        this.customer=new Customer("Adam Malysz","adam.malysz@gmail.com");
        this.dateOfRent=ZonedDateTime.of(LocalDateTime.of(2016, 10, 8, 10, 0), ZoneId.of("Europe/Warsaw"));

        this.carRepositoryMock=createStrictMock(CarRepository.class);
        this.customerRepositoryMock=createStrictMock(CustomerRepository.class);
        this.rentedCarRepositoryMock=createStrictMock(RentedCarRepository.class);
        this.rentedCarHistoryRepositoryMock=createStrictMock(RentedCarHistoryRepository.class);
        this.currentTimeUtilMock=createStrictMock(CurrentTimeUtil.class);

        RentReturnServiceImpl rentReturnServiceImpl = new RentReturnServiceImpl();
        rentReturnServiceImpl.modelMapper=modelMapper;
        rentReturnServiceImpl.carRepository=carRepositoryMock;
        rentReturnServiceImpl.customerRepository=customerRepositoryMock;
        rentReturnServiceImpl.rentedCarRepository=rentedCarRepositoryMock;
        rentReturnServiceImpl.rentedCarHistoryRepository=rentedCarHistoryRepositoryMock;
        rentReturnServiceImpl.currentTimeUtil=currentTimeUtilMock;

        this.rentReturnService=rentReturnServiceImpl;
    }

    private CarDTO getCarDTO(){
        return modelMapper.map(this.car,CarDTO.class);
    }
    private CustomerDTO getCustomerDTO(){
        return modelMapper.map(this.customer,CustomerDTO.class);
    }

    @Test
    public void rentCarForCustomerTest() {
        //arrange
        RentedCarDTO rentedCarDTO=new RentedCarDTO(getCarDTO(),getCustomerDTO());
        RentedCar rentedCar = new RentedCar(car, customer,dateOfRent);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(this.car.getRegistrationNumber())).andReturn(this.car);
        replay(carRepositoryMock);

        EasyMock.expect(customerRepositoryMock.findByEmail(getCustomerDTO().getEmail())).andReturn(customer);
        replay(customerRepositoryMock);

        EasyMock.expect(currentTimeUtilMock.getCurrentTime()).andReturn(dateOfRent);
        replay(currentTimeUtilMock);

        EasyMock.expect(rentedCarRepositoryMock.save(rentedCar)).andReturn(rentedCar);
        replay(rentedCarRepositoryMock);

        //act
        rentReturnService.rentCarForCustomer(rentedCarDTO);

        //assert
        verify(rentedCarRepositoryMock);
        verify(carRepositoryMock);
        verify(customerRepositoryMock);
        verify(currentTimeUtilMock);
    }


    @Test
    public void returnRentedCarTest()  {
        //arrange
        ZonedDateTime dateOfReturn= ZonedDateTime.of(LocalDateTime.of(2016, 11, 9, 8, 0), ZoneId.of("Europe/Warsaw"));

        RentedCarDTO rentedCarDTO=new RentedCarDTO(getCarDTO(),getCustomerDTO(),dateOfRent);
        RentedCar rentedCar = new RentedCar(car, customer,dateOfRent);
        RentedCarHistory rentedCarHistory = new RentedCarHistory(car,customer,dateOfRent,dateOfReturn);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(car.getRegistrationNumber())).andReturn(car);
        replay(carRepositoryMock);

        EasyMock.expect(customerRepositoryMock.findByEmail(getCustomerDTO().getEmail())).andReturn(customer);
        replay(customerRepositoryMock);

        EasyMock.expect(currentTimeUtilMock.getCurrentTime()).andReturn(dateOfReturn);
        replay(currentTimeUtilMock);

        EasyMock.expect(rentedCarRepositoryMock.findByCarAndCustomerAndDateOfRent(car, customer, dateOfRent)).andReturn(rentedCar);
        rentedCarRepositoryMock.deleteByCar(car);
        EasyMock.expectLastCall();
        replay(rentedCarRepositoryMock);

        EasyMock.expect(rentedCarHistoryRepositoryMock.save(rentedCarHistory)).andReturn(rentedCarHistory);
        replay(rentedCarHistoryRepositoryMock);

        //act
        rentReturnService.returnRentedCar(rentedCarDTO);

        //assert
        verify(rentedCarHistoryRepositoryMock);
        verify(rentedCarRepositoryMock);
        verify(carRepositoryMock);
        verify(customerRepositoryMock);
        verify(currentTimeUtilMock);
    }
}