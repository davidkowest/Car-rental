package com.epam.carrental.controller;

import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.impl.CustomerInputHandler;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerController implements TableController<CustomerDTO> {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;

    @Autowired
    private AbstractSwingTableModel<CustomerDTO> customerTableModel;
    @Autowired
    private CustomerInputHandler customerInputHandler;
    @Autowired
    private CustomerService customerService;

    @Override
    public void refreshTableView() {
        inBackgroundWorker.execute(
                () -> customerService.readAll(),
                customerDTOs -> {
                    customerTableModel.setData(customerDTOs);
                    customerTableModel.fireTableDataChanged();
                },
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    @Override
    public void save(CustomerDTO customerDTO) {
       inBackgroundWorker.execute(
                () -> customerService.create(customerDTO),
                o -> refreshTableView(),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    @Override
    public void handleUserInput() {
        customerInputHandler.handleInput();
    }
}

