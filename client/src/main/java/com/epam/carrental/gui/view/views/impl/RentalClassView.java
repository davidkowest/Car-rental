package com.epam.carrental.gui.view.views.impl;


import com.epam.carrental.controller.RentalClassController;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class RentalClassView extends TableTabView {

    public RentalClassView(RentalClassController rentalClassController, AbstractSwingTableModel rentalClassTableModel) {
        super(rentalClassTableModel);

        this.actions.put("Refresh table",rentalClassController::refreshData);
        this.actions.put("Add new class",rentalClassController::handleUserInput);
    }

}