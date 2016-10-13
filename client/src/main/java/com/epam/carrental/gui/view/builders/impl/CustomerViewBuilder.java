package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.controller.CustomerController;
import com.epam.carrental.gui.view.hanlders.impl.CustomerUserInputHandler;
import com.epam.carrental.models.CustomerTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerViewBuilder extends TableTabViewBuilder {

    @Autowired
    public CustomerViewBuilder(CustomerTableModel customerTableModel, CustomerUserInputHandler customerUserInputHandler, CustomerController customerController) {
        super(customerTableModel, customerUserInputHandler,customerController);
    }


}
