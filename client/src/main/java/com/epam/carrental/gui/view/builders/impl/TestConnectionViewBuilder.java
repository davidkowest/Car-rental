package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.gui.controller.ServerInfoController;
import com.epam.carrental.gui.view.builders.ViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class TestConnectionViewBuilder implements ViewBuilder {

    private final JPanel panel;

    @Autowired
    private ServerInfoController serverInfoController;

    public TestConnectionViewBuilder(){
        this.panel = new JPanel();
        JButton testConnectionButton = new JButton("Test connection");
        testConnectionButton.addActionListener(e ->  serverInfoController.getMessageForTestConnectionButton());
        this.panel.add(testConnectionButton);
    }

    @Override
    public JPanel build(){
        return this.panel;
    }

}
