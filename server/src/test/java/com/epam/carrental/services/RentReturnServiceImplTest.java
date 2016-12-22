package com.epam.carrental.services;

import com.epam.carrental.data_generator.CurrentTimeUtil;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.*;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.CustomerRepository;
import com.epam.carrental.repository.RentalsHistoryRepository;
import com.epam.carrental.repository.RentedCarRepository;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.easymock.EasyMock.*;

public class RentReturnServiceImplTest {

    private CarRepository carRepositoryMock;
    private CustomerRepository customerRepositoryMock;
    private RentedCarRepository rentedCarRepositoryMock;
    private RentalsHistoryRepository rentalsHistoryRepositoryMock;
    private CurrentTimeUtil currentTimeUtilMock;
    private RentReturnService rentReturnService;
    private CurrentRentalsService currentRentalsServiceMock;
    private ModelMapper modelMapper = new ModelMapper();

    private Car car;
    private Customer customer;
    private ZonedDateTime dateOfRent;

    @BeforeMethod
    public void setUp() {
        RentalClass rentalClass = new RentalClass("Economy", 2.45f);
        this.car = new Car("VW GOL IV", "KR12345", rentalClass);
        this.customer = new Customer("Adam Malysz", "adam.malysz@gmail.com");
        this.dateOfRent = ZonedDateTime.of(LocalDateTime.of(2016, 10, 8, 10, 0), ZoneId.of("Europe/Warsaw"));

        this.carRepositoryMock = createStrictMock(CarRepository.class);
        this.customerRepositoryMock = createStrictMock(CustomerRepository.class);
        this.rentedCarRepositoryMock = createStrictMock(RentedCarRepository.class);
        this.rentalsHistoryRepositoryMock = createStrictMock(RentalsHistoryRepository.class);
        this.currentTimeUtilMock = createStrictMock(CurrentTimeUtil.class);
        this.currentRentalsServiceMock = createStrictMock(CurrentRentalsService.class);

        RentReturnServiceImpl rentReturnServiceImpl = new RentReturnServiceImpl();
        rentReturnServiceImpl.modelMapper = modelMapper;
        rentReturnServiceImpl.carRepository = carRepositoryMock;
        rentReturnServiceImpl.customerRepository = customerRepositoryMock;
        rentReturnServiceImpl.rentedCarRepository = rentedCarRepositoryMock;
        rentReturnServiceImpl.rentalsHistoryRepository = rentalsHistoryRepositoryMock;
        rentReturnServiceImpl.currentTimeUtil = currentTimeUtilMock;
        rentReturnServiceImpl.currentRentalsService = currentRentalsServiceMock;

        this.rentReturnService = rentReturnServiceImpl;
    }


    @Test
    public void rentCarForCustomerTest() {
        //arrange
        ZonedDateTime plannedReturnDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 23, 10, 0), ZoneId.of("Europe/Moscow"));
        RentedCarDTO rentedCarDTO = new RentedCarDTO(getCarDTO(), getCustomerDTO(), plannedReturnDate);
        RentedCar rentedCar = new RentedCar(car, customer, dateOfRent, plannedReturnDate);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(this.car.getRegistrationNumber())).andReturn(this.car);
        EasyMock.expect(customerRepositoryMock.findByEmail(getCustomerDTO().getEmail())).andReturn(customer);
        EasyMock.expect(currentTimeUtilMock.getCurrentTime()).andReturn(dateOfRent);
        EasyMock.expect(rentedCarRepositoryMock.save(rentedCar)).andReturn(rentedCar);
        EasyMock.expect(currentRentalsServiceMock.findAvailableToRent(rentedCarDTO.getCar().getRentalClass(), plannedReturnDate)).andReturn(Collections.singletonList(getCarDTO()));
        replay(rentedCarRepositoryMock, carRepositoryMock, customerRepositoryMock, currentTimeUtilMock, currentRentalsServiceMock);

        //act
        rentReturnService.rentCarForCustomer(rentedCarDTO);

        //assert
        verify(rentedCarRepositoryMock);
        verify(carRepositoryMock);
        verify(customerRepositoryMock);
        verify(currentTimeUtilMock);
    }

    @Test
    public void returnRentedCarTest() {
        //arrange
        ZonedDateTime dateOfReturn = ZonedDateTime.of(LocalDateTime.of(2016, 11, 9, 8, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime plannedReturnDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 23, 10, 0), ZoneId.of("Europe/Moscow"));

        RentedCarDTO rentedCarDTO = new RentedCarDTO(getCarDTO(), getCustomerDTO(), dateOfRent, plannedReturnDate);
        RentedCar rentedCar = new RentedCar(car, customer, dateOfRent, plannedReturnDate);
        RentedCarHistory rentedCarHistory = new RentedCarHistory(car, customer, dateOfRent, dateOfReturn);

        EasyMock.expect(carRepositoryMock.findByRegistrationNumber(car.getRegistrationNumber())).andReturn(car);
        EasyMock.expect(customerRepositoryMock.findByEmail(getCustomerDTO().getEmail())).andReturn(customer);
        EasyMock.expect(currentTimeUtilMock.getCurrentTime()).andReturn(dateOfReturn);
        EasyMock.expect(rentedCarRepositoryMock.findByCarAndCustomerAndDateOfRent(car, customer, dateOfRent)).andReturn(rentedCar);
        rentedCarRepositoryMock.deleteByCar(car);
        EasyMock.expectLastCall();

        EasyMock.expect(rentalsHistoryRepositoryMock.save(rentedCarHistory)).andReturn(rentedCarHistory);
        replay(rentalsHistoryRepositoryMock, carRepositoryMock, customerRepositoryMock, currentTimeUtilMock, rentedCarRepositoryMock);

        //act
        rentReturnService.returnRentedCar(rentedCarDTO);

        //assert
        verify(rentalsHistoryRepositoryMock);
        verify(rentedCarRepositoryMock);
        verify(carRepositoryMock);
        verify(customerRepositoryMock);
        verify(currentTimeUtilMock);
    }

    private CarDTO getCarDTO() {
        return modelMapper.map(this.car, CarDTO.class);
    }

    private CustomerDTO getCustomerDTO() {
        return modelMapper.map(this.customer, CustomerDTO.class);
    }

}