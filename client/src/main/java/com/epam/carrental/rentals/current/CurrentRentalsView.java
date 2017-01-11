package com.epam.carrental.rentals.current;

import com.epam.carrental.gui.view.TableTabView;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class CurrentRentalsView extends TableTabView {

    @Autowired
    private CurrentRentalsController currentRentalsController;

    public CurrentRentalsView(AbstractSwingTableModel rentedCarTableModel) {
        super(rentedCarTableModel);
    }

    @Override
    public JPanel initView() {
        super.initView();
        addButtonToToolBar("Refresh table", currentRentalsController::refreshTableView);
        addButtonToToolBar("Return", () -> currentRentalsController.handleUserInput(getSelectedRow()));
        return jPanel;
    }
}
