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
public class CustomerInputHandler implements UserInputHandler {

    private final String USER_NAME_LABEL= "User Name:";
    private final String USER_EMAIL_LABEL= "User Email:";
    private final String MESSAGE="Enter your name and email";

    @Autowired
    private CustomerController customerController;
    @Autowired
    private MessageView messageView;

    public CustomerInputHandler() {
    }

    public void handleInput() {
        JTextField userNameField = new JTextField();
        JTextField userEmailField = new JTextField();
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(userNameField, USER_NAME_LABEL), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(userEmailField, USER_EMAIL_LABEL), BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                MESSAGE, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            customerController.save(new CustomerDTO(userNameField.getText(), userEmailField.getText()));
        }
    }
}
