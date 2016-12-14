package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.gui.view.views.TabView;
import com.epam.carrental.models.table.AbstractSwingTableModel;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;


public class TableTabView extends TabView {

    private AbstractSwingTableModel tableModel;
    private JTable table;

    public TableTabView(AbstractSwingTableModel tableModel) {
        this.tableModel = tableModel;
    }

    @Override
    public JPanel initView() {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(toolBar, NORTH);
        jPanel.add(prepareTablePanel());
        return jPanel;
    }

    private JPanel prepareTablePanel() {
        table = new JTable(tableModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(tableModel.getTableName()), NORTH);
        panel.add(new JScrollPane(table), CENTER);
        return panel;
    }

    public int getSelectedRow(){
        return table.getSelectedRow();
    }
}
