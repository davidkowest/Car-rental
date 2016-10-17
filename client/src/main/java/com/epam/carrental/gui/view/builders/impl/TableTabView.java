package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.gui.view.builders.TabView;
import com.epam.carrental.models.AbstractSwingTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;


public class TableTabView<DTO> implements TabView {

    private final AbstractSwingTableModel<DTO> tableModel;

    private final Map<String,Runnable> actions;

    private JTable table;

    public TableTabView(AbstractSwingTableModel<DTO> tableModel,  Map<String,Runnable> actions) {
        this.tableModel = tableModel;
        this.actions=actions;
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

    private JToolBar prepareToolBar() {
        JToolBar toolBar = new JToolBar();

        for (String buttonName:actions.keySet()){
            JButton button = new JButton(buttonName);
            button.addActionListener(e -> actions.get(buttonName).run());
            toolBar.add(button);
        }

        return toolBar;
    }
    public int getSelectedRow(){
        return table.getSelectedRow();
    }
}
