package com.epam.carrental.gui.view.views.impl;


import com.epam.carrental.controller.BookCarController;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.listeners.RentalClassComboBoxListener;
import com.epam.carrental.gui.utils.DateTimeAdapter;
import com.epam.carrental.gui.utils.RentalClassRenderer;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import com.epam.carrental.models.table.UpdatableListComboBoxModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

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

    @Autowired
    public BookCarView(AbstractSwingTableModel availableToBookCarTableModel) {
        super(availableToBookCarTableModel);
    }

    @Override
    public JPanel initView() {
        super.initView();
        addButtonToToolBar("Refresh table", () -> bookCarController.refreshTableView(dateFromPicker.getDateTime(), dateToPicker.getDateTime(), updatableListComboBoxModel.getSelectedItem()));
        addButtonToToolBar("Book a car", () -> bookCarController.handleUserInput(getSelectedRow(), dateFromPicker.getDateTime(), dateToPicker.getDateTime()));
        addComponentToToolBar(prepareComboBox(updatableListComboBoxModel, new RentalClassRenderer(), rentalClassComboBoxListener));
        addPickerToToolBar("Date from:", dateFromPicker.getComponent());
        addPickerToToolBar("Date to:", dateToPicker.getComponent());
        return jPanel;
    }
}
