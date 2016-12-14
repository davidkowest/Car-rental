package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.CustomerController;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerView extends TableTabView {

    public CustomerView(CustomerController customerController, AbstractSwingTableModel customerTableModel) {
        super(customerTableModel);

        addButtonToToolBar("Refresh table",customerController::refreshTableView);
        addButtonToToolBar("Add new customer",customerController::handleUserInput);
    }

}

