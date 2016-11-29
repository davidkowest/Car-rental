package com.epam.carrental.controller;


import com.epam.carrental.dto.BookedCarDTO;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.impl.BookUserInputHandler;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.models.CustomerTableModel;
import com.epam.carrental.services.BookCarService;
import com.epam.carrental.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class BookCarController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;

    @Autowired
    private BookUserInputHandler bookUserInputHandler;
    @Autowired
    private AbstractSwingTableModel<CarDTO> availableToBookCarTableModel;
    @Autowired
    private CustomerTableModel customerTableModel;

    @Autowired
    private BookCarService bookCarService;
    @Autowired
    private CustomerService customerService;


    public void handleUserInput(int selectedRow, ZonedDateTime startDate, ZonedDateTime endDate) {
        refreshCustomerModel();
        if (selectedRow < 0) {
            messageView.showErrorMessage("No rows selected!");
        } else {
            CarDTO carDTO = availableToBookCarTableModel.getModel(selectedRow);
            bookUserInputHandler.handleInputUsing(carDTO, startDate, endDate);
        }
    }

    public void refreshTableView(ZonedDateTime startDate, ZonedDateTime endDate, RentalClassDTO rentalClassDTO) {
        inBackgroundWorker.execute(
                () -> bookCarService.findAvailableToBook(startDate, endDate, rentalClassDTO),
                availableToBookCarTableModel::setDataAndRefreshTable,
                e -> messageView.showErrorMessage(e.getCause().getMessage())
        );
    }

    public void bookCar(BookedCarDTO bookedCarDTO) {
        inBackgroundWorker.execute(
                () -> bookCarService.bookCar(bookedCarDTO),
                () -> refreshTableView(bookedCarDTO.getStartDate(), bookedCarDTO.getEndDate(), bookedCarDTO.getCar().getRentalClass()),
                e -> messageView.showErrorMessage(e.getCause().getMessage())
        );
    }

    private void refreshCustomerModel() {
        inBackgroundWorker.execute(
                customerService::readAll,
                customerTableModel::setDataAndRefreshTable,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}
