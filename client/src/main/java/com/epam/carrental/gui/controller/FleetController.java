package com.epam.carrental.gui.controller;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.table.DefaultTableModel;
import java.util.concurrent.Callable;

@Component
public class FleetController implements DAOController{

    private final Object[] columnNames = {"Car Number", "CarModel"};
    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private CarService carService;
    @Autowired
    private MessageView messageView;

    public DefaultTableModel getModel() {
        Object[][] allCars = carService.getAllCarsForJTable();
        return new DefaultTableModel(allCars, columnNames);
    }

    public void addCarToDB(String ... dtoFields) {
        CarDTO carDTO=new CarDTO(dtoFields[0],dtoFields[1]);

        Callable<Boolean> task = () -> carService.create(carDTO);

        inBackgroundWorker.execute(task, r -> {}, e -> {
            messageView.showErrorMessage(e.getCause().getMessage());
        });
    }

}


