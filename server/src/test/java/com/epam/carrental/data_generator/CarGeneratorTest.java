package com.epam.carrental.data_generator;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class CarGeneratorTest {

    @InjectMocks
    CarGenerator carGenerator = new CarGenerator();
    @Mock
    RandomNumberGenerator randomNumberGeneratorMock;

    @Test
    public void generateCarsNumberTest() {
        //arrange
        carGenerator.carModels=Arrays.asList("Nissan 300ZX", "Jeep Cherokee", "Kia Sportage");
        carGenerator.carPrefixes = Arrays.asList("KHZ", "WAR", "RZE");
        carGenerator.amountOfCars=10;

        MockitoAnnotations.initMocks(this);
        when(randomNumberGeneratorMock.generateWithin(anyInt(),anyInt())).thenReturn(0,0,1,1,2,2,0,0,1,1,2,2);

        List<RentalClassDTO> rentalClassDTOs = Arrays.asList(new RentalClassDTO("Economy", 2.1f),new RentalClassDTO("Compact",3.4f),new RentalClassDTO("Intermediate",3.9f));

        //act
        List<CarDTO> carDTOs = carGenerator.generateCars(rentalClassDTOs);

        //assert
        assertEquals(carDTOs.size(),10);
    }

}