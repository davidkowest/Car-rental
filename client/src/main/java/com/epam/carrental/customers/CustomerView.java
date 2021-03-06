package com.epam.carrental.customers;

import com.epam.carrental.gui.view.TableTabView;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CustomerView extends TableTabView {

    @Autowired
    private CustomerController customerController;

    public CustomerView(AbstractSwingTableModel customerTableModel) {
        super(customerTableModel);
    }

    @Override
    public JPanel initView() {
        super.initView();
        addButtonToToolBar("Refresh table", customerController::refreshTableView);
        addButtonToToolBar("Add new customer", customerController::handleUserInput);
        return jPanel;
    }
}

