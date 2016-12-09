package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.gui.view.views.TabView;
import com.epam.carrental.models.AbstractSwingTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;


public class TableTabView implements TabView {

    private AbstractSwingTableModel tableModel;


    Map<String,Runnable> actions;

    private JTable table;

    public TableTabView(AbstractSwingTableModel tableModel) {
        this.tableModel = tableModel;
        this.actions=new LinkedHashMap<>();
    }

    @Override
    public JPanel initView() {
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
        table = new JTable(tableModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(tableModel.getTableName()), NORTH);
        panel.add(new JScrollPane(table), CENTER);
        return panel;
    }

    JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();

        for (Map.Entry<String,Runnable> entry : actions.entrySet()){
            JButton button = new JButton(entry.getKey());
            button.addActionListener(e -> entry.getValue().run());
            toolBar.add(button);
        }

        return toolBar;
    }
    public int getSelectedRow(){
        return table.getSelectedRow();
    }
}
