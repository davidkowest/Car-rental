package com.epam.carrental.gui.view.views.impl;


import com.epam.carrental.controller.RentalClassController;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class RentalClassView extends TableTabView {

    public RentalClassView(RentalClassController rentalClassController, AbstractSwingTableModel rentalClassTableModel) {
        super(rentalClassTableModel);

        addButtonToToolBar("Refresh table",rentalClassController::refreshData);
        addButtonToToolBar("Add new class",rentalClassController::handleUserInput);
    }

}