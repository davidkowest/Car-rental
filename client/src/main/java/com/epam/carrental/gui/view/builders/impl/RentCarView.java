package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.controller.RentCarController;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;


@Component
public class RentCarView  extends TableTabView{

    @Autowired
    private RentCarController rentCarController;
    
    public RentCarView(AbstractSwingTableModel availableCarTableModel) {
       super(availableCarTableModel);
    }

    @Override
    JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();

        JButton refreshTableButton = new JButton("Refresh table");
        refreshTableButton.addActionListener(e ->  rentCarController.refreshTableView());
        toolBar.add(refreshTableButton);

        JButton rentCarButton = new JButton("Rent a car");
        rentCarButton.addActionListener(e ->  rentCarController.handleUserInput(getSelectedRow()));
        toolBar.add(rentCarButton);

        return toolBar;
    }
}

