package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.controller.TableController;
import com.epam.carrental.gui.view.builders.ViewBuilder;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import com.epam.carrental.models.AbstractCarRentalTableModel;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;


public class TableTabViewBuilder<DTO> implements ViewBuilder {

    private final AbstractCarRentalTableModel tableModel;

    private final UserInputHandler userInputHandler;

    private final TableController<DTO> tableController;

    public TableTabViewBuilder(AbstractCarRentalTableModel tableModel, UserInputHandler userInputHandler,TableController<DTO> tableController ) {
        this.tableModel = tableModel;
        this.userInputHandler = userInputHandler;
        this.tableController = tableController;
    }

    @Override
    public JPanel build() {
        return preparePanel();
    }

    private JPanel preparePanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.add(prepareTablePanel(), CENTER);
        jPanel.add(prepareToolBar(), NORTH);
        return jPanel;
    }

    private JPanel prepareTablePanel() {
        JTable table = new JTable(tableModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(tableModel.getTableName()), NORTH);
        panel.add(new JScrollPane(table), CENTER);
        return panel;
    }

    private JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton refreshButton = new JButton("Refresh table");
        refreshButton.addActionListener(e -> refreshTable());
        JButton addNewCarButton = new JButton("Add new item");
        addNewCarButton.addActionListener(e -> userInputHandler.saveInput());
        toolBar.add(refreshButton);
        toolBar.add(addNewCarButton);
        return toolBar;
    }

    private void refreshTable(){
        tableController.refreshTableModel();
        tableModel.fireTableDataChanged();
    }
}
