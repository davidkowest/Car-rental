package com.epam.carrental.jms;

import com.epam.carrental.customers.CustomerTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MessageListener {

    @Autowired
    CustomerTableModel customerTableModel;

    @JmsListener(containerFactory = "containerFactory", destination = "topic")
    public void receiveMessage(DataChangedMessage message) {
        SwingUtilities.invokeLater(() -> customerTableModel.addRowAndRefreshTable(message.getCustomerDTO()));
    }
}