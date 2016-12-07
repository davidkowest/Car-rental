package com.epam.carrental.data_generator;


import com.epam.carrental.dto.CustomerDTO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class CustomerGeneratorTest {

    @InjectMocks
    CustomerGenerator customerGenerator = new CustomerGenerator();
    @Mock
    RandomNumberGenerator randomNumberGenerator;

    @Test
    public void generateCustomersTest() {
        //arrange
        MockitoAnnotations.initMocks(this);
        when(randomNumberGenerator.generateWithin(anyInt(),anyInt())).thenReturn(0,0,1,1,2,2,3,3,0,0,1,1,2,2,3,3);
        customerGenerator.firstNames = Arrays.asList("Michelle","Ryan","Rachel","Alex");
        customerGenerator.surnames =Arrays.asList("Smith","Jones","Williams","Taylor");
        customerGenerator.amountOfCustomers =10;

        //act
        List<CustomerDTO> customerDTOs = customerGenerator.generateCustomers();

        //assert
        assertEquals(customerDTOs.size(),10);
    }
}