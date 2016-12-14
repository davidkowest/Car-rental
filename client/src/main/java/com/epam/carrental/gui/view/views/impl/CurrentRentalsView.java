package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.CurrentRentalsController;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class CurrentRentalsView extends TableTabView {

    public CurrentRentalsView(AbstractSwingTableModel rentedCarTableModel, CurrentRentalsController currentRentalsController) {
        super(rentedCarTableModel);

        addButtonToToolBar("Refresh table", currentRentalsController::refreshTableView);
        addButtonToToolBar("Return", () -> currentRentalsController.handleUserInput(getSelectedRow()));
    }
}
