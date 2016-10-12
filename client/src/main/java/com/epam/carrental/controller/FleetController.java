package com.epam.carrental.controller;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.models.CarTableModel;
import com.epam.carrental.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class FleetController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private CarService carService;
    @Autowired
    private MessageView messageView;
    @Autowired
    private CarTableModel carTableModel;

    @PostConstruct
    public void setInitialFleetTableModel(){
        try {
            carTableModel.setData(carService.readAll());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void save(CarDTO carDTO) {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CarDTO>> violations = validator.validate(carDTO);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Input fields cannot be empty");
        }

        inBackgroundWorker.execute(
                () -> carService.create(carDTO),
                o -> carTableModel.setData(carService.readAll()),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}



