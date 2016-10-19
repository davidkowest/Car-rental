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

    private final String CAR_MODEL_LABEL= "Car model:";
    private final String CAR_REG_NUMBER_LABEL= "Car model:";
    private final String MESSAGE="Enter Car number and model";

    @Autowired
    private FleetController fleetController;
    @Autowired
    private MessageView messageView;

    public void handleInput() {
        JTextField carModelField = new JTextField();
        JTextField carNumberField = new JTextField();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(carModelField, CAR_MODEL_LABEL), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(carNumberField, CAR_REG_NUMBER_LABEL), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                MESSAGE, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            fleetController.save(new CarDTO(carModelField.getText(), carNumberField.getText()));
        }
    }
}
