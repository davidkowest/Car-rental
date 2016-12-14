package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.RentCarController;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.listeners.RentalClassComboBoxListener;
import com.epam.carrental.gui.utils.DateTimeAdapter;
import com.epam.carrental.gui.utils.RentalClassRenderer;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import com.epam.carrental.models.table.UpdatableListComboBoxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;


@Component
public class RentCarView extends TableTabView {

    @Autowired
    RentalClassComboBoxListener rentalClassComboBoxListener;
    @Autowired
    DateTimeAdapter dateUntilPicker;
    @Autowired
    private RentCarController rentCarController;

    @Autowired
    private UpdatableListComboBoxModel<RentalClassDTO> updatableListComboBoxModel;

    public RentCarView(AbstractSwingTableModel availableToRentCarTableModel) {
        super(availableToRentCarTableModel);

    }

    @SuppressWarnings("unchecked")
    @PostConstruct
    protected void prepareToolBar() {

        JComboBox<RentalClassDTO> rentalClassComboBox = prepareComboBox(updatableListComboBoxModel, new RentalClassRenderer(), rentalClassComboBoxListener);

        addButtonToToolBar("Refresh table",() -> rentCarController.refreshTableView((RentalClassDTO) rentalClassComboBox.getSelectedItem(), dateUntilPicker.getDateTime()));
        addButtonToToolBar("Rent a car",() -> rentCarController.handleUserInput(getSelectedRow()));
        addComponentToToolBar(rentalClassComboBox);
        addTimePickerToToolBar("Available until", dateUntilPicker.getComponent());
    }
}

