package com.epam.carrental.controller;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.impl.RentalUserInputHandler;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.services.CustomerService;
import com.epam.carrental.services.RentedCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentCarController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;

    @Autowired
    private AbstractSwingTableModel<CarDTO> availableCarTableModel;
    @Autowired
    private AbstractSwingTableModel<CustomerDTO> customerTableModel;
    @Autowired
    private RentedCarService rentedCarService;
    @Autowired
    CustomerService customerService;

    @Autowired
    private RentalUserInputHandler rentalUserInputHandler;

    public void refreshTableView() {

        inBackgroundWorker.execute(
                () -> rentedCarService.findNotRented(),
                carDTOs -> {
                    availableCarTableModel.setData(carDTOs);
                    availableCarTableModel.fireTableDataChanged();
                },
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void save(RentedCarDTO rentedCarDTO) {
        inBackgroundWorker.execute(
                () -> rentedCarService.rentCarForCustomer(rentedCarDTO),
                o -> refreshTableView(),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void handleUserInput(int selectedRow) {
        refreshCustomerModel();
        if (selectedRow < 0) {
            messageView.showErrorMessage("No rows selected!");
        }
        CarDTO carDTO = availableCarTableModel.getModel(selectedRow);
        rentalUserInputHandler.handleInputUsing(carDTO);
    }

    private void refreshCustomerModel() {
        inBackgroundWorker.execute(
                () -> customerService.readAll(),
                customerDTOs -> {
                    customerTableModel.setData(customerDTOs);
                    customerTableModel.fireTableDataChanged();
                },
                e -> messageView.showErrorMessage(e.getCause().getMessage()));

    }
}


