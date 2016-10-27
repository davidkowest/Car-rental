package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.FleetController;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class FleetView extends TableTabView{

    public FleetView(FleetController fleetController,AbstractSwingTableModel carTableModel) {
        super(carTableModel);

        this.actions.put("Refresh table",fleetController::refreshTableView);
        this.actions.put("Add new car",fleetController::handleUserInput);
    }
}
