package com.epam.carrental.gui.view.hanlders.impl;

import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.controller.FleetController;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class CarUserInputHandler implements UserInputHandler {

    @Autowired
    private FleetController fleetController;
    @Autowired
    private MessageView messageView;

    public void handleInput() {
        JTextField carModelField = new JTextField();
        JTextField carNumberField = new JTextField();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(carModelField, "Car model:"), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(carNumberField, "Car number:"), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Enter Car number and model", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            fleetController.save(new CarDTO(carModelField.getText(), carNumberField.getText()));
        }
    }
}
