package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.FleetController;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class FleetView extends TableTabView{

    public FleetView(FleetController fleetController,AbstractSwingTableModel carTableModel) {
        super(carTableModel);

        addButtonToToolBar("Refresh table",fleetController::refreshTableView);
        addButtonToToolBar("Add new car",fleetController::handleUserInput);
    }
}
