package com.epam.carrental.services;

import com.epam.carrental.dto.RentedCarHistoryDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentalClass;
import com.epam.carrental.entity.RentedCarHistory;
import com.epam.carrental.repository.RentedCarHistoryRepository;
import org.easymock.EasyMock;
import org.modelmapper.ModelMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;

public class RentalHistoryServiceTest {

    private RentedCarHistoryRepository rentedCarHistoryRepositoryMock;
    private RentalsHistoryService rentalsHistoryService;
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeMethod
    public void setUp() {
        RentalsHistoryServiceImpl rentalsHistoryServiceImp = new RentalsHistoryServiceImpl();
        this.rentedCarHistoryRepositoryMock = createStrictMock(RentedCarHistoryRepository.class);
        rentalsHistoryServiceImp.modelMapper = modelMapper;
        rentalsHistoryServiceImp.rentedCarHistoryRepository = rentedCarHistoryRepositoryMock;
        this.rentalsHistoryService = rentalsHistoryServiceImp;
    }

    @Test(dataProvider = "provideRentedHistories")
    public void testFindByDateOfRentAndDateOfReturn(List<RentedCarHistory> histories, int expectedSize) throws Exception {
        //arrange
        EasyMock.expect(rentedCarHistoryRepositoryMock.findAll()).andReturn(histories);
        replay(rentedCarHistoryRepositoryMock);
        ZonedDateTime dateOfRent = getTime("2015-01-10T10:00:00Z");
        ZonedDateTime dateOfReturn = getTime("2015-03-25T10:00:00Z");

        //act
        List<RentedCarHistoryDTO> resultRentedCarHistoryList = rentalsHistoryService.findByDateOfRentAndDateOfReturn(dateOfRent, dateOfReturn);

        //assert
        verify(rentedCarHistoryRepositoryMock);
        Assert.assertEquals(resultRentedCarHistoryList.size(), expectedSize);
    }

    @DataProvider
    public Object[][] provideRentedHistories() {
        return new Object[][]{
                // after filtering should be 3 results
                {Arrays.asList(new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2015-08-16T15:23:01Z"), getTime("2015-08-25T15:23:01Z")),
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2014-09-16T15:23:01Z"),getTime("2014-09-26T15:23:01Z")),
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2015-01-10T10:00:00Z"),getTime("2015-03-25T10:00:00Z")), // matches
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2015-02-10T10:00:00Z"),getTime("2015-03-15T10:00:00Z")), // matches
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2015-02-15T10:00:00Z"),getTime("2015-02-25T10:00:00Z"))),3}, // matches
                // after filtering should be 0 results
                {Arrays.asList(new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2015-08-16T15:23:01Z"), getTime("2015-08-25T15:23:01Z")),
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2014-09-16T15:23:01Z"),getTime("2014-09-26T15:23:01Z")),
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2015-04-10T10:00:00Z"),getTime("2015-04-25T10:00:00Z")),
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2016-02-10T10:00:00Z"),getTime("2016-03-15T10:00:00Z")),
                        new RentedCarHistory(getCar(), getCustomer(),
                                getTime("2012-02-15T10:00:00Z"),getTime("2012-02-25T10:00:00Z"))),0},
        };
    }

    private Car getCar() {
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        return new Car("VW GOL IV", "KR12345",rentalClass);
    }

    private Customer getCustomer() {
        return new Customer("Adam Malysz", "adam.malysz@gmail.com");
    }

    private ZonedDateTime getTime(String time) {
        return ZonedDateTime.parse(time);
    }

}