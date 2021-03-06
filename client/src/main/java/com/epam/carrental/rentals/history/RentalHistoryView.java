package com.epam.carrental.rentals.history;


import com.epam.carrental.gui.components.DateTimeAdapter;
import com.epam.carrental.gui.view.TableTabView;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

@org.springframework.stereotype.Component
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
    public JPanel initView() {
        super.initView();
        addPickerToToolBar("Date from:", dateFromPicker.getComponent());
        addPickerToToolBar("Date to:", dateToPicker.getComponent());
        addButtonToToolBar("Filter", () -> rentalHistoryController.filter(dateFromPicker.getDateTime(), dateToPicker.getDateTime()));
        return jPanel;
    }

}
