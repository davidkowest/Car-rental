package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.ServerInfoController;
import com.epam.carrental.gui.view.views.TabView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class TestConnectionView implements TabView {

    private final JPanel panel;

    @Autowired
    private ServerInfoController serverInfoController;

    public TestConnectionView(){
        this.panel = new JPanel();
        JButton testConnectionButton = new JButton("Test connection");
        testConnectionButton.addActionListener(e ->  serverInfoController.getMessageForTestConnectionButton());
        this.panel.add(testConnectionButton);
    }

    @Override
    public JPanel initView(){
        return this.panel;
    }

}
