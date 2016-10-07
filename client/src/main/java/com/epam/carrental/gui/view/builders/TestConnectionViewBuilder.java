package com.epam.carrental.gui.view.builders;

import com.epam.carrental.gui.controller.MessageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class TestConnectionViewBuilder {

    private JPanel panel;
    private JButton testConnectionButton;

    @Autowired
    MessageController messageController;


    public TestConnectionViewBuilder(){
        this.panel = new JPanel();
        this.testConnectionButton = new JButton("Test connection");
        this.testConnectionButton.addActionListener(e ->  messageController.getMessageForTestConnectionButton());
        this.panel.add(testConnectionButton);
    }

    public JPanel build(){
        return this.panel;
    }

}
