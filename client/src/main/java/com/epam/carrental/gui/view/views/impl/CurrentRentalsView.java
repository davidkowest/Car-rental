package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.CurrentRentalsController;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CurrentRentalsView extends TableTabView {

    @Autowired
    CurrentRentalsController currentRentalsController;

    public CurrentRentalsView(AbstractSwingTableModel rentedCarTableModel) {
        super(rentedCarTableModel);
    }

    @Override
    JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();

        JButton refreshTableButton = new JButton("Refresh table");
        refreshTableButton.addActionListener(e ->  currentRentalsController.refreshTableView());
        toolBar.add(refreshTableButton);

        JButton rentCarButton = new JButton("Return");
        rentCarButton.addActionListener(e ->  currentRentalsController.handleUserInput(getSelectedRow()));
        toolBar.add(rentCarButton);

        return toolBar;
    }
}
