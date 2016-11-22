package com.epam.carrental.gui.view.views.impl;


import com.epam.carrental.controller.RentalHistoryController;
import com.epam.carrental.gui.utils.DateTimeAdapter;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;

@org.springframework.stereotype.Component
public class RentalHistoryView extends TableTabView {

    @Autowired
    RentalHistoryController rentalHistoryController;

    @Autowired
    DateTimeAdapter dateFromPicker;

    @Autowired
    DateTimeAdapter dateToPicker;

    private JToolBar toolBar = new JToolBar();

    public RentalHistoryView(AbstractSwingTableModel rentedCarHistoryTableModel) {
        super(rentedCarHistoryTableModel);
    }

    @Override
    JToolBar prepareToolBar() {

        addTimePicker("Date from:", dateFromPicker.getComponent());
        addTimePicker("Date to:", dateToPicker.getComponent());

        JButton refreshTableButton = new JButton("Filter");
        refreshTableButton.addActionListener(e -> rentalHistoryController.filter(dateFromPicker.getDateTime(), dateToPicker.getDateTime()));
        toolBar.add(refreshTableButton);

        return toolBar;
    }

    private void addTimePicker(String label, Component datePickerComponent) {
        JLabel jLabel = new JLabel();
        jLabel.setText(label);
        toolBar.add(jLabel, BorderLayout.NORTH);
        toolBar.add(datePickerComponent, BorderLayout.CENTER);
    }
}
