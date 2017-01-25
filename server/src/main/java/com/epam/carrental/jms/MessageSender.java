package com.epam.carrental.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendNotification(DataChangedMessage message) {
        jmsTemplate.convertAndSend(message);
    }
}