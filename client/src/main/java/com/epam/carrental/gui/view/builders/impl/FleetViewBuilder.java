package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.gui.controller.DAOController;
import com.epam.carrental.gui.view.GUITitleConstants;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.builders.ViewBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;

public class FleetViewBuilder implements ViewBuilder {

    private final JPanel jPanel = new JPanel();

    private MessageView messageView;

    private DAOController daoController;

    private GUITitleConstants guiTitles = new GUITitleConstants();

    private String tabName;
    private JTable table;

    public FleetViewBuilder(String tabName, DAOController controller) {
        this.tabName = tabName;
        this.daoController = controller;
        this.messageView = new MessageView();
    }

    @Override
    public JPanel build() {
        preparePanel();
        return jPanel;
    }

    private void preparePanel() {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(prepareTablePanel(), CENTER);
        jPanel.add(prepareToolBar(), NORTH);
    }

    private JPanel prepareTablePanel() {
        table = new JTable(getModel());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(guiTitles.getTitleFor(tabName)), NORTH);
        panel.add(new JScrollPane(table), CENTER);
        return panel;
    }

    private JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();
        JButton refreshButton = new JButton("Refresh table");
        refreshButton.addActionListener(e -> refreshTable());
        JButton addNewCarButton = new JButton("Add new car");
        addNewCarButton.addActionListener(e -> saveNewCar());
        toolBar.add(refreshButton);
        toolBar.add(addNewCarButton);
        return toolBar;
    }

    private void saveNewCar() {
        JTextField carNumberField = new JTextField();
        JTextField carModelField = new JTextField();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(carModelField, "Car model:"), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(carNumberField, "Car number:"), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Enter Car number and model", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                daoController.addCarToDB(carModelField.getText(), carNumberField.getText());
            } catch (Exception e) {
                messageView.showErrorMessage(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private JPanel prepareInputPanel(JTextField field, String name) {
        JLabel label = new JLabel(name);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private void refreshTable() {
        DefaultTableModel freshModel = getModel();
        table.setModel(freshModel);
        freshModel.fireTableDataChanged();
    }

    private DefaultTableModel getModel() {
        DefaultTableModel model = new DefaultTableModel();
       try {
            model = daoController.getModel();
        } catch (Throwable e) {
            messageView.showErrorMessage(e.getCause().getMessage());
        }
        return model;
    }
}
