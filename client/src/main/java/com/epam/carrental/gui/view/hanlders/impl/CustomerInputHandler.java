package com.epam.carrental.gui.view.hanlders.impl;

import com.epam.carrental.controller.CustomerController;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerInputHandler implements UserInputHandler {

    @Value("${customer.name.label}")
    private  String userNameLabel;

    @Value("${customer.email.label}")
    private  String userEmailLabel;

    @Value("${customer.input.message}")
    private  String message;

    @Autowired
    private CustomerController customerController;
    @Autowired
    private MessageView messageView;

    public void handleInput() {
        JTextField userNameField = new JTextField();
        JTextField userEmailField = new JTextField();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(userNameField, userNameLabel), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(userEmailField, userEmailLabel), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                message, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            customerController.save(new CustomerDTO(userNameField.getText(), userEmailField.getText()));
        }
    }
}
