package com.epam.carrental.gui;

import com.epam.carrental.gui.listener_actions.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Component
public class MainWindow {

    @Autowired
    private MessageGenerator messageGenerator;

    private JFrame jFrame = new JFrame();

    @PostConstruct
    public void init() {
        initGUI();
    }

    private void initGUI() {
        jFrame.setTitle("Car-rental");
        jFrame.setLayout(new FlowLayout());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(preparePanel());
        jFrame.pack();
        //set component relativeTo null so that window will be shown in the center of the screen
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private JPanel preparePanel() {
        JPanel panel = new JPanel();
        JButton testConnectionButton = new JButton("Test connection");
        testConnectionButton.addActionListener(getListenerForTestConnectionButton());
        panel.add(testConnectionButton);
        return panel;
    }

    private ActionListener getListenerForTestConnectionButton() {
        return e -> {
            String message = messageGenerator.getMessageForTestConnectionButton();
            JOptionPane.showMessageDialog(jFrame.getComponent(0), message);
        };
    }
}
