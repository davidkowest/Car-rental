package com.epam.carrental.gui;

import com.epam.carrental.gui.listener_actions.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.ActionListener;

@Component
public class MainWindow extends JFrame {

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 600;

    @Autowired
    private MessageGenerator messageGenerator;

    @PostConstruct
    public void init() {
        initGUI();
    }

    private void initGUI() {
        setTitle("Car-rental");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton testConnectionButton = new JButton("Test connection");
        testConnectionButton.addActionListener(getListenerForTestConnectionButton());
        panel.add(testConnectionButton);
        add(panel);
        setVisible(true);
    }

   private ActionListener getListenerForTestConnectionButton() {
        return e -> {
            String message = messageGenerator.getMessageForTestConnectionButton();
            JOptionPane.showMessageDialog(getComponent(0), message);
        };
    }
}
