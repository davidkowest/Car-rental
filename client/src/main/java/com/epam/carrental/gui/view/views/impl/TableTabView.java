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
        this.table = new JTable(tableModel);
    }

    @Override
    public JPanel initView() {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(toolBar, NORTH);
        addTableView(tableModel.getTableName(), table, CENTER);
        return jPanel;
    }

    protected void addTableView(String tableName, JTable table, String alignment) {
        JScrollPane jsTable = new JScrollPane(table);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(tableName), NORTH);
        panel.add(jsTable, CENTER);
        jPanel.add(panel, alignment);
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }
}

