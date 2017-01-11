package com.epam.carrental.rental_classes;


import com.epam.carrental.gui.view.TableTabView;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class RentalClassView extends TableTabView {

    @Autowired
    private RentalClassController rentalClassController;

    public RentalClassView(AbstractSwingTableModel rentalClassTableModel) {
        super(rentalClassTableModel);
    }

    @Override
    public JPanel initView() {
        super.initView();
        addButtonToToolBar("Refresh table", rentalClassController::refreshData);
        addButtonToToolBar("Add new class", rentalClassController::handleUserInput);
        return jPanel;
    }

}