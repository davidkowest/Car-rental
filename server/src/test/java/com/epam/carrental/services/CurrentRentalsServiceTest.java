package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentedCar;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.RentedCarRepository;
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

import static org.easymock.EasyMock.*;

public class CurrentRentalsServiceTest {


    private CurrentRentalsService currentRentalsService;
    private CarRepository carRepositoryMock;
    private RentedCarRepository rentedCarRepositoryMock;
    private ModelMapper modelMapper=new ModelMapper();

    @BeforeMethod
    public void setUp(){
        CurrentRentalsServiceImpl currentRentalsServiceImpl = new CurrentRentalsServiceImpl();
        this.carRepositoryMock=createStrictMock(CarRepository.class);
        this.rentedCarRepositoryMock=createStrictMock(RentedCarRepository.class);
        currentRentalsServiceImpl.modelMapper=modelMapper;
        currentRentalsServiceImpl.carRepository=carRepositoryMock;
        currentRentalsServiceImpl.rentedCarRepository=rentedCarRepositoryMock;
        this.currentRentalsService=currentRentalsServiceImpl;
    }

    @Test
    public void testFindNotRented(){
        //arrange
        Car rentedCar=new Car ("VW GOL IV","KR12345");
        Car notRentedCar=new Car ("VW GOL V","KR12346");
        CarDTO notRentedCarDTO=modelMapper.map(notRentedCar,CarDTO.class);

        Customer customer=new Customer("Adam Malysz","adam.malysz@gmail.com");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));

        List<RentedCar> rentedCarList=Arrays.asList(new RentedCar(rentedCar,customer,rentingDate));

        EasyMock.expect(rentedCarRepositoryMock.findAll()).andReturn(rentedCarList);
        replay(rentedCarRepositoryMock);
        EasyMock.expect(carRepositoryMock.findAll()).andReturn(Arrays.asList(rentedCar,notRentedCar));
        replay(carRepositoryMock);

        List<CarDTO> expectedNotRentedCarList=Arrays.asList(notRentedCarDTO);

        //act
        List<CarDTO> resultNotRentedCarList=currentRentalsService.findNotRented();

        //assert
        verify(carRepositoryMock);
        verify(rentedCarRepositoryMock);
        Assert.assertEquals(resultNotRentedCarList,expectedNotRentedCarList);
    }


    @Test
    public void testFindCurrentRentals()  {
        //arrange
        Car rentedCar=new Car ("VW GOL IV","KR12345");
        Customer customer=new Customer("Adam Malysz","adam.malysz@gmail.com");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));

        RentedCar currentRentedCar=new RentedCar(rentedCar,customer,rentingDate);
        RentedCarDTO currentRentedCarDTO=modelMapper.map(currentRentedCar,RentedCarDTO.class);

        EasyMock.expect(rentedCarRepositoryMock.findAll()).andReturn(Arrays.asList(currentRentedCar));
        replay(rentedCarRepositoryMock);


        List<RentedCarDTO> expectedRentedCarsList=Arrays.asList(currentRentedCarDTO);

        //act
        List<RentedCarDTO> resultRentedCarList=currentRentalsService.findCurrentRentals();

        //assert
        verify(rentedCarRepositoryMock);
        Assert.assertEquals(resultRentedCarList,expectedRentedCarsList);
    }

}