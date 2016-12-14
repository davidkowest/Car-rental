package com.epam.carrental.controller;


import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.impl.RentalClassInputHandler;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import com.epam.carrental.models.table.UpdatableListComboBoxModel;
import com.epam.carrental.services.RentalClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalClassController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;
    @Autowired
    private AbstractSwingTableModel<RentalClassDTO> rentalClassTableModel;
    @Autowired
    private UpdatableListComboBoxModel<RentalClassDTO> updatableListComboBoxModel;
    @Autowired
    private RentalClassService rentalClassService;
    @Autowired
    private RentalClassInputHandler rentalClassInputHandler;

    public void refreshData() {
        inBackgroundWorker.execute(
                rentalClassService::readAll,
                this::refreshModels,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void save(RentalClassDTO rentalClassDTO) {
        inBackgroundWorker.execute(
                () -> rentalClassService.create(rentalClassDTO),
                this::refreshData,
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }

    public void handleUserInput() {
        rentalClassInputHandler.handleInput();
    }

    private void refreshModels(List<RentalClassDTO> rentalClassDTOList){
        rentalClassTableModel.setDataAndRefreshTable(rentalClassDTOList);
        updatableListComboBoxModel.setDataAndRefresh(rentalClassDTOList);
    }
}
