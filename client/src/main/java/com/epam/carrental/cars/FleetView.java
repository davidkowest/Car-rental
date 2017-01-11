package com.epam.carrental.cars;

import com.epam.carrental.gui.view.TableTabView;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
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
