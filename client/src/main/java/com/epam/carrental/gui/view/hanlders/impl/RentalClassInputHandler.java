package com.epam.carrental.gui.view.hanlders.impl;

import com.epam.carrental.controller.RentalClassController;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;


@Component
public class RentalClassInputHandler implements UserInputHandler {

    @Value("${car.class.name.label}")
    private String nameLabel;

    @Value("${car.class.rate.label}")
    private String hourlyRateLabel;

    @Value("${car.class.input.message}")
    private String message;

    @Autowired
    private RentalClassController rentalClassController;

    public void handleInput() {
        JTextField nameField = new JTextField();
        JTextField hourlyRentalRateField = new JTextField();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(nameField, nameLabel), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(hourlyRentalRateField, hourlyRateLabel), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                message, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            rentalClassController.save(new RentalClassDTO(nameField.getText(), Float.valueOf(hourlyRentalRateField.getText())));
        }
    }
}
