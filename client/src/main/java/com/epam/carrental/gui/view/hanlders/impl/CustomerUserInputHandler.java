package com.epam.carrental.gui.view.hanlders.impl;


import com.epam.carrental.controller.CustomerController;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class CustomerUserInputHandler implements UserInputHandler {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private MessageView messageView;

    @Override
    public void saveInput() {
        JTextField userNameField = new JTextField();
        JTextField userEmailField = new JTextField();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(userNameField, "User Name:"), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(userEmailField, "User Email:"), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Enter your name and email", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {

                customerController.save(new CustomerDTO(userNameField.getText(),userEmailField.getText()));
            } catch (Exception e) {
                messageView.showErrorMessage(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
