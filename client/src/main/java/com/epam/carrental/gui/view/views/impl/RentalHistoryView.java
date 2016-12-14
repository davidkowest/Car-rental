package com.epam.carrental.gui.view.views.impl;


import com.epam.carrental.controller.RentalHistoryController;
import com.epam.carrental.gui.utils.DateTimeAdapter;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    protected void prepareView() {
        addTimePickerToToolBar("Date from:", dateFromPicker.getComponent());
        addTimePickerToToolBar("Date to:", dateToPicker.getComponent());
        addButtonToToolBar("Filter",() ->rentalHistoryController.filter(dateFromPicker.getDateTime(), dateToPicker.getDateTime() ));
    }

}
