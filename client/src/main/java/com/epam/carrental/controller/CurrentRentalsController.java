package com.epam.carrental.controller;

import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.models.RentedCarTableModel;
import com.epam.carrental.services.CurrentRentalsService;
import com.epam.carrental.services.RentReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentRentalsController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;

    @Autowired
    private CurrentRentalsService currentRentalsService;

    @Autowired
    private RentReturnService rentReturnService;

    @Autowired
    private RentedCarTableModel rentedCarTableModel;

    @Autowired
    private MessageView messageView;

    public void refreshTableView() {

        inBackgroundWorker.execute(
                currentRentalsService::findCurrentRentals,
                rentedCarTableModel::setDataAndRefreshTable,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void handleUserInput(int selectedRow) {
        if (selectedRow < 0) {
            messageView.showErrorMessage("No rows selected!");
        }else {
            RentedCarDTO rentedCarDTO = rentedCarTableModel.getModel(selectedRow);
            inBackgroundWorker.execute(
                    () ->  rentReturnService.returnRentedCar(rentedCarDTO),
                    this::refreshTableView,
                    e -> messageView.showErrorMessage(e.getCause().getMessage()));
        }
    }
}
