package com.epam.carrental.services;

import com.epam.carrental.dto.RentedCarHistoryDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCarHistory;
import com.epam.carrental.repository.RentedCarHistoryRepository;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class RentalHistoryServiceTest {

    private RentedCarHistoryRepository rentedCarHistoryRepositoryMock;
    private RentalsHistoryService rentalsHistoryService;
    private ModelMapper modelMapper=new ModelMapper();

    @BeforeMethod
    public void setUp(){
        RentalsHistoryServiceImpl rentalsHistoryServiceImp = new RentalsHistoryServiceImpl();
        this.rentedCarHistoryRepositoryMock=createStrictMock(RentedCarHistoryRepository.class);
        rentalsHistoryServiceImp.modelMapper=modelMapper;
        rentalsHistoryServiceImp.rentedCarHistoryRepository=rentedCarHistoryRepositoryMock;
        this.rentalsHistoryService=rentalsHistoryServiceImp;
    }

    @Test
    public void testFindByDateOfRentAndDateOfReturn() throws Exception {
        //arrange
        Car rentedCar=new Car ("VW GOL IV","KR12345");
        Customer customer=new Customer("Adam Malysz","adam.malysz@gmail.com");
        ZonedDateTime dateOfRent = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        ZonedDateTime dateOfReturn = ZonedDateTime.of(LocalDateTime.of(2016, 11, 18, 9, 0), ZoneId.of("Europe/Warsaw"));
        //act
        RentedCarHistory rentedCarHistory=new RentedCarHistory(rentedCar,customer,dateOfRent,dateOfReturn);
        RentedCarHistoryDTO rentedCarHistoryDTO=modelMapper.map(rentedCarHistory,RentedCarHistoryDTO.class);

        List<RentedCarHistory> rentedCarHistoryList=Arrays.asList(rentedCarHistory);


        EasyMock.expect(rentedCarHistoryRepositoryMock.findByDateOfRentAndDateOfReturn(dateOfRent,dateOfReturn)).andReturn(rentedCarHistoryList);
        replay(rentedCarHistoryRepositoryMock);

        List<RentedCarHistoryDTO> expectedRentedCarHistoryList=Arrays.asList(rentedCarHistoryDTO);

        //act
        List<RentedCarHistoryDTO> resultRentedCarHistoryList=rentalsHistoryService.findByDateOfRentAndDateOfReturn(dateOfRent,dateOfReturn);

        //assert
        verify(rentedCarHistoryRepositoryMock);
        Assert.assertEquals(resultRentedCarHistoryList,expectedRentedCarHistoryList);
    }

}