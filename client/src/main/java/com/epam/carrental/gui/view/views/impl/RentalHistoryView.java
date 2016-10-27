package com.epam.carrental.gui.view.views.impl;


import com.epam.carrental.controller.RentalHistoryController;
import com.epam.carrental.gui.utils.DateTimeAdapter;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class RentalHistoryView extends TableTabView {

    @Autowired
    RentalHistoryController rentalHistoryController;

    @Autowired
    DateTimeAdapter dateFromPicker;

    @Autowired
    DateTimeAdapter dateToPicker;

    public RentalHistoryView(AbstractSwingTableModel rentedCarHistoryTableModel) {
        super(rentedCarHistoryTableModel);
    }

    @Override
    JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();

        JLabel dateFromLabel = new JLabel();
        dateFromLabel.setText("Date from:");
        toolBar.add(dateFromLabel, BorderLayout.NORTH);
        toolBar.add(dateFromPicker.getComponent(), BorderLayout.CENTER);

        JLabel dateToLabel = new JLabel();
        dateToLabel.setText("Date to:");
        toolBar.add(dateToLabel, BorderLayout.NORTH);
        toolBar.add(dateToPicker.getComponent(), BorderLayout.CENTER);

        JButton refreshTableButton = new JButton("Filter");
        refreshTableButton.addActionListener(e ->  rentalHistoryController.filter(dateFromPicker.getDateTime(),dateToPicker.getDateTime()));
        toolBar.add(refreshTableButton);

        return toolBar;
    }
}
