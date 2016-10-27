package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.CustomerController;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerView extends TableTabView {

    public CustomerView(CustomerController customerController, AbstractSwingTableModel customerTableModel) {
        super(customerTableModel);

        this.actions.put("Refresh table",customerController::refreshTableView);
        this.actions.put("Add new customer",customerController::handleUserInput);
    }

}

