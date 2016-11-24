package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.RentCarController;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.listeners.RentalClassComboBoxListener;
import com.epam.carrental.gui.utils.RentalClassRenderer;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.models.UpdatableListComboBoxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;


@Component
public class RentCarView extends TableTabView {

    @Autowired
    private RentCarController rentCarController;
    @Autowired
    private UpdatableListComboBoxModel<RentalClassDTO> updatableListComboBoxModel;

    @Autowired
    RentalClassComboBoxListener rentalClassComboBoxListener;

    public RentCarView(AbstractSwingTableModel availableToRentCarTableModel) {
        super(availableToRentCarTableModel);
    }

    @Override
    JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();


        JComboBox<RentalClassDTO> rentalClassComboBox = new JComboBox<>(updatableListComboBoxModel);
        rentalClassComboBox.setRenderer(new RentalClassRenderer());
        rentalClassComboBox.addPopupMenuListener(rentalClassComboBoxListener);
        JButton refreshTableButton = new JButton("Refresh table");
        refreshTableButton.addActionListener(e -> rentCarController.refreshTableView((RentalClassDTO) rentalClassComboBox.getSelectedItem()));
        toolBar.add(refreshTableButton);

        JButton rentCarButton = new JButton("Rent a car");
        rentCarButton.addActionListener(e -> rentCarController.handleUserInput(getSelectedRow()));
        toolBar.add(rentCarButton);
        toolBar.add(rentalClassComboBox);
        return toolBar;
    }
}

