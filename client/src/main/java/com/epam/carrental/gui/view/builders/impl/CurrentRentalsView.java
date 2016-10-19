package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.controller.CurrentRentalsController;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class CurrentRentalsView extends TableTabView {

    public CurrentRentalsView(CurrentRentalsController currentRentalsController, AbstractSwingTableModel rentedCarTableModel) {
        super(rentedCarTableModel);
        this.actions.put("Refresh table", currentRentalsController::refreshTableView);
    }
}
