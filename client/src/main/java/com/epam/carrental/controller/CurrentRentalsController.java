package com.epam.carrental.controller;

import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.models.RentedCarTableModel;
import com.epam.carrental.services.RentedCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrentRentalsController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;

    @Autowired
    private RentedCarService rentedCarService;

    @Autowired
    private RentedCarTableModel rentedCarTableModel;

    @Autowired
    private MessageView messageView;

    public void refreshTableView() {
        inBackgroundWorker.execute(
                () -> rentedCarService.findAll(),
                rentedCarDTOs -> {
                    rentedCarTableModel.setData(rentedCarDTOs);
                    rentedCarTableModel.fireTableDataChanged();
                },
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }


}
