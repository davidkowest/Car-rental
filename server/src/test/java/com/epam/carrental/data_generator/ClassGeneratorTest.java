package com.epam.carrental.data_generator;

import com.epam.carrental.dto.RentalClassDTO;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassGeneratorTest {

    @Test
    public void generateRentalClasses() {
        //arrange
        ClassGenerator classGenerator = new ClassGenerator();
        Map<String, Float> classesMap = new HashMap<>();
        classesMap.put("Economy", 2.1f);
        classesMap.put("Compact", 3.4f);
        classesMap.put("Intermediate", 3.9f);
        classGenerator.classesWithRates = classesMap;

        //act
        List<RentalClassDTO> rentalClassDTOs = classGenerator.generateRentalClasses();

        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(rentalClassDTOs.contains(new RentalClassDTO("Economy", 2.1f)),"Economy rental class");
        sa.assertTrue(rentalClassDTOs.contains(new RentalClassDTO("Compact", 3.4f)),"Compact rental class");
        sa.assertTrue(rentalClassDTOs.contains(new RentalClassDTO("Intermediate", 3.9f)),"Intermediate rental class");
        sa.assertAll();
    }
}