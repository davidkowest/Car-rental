package com.epam.carrental.services;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentalClass;
import com.epam.carrental.entity.RentedCar;
import com.epam.carrental.repository.CarRepository;
import com.epam.carrental.repository.RentalClassRepository;
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
    private RentalClassRepository rentalClassRepositoryMock;
    private RentedCarRepository rentedCarRepositoryMock;
    private ModelMapper modelMapper=new ModelMapper();

    @BeforeMethod
    public void setUp(){
        CurrentRentalsServiceImpl currentRentalsServiceImpl = new CurrentRentalsServiceImpl();
        this.carRepositoryMock=createStrictMock(CarRepository.class);
        this.rentedCarRepositoryMock=createStrictMock(RentedCarRepository.class);
        this.rentalClassRepositoryMock=createStrictMock(RentalClassRepository.class);
        currentRentalsServiceImpl.modelMapper=modelMapper;
        currentRentalsServiceImpl.carRepository=carRepositoryMock;
        currentRentalsServiceImpl.rentedCarRepository=rentedCarRepositoryMock;
        currentRentalsServiceImpl.rentalClassRepository=rentalClassRepositoryMock;
        this.currentRentalsService=currentRentalsServiceImpl;
    }

    @Test
    public void findNotRentedInClassEconomyTest(){
        //arrange
        RentalClassDTO rentalClassDTO=new RentalClassDTO("Economy",2.45f);
        RentalClass rentalClass=modelMapper.map(rentalClassDTO,RentalClass.class);

        Car rentedCar=new Car ("VW GOL IV","KR12345",rentalClass);
        Car notRentedCar=new Car ("VW GOL V","KR12346",rentalClass);
        CarDTO notRentedCarDTO=modelMapper.map(notRentedCar,CarDTO.class);

        Customer customer=new Customer("Adam Malysz","adam.malysz@gmail.com");
        ZonedDateTime rentingDate = ZonedDateTime.of(LocalDateTime.of(2016, 10, 18, 9, 0), ZoneId.of("Europe/Warsaw"));

        List<RentedCar> rentedCarList=Arrays.asList(new RentedCar(rentedCar,customer,rentingDate));

        EasyMock.expect(rentedCarRepositoryMock.findAll()).andReturn(rentedCarList);
        replay(rentedCarRepositoryMock);
        EasyMock.expect(carRepositoryMock.findAll()).andReturn(Arrays.asList(rentedCar,notRentedCar));
        replay(carRepositoryMock);
        EasyMock.expect(rentalClassRepositoryMock.findByName("Economy")).andReturn(rentalClass);
        replay(rentalClassRepositoryMock);

        List<CarDTO> expectedNotRentedCarList=Arrays.asList(notRentedCarDTO);

        //act
        List<CarDTO> resultNotRentedCarList=currentRentalsService.findNotRentedInClass(rentalClassDTO);

        //assert
        verify(carRepositoryMock);
        verify(rentedCarRepositoryMock);
        verify(rentalClassRepositoryMock);
        Assert.assertEquals(resultNotRentedCarList,expectedNotRentedCarList);
    }


    @Test
    public void testFindCurrentRentals()  {
        //arrange
        RentalClass rentalClass=new RentalClass("Economy",2.45f);
        Car rentedCar=new Car ("VW GOL IV","KR12345",rentalClass);
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

    @Test(expectedExceptions = { IllegalArgumentException.class})
    public void findNotRentedInNonExistingClassTest() {
        //arrange
        Car rentedCar=new Car ("VW GOL IV","KR12345",null);

        //act
        List<RentedCarDTO> resultRentedCarList=currentRentalsService.findCurrentRentals();
    }

}