package com.epam.carrental.jms;

import com.epam.carrental.customers.CustomerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    CustomerController customerController;

    @JmsListener(containerFactory = "containerFactory", destination = "topic")
    public void receiveMessage(DataChangedMessage message) {
        customerController.refreshTableView();
    }
}