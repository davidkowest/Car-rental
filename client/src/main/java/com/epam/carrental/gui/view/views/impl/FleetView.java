package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.FleetController;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class FleetView extends TableTabView {

    @Autowired
    private FleetController fleetController;

    public FleetView(AbstractSwingTableModel carTableModel) {
        super(carTableModel);
    }

    @Override
    public JPanel initView() {
        super.initView();
        addButtonToToolBar("Refresh table", fleetController::refreshTableView);
        addButtonToToolBar("Add new car", fleetController::handleUserInput);
        return jPanel;
    }
}
