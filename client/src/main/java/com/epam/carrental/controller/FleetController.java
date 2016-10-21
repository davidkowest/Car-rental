package com.epam.carrental.controller;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.impl.CarUserInputHandler;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FleetController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;

    @Autowired
    private AbstractSwingTableModel<CarDTO> carTableModel;
    @Autowired
    private CarUserInputHandler carUserInputHandler;
    @Autowired
    private CarService carService;


    public void refreshTableView() {
        inBackgroundWorker.execute(
                () -> carService.readAll(),
                carDTOs -> {
                    carTableModel.setData(carDTOs);
                    carTableModel.fireTableDataChanged();
                },
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void save(CarDTO carDTO) {
        inBackgroundWorker.execute(
                () -> carService.create(carDTO),
                o -> refreshTableView(),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void handleUserInput() {
        carUserInputHandler.handleInput();
    }
}



