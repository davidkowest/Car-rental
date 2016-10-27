package com.epam.carrental.controller;

import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.services.RentedCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class RentalHistoryController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;

    @Autowired
    RentedCarService rentedCarService;

    @Autowired
    private AbstractSwingTableModel<RentedCarDTO> rentedCarHistoryTableModel;

    public void filter(ZonedDateTime dateOfRentDateTime, ZonedDateTime dateOfReturnDateTime) {

        inBackgroundWorker.execute(
                () -> rentedCarService.findByDateOfRentAndDateOfReturn(dateOfRentDateTime, dateOfReturnDateTime),
                rentedCarHistoryTableModel::setDataAndRefreshTable,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}