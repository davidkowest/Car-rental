package com.epam.carrental.controller;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.impl.CarUserInputHandler;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.services.CarService;
import com.epam.carrental.services.RentalClassService;
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

    @Autowired
    private RentalClassService rentalClassService;

    public void refreshTableView() {

        inBackgroundWorker.execute(
                carService::readAll,
                carTableModel::setDataAndRefreshTable,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void save(CarDTO carDTO) {
        inBackgroundWorker.execute(
                () -> carService.create(carDTO),
                this::refreshTableView,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void handleUserInput() {
        inBackgroundWorker.execute(
                rentalClassService::readAll,
                carUserInputHandler::handleInput,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}



