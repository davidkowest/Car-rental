package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.StatisticsController;
import com.epam.carrental.gui.utils.DateAdapter;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

import static java.awt.BorderLayout.SOUTH;

@Component
public class StatisticsView extends TableTabView {

    @Autowired
    StatisticsController statisticsController;
    @Autowired
    DateAdapter dateFromPicker;
    @Autowired
    DateAdapter dateToPicker;

    private AbstractSwingTableModel tableModel;
    private JTable table;

    public StatisticsView(AbstractSwingTableModel rentalClassEarningsAndUtilizationTableModel, AbstractSwingTableModel rentalClassUtilizationPerDayTableModel) {
        super(rentalClassEarningsAndUtilizationTableModel);
        this.tableModel = rentalClassUtilizationPerDayTableModel;
        this.table = new JTable(tableModel);
    }

    @Override
    public JPanel initView() {
        super.initView();
        addPickerToToolBar("Date from:", dateFromPicker.getComponent());
        addPickerToToolBar("Date to:", dateToPicker.getComponent());
        addButtonToToolBar("Filter", () -> statisticsController.filter(dateFromPicker.getDateTime(), dateToPicker.getDateTime()));
        addTableView(tableModel.getTableName(), table, SOUTH);
        return jPanel;
    }
}
