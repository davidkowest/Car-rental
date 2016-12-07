package com.epam.carrental.data_generator;


import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class RandomNumberGeneratorTest {

    @Test
    public void generateWithinTest(){
        //arrange
        RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
        int min = 0;
        int max =10;

        //act
        int generatedNumber = randomNumberGenerator.generateWithin(min, max);

        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(generatedNumber>=min,"generated number "+generatedNumber+" should be more or equal then"+min);
        sa.assertTrue(generatedNumber<=max,"generated number "+generatedNumber+" should be less or equal then"+max);
        sa.assertAll();
    }
}