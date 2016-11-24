package com.epam.carrental.gui.view.views.impl;


import com.epam.carrental.controller.BookCarController;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.listeners.RentalClassComboBoxListener;
import com.epam.carrental.gui.utils.DateTimeAdapter;
import com.epam.carrental.gui.utils.RentalClassRenderer;
import com.epam.carrental.models.AbstractSwingTableModel;
import com.epam.carrental.models.UpdatableListComboBoxModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

@org.springframework.stereotype.Component
public class BookCarView extends TableTabView {

    @Autowired
    RentalClassComboBoxListener rentalClassComboBoxListener;

    @Autowired
    BookCarController bookCarController;

    @Autowired
    DateTimeAdapter dateFromPicker;

    @Autowired
    DateTimeAdapter dateToPicker;

    @Autowired
    private UpdatableListComboBoxModel<RentalClassDTO> updatableListComboBoxModel;

    private JToolBar toolBar = new JToolBar();

    public BookCarView(AbstractSwingTableModel availableToBookCarTableModel) {
        super(availableToBookCarTableModel);
    }

    @Override
    JToolBar prepareToolBar() {

        JButton refreshTableButton = new JButton("Refresh table");
        refreshTableButton.addActionListener(e -> bookCarController.refreshTableView(dateFromPicker.getDateTime(), dateToPicker.getDateTime(),updatableListComboBoxModel.getSelectedItem()));

        JButton bookCarButton = new JButton("Book a car");
        bookCarButton.addActionListener(e -> bookCarController.handleUserInput(getSelectedRow(),dateFromPicker.getDateTime(), dateToPicker.getDateTime()));

        JComboBox<RentalClassDTO> rentalClassComboBox = new JComboBox<>(updatableListComboBoxModel);
        rentalClassComboBox.setRenderer(new RentalClassRenderer());
        rentalClassComboBox.addPopupMenuListener(rentalClassComboBoxListener);

        toolBar.add(refreshTableButton);
        toolBar.add(bookCarButton);
        toolBar.add(rentalClassComboBox);

        addTimePicker("Date from:", dateFromPicker.getComponent());
        addTimePicker("Date to:", dateToPicker.getComponent());

        return toolBar;
    }

    private void addTimePicker(String label, Component datePickerComponent) {
        JLabel jLabel = new JLabel();
        jLabel.setText(label);
        toolBar.add(jLabel, BorderLayout.NORTH);
        toolBar.add(datePickerComponent, BorderLayout.CENTER);
    }
}
