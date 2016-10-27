package com.epam.carrental.data_generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataGeneratorOnStartup {

    @Autowired
    DataGenerator dataGenerator;

    @Value("${need.generate.data}")
    private Boolean newDataRequired;

    @PostConstruct
    public void generateInitialData() {
        if (newDataRequired) {
            dataGenerator.generateAndSaveData();
        }
    }
}