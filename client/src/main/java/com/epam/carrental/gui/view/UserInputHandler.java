package com.epam.carrental.gui.view;

import javax.swing.*;
import java.awt.*;

public interface UserInputHandler {

    default JPanel prepareInputPanel(JTextField field, String name) {
        JLabel label = new JLabel(name);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    default JPanel prepareComboBoxPanel(JComboBox comboBox, String name) {
        JLabel label = new JLabel(name);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    default JPanel prepareTimePicker(Component datePickerComponent, String name) {
        JLabel label = new JLabel(name);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.NORTH);
        panel.add(datePickerComponent, BorderLayout.CENTER);
        return panel;
    }
}
