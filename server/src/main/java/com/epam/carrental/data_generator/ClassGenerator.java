package com.epam.carrental.data_generator;

import com.epam.carrental.dto.RentalClassDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class ClassGenerator {

    @Value("#{PropertySplitter.map('${rental.classes.rate.map}')}")
    Map<String, Float> classesWithRates;

    public List<RentalClassDTO> generateRentalClasses() {
        List<RentalClassDTO> rentalClasses = new LinkedList<>();
        for (String key : classesWithRates.keySet()) {
            rentalClasses.add(new RentalClassDTO(key, classesWithRates.get(key)));
        }
        return rentalClasses;
    }
}