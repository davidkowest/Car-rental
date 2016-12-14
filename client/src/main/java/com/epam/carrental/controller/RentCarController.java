package com.epam.carrental.controller;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.impl.RentalUserInputHandler;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import com.epam.carrental.services.CurrentRentalsService;
import com.epam.carrental.services.CustomerService;
import com.epam.carrental.services.RentReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class RentCarController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;
    @Autowired
    private AbstractSwingTableModel<CarDTO> availableToRentCarTableModel;
    @Autowired
    private AbstractSwingTableModel<CustomerDTO> customerTableModel;
    @Autowired
    private RentReturnService rentReturnService;
    @Autowired
    private CurrentRentalsService currentRentalsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RentalUserInputHandler rentalUserInputHandler;

    public void refreshTableView(RentalClassDTO rentalClassDTO, ZonedDateTime dateUntil) {
        inBackgroundWorker.execute(
                () -> currentRentalsService.findAvailableToRent(rentalClassDTO, dateUntil),
                availableToRentCarTableModel::setDataAndRefreshTable,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void save(RentedCarDTO rentedCarDTO) {
        inBackgroundWorker.execute(
                () -> rentReturnService.rentCarForCustomer(rentedCarDTO),
                () -> refreshTableView(rentedCarDTO.getCar().getRentalClass(),rentedCarDTO.getPlannedDateOfReturn()),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void handleUserInput(int selectedRow) {
        refreshCustomerModel();
        if (selectedRow < 0) {
            messageView.showErrorMessage("No rows selected!");
        } else {
            CarDTO carDTO = availableToRentCarTableModel.getModel(selectedRow);
            rentalUserInputHandler.handleInputUsing(carDTO);
        }
    }

    private void refreshCustomerModel() {
        inBackgroundWorker.execute(
                customerService::readAll,
                customerTableModel::setDataAndRefreshTable,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}


