package com.epam.carrental.data_generator;

import com.epam.carrental.dto.RentalClassDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ClassGenerator {

    @Value("#{PropertySplitter.map('${rental.classes.rate.map}')}")
    Map<String, Float> classesWithRates;

    public List<RentalClassDTO> generateRentalClasses() {
        return classesWithRates.entrySet()
                .stream()
                .map(mapEntry ->new RentalClassDTO(mapEntry.getKey(),mapEntry.getValue()))
                .collect(Collectors.toList());
    }
}