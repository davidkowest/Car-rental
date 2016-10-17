package com.epam.carrental.controller;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.builders.impl.TableTabView;
import com.epam.carrental.gui.view.hanlders.impl.RentalUserInputHandler;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.services.RentedCarService;
import com.epam.carrental.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentCarController implements TableController<RentedCarDTO> {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;

    @Autowired
    private AbstractSwingTableModel<CarDTO> availableCarsTableModel;
    @Autowired
    private AbstractSwingTableModel customerTableModel;
    @Autowired
    private RentedCarService rentedCarService;
    @Autowired
    CustomerService customerService;


    private TableTabView rentCarView;
    @Autowired
    private RentalUserInputHandler rentalUserInputHandler;

    // to avoid unresolvable circular reference during creating RentCarController and RentCarView
    public void setRentCarView(TableTabView rentCarView) {
        this.rentCarView = rentCarView;
    }

    @Override
    public void refreshTableView() {

        inBackgroundWorker.execute(
                () -> rentedCarService.findNotRented(),
                carDTOs -> {
                    availableCarsTableModel.setData(carDTOs);
                    availableCarsTableModel.fireTableDataChanged();
                },
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void save(RentedCarDTO rentedCarDTO) {
        inBackgroundWorker.execute(
                () -> rentedCarService.rentCarForCustomer(rentedCarDTO),
                o -> refreshTableView(),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    @Override
    public void handleUserInput() {
        refreshCustomerModel();
        int selectedRow = rentCarView.getSelectedRow();
        if (selectedRow < 0) {
            messageView.showErrorMessage("No rows selected!");
        }
        CarDTO carDTO = availableCarsTableModel.getModel(selectedRow);
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


