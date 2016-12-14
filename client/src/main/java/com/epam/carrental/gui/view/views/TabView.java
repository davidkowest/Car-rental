package com.epam.carrental.gui.view.views;

import org.jdesktop.swingx.combobox.ListComboBoxModel;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;

public abstract class TabView {

    protected JPanel jPanel = new JPanel();
    protected JToolBar toolBar = new JToolBar();

    public JPanel initView() {
        return jPanel;
    }

    protected void addButtonToToolBar(String name, Runnable action) {
        toolBar.add(prepareButton(name,action));
    }

    protected void addButtonToPanel(String name, Runnable action) {
        jPanel.add(prepareButton(name,action));
    }

    protected void addTimePickerToToolBar(String label, Component datePickerComponent) {
        JLabel jLabel = new JLabel();
        jLabel.setText(label);
        toolBar.add(jLabel, BorderLayout.NORTH);
        toolBar.add(datePickerComponent, BorderLayout.CENTER);
    }

    protected void addComponentToToolBar(Component component) {
        toolBar.add(component);
    }

    private JButton prepareButton(String name, Runnable action){
        JButton button = new JButton(name);
        button.addActionListener(e -> action.run());
        return button;
    }
    protected JComboBox prepareComboBox(ListComboBoxModel comboBoxModel, DefaultListCellRenderer renderer, PopupMenuListener listener) {
        JComboBox comboBox = new JComboBox(comboBoxModel);
        comboBox.setRenderer(renderer);
        comboBox.addPopupMenuListener(listener);
        return comboBox;
    }
}
