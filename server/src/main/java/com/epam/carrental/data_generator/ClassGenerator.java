package com.epam.carrental.data_generator;

import com.epam.carrental.dto.RentalClassDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ClassGenerator {

    @Value("#{'${rental.classes}'.split(',')}")
    private List<String> classes;

    @Value("#{'${rental.rate}'.split(',')}")
    private List<Float> carHourlyRate;

    public List<RentalClassDTO> generateRentalClasses() {
        List<RentalClassDTO> rentalClasses= new LinkedList<>();
        for (int i = 0; i < classes.size(); i++) {
            rentalClasses.add(new RentalClassDTO(classes.get(i),carHourlyRate.get(i)));
        }
        return rentalClasses;
    }
}