package com.epam.carrental.gui;

import com.epam.carrental.dto.ServerInfoDTO;
import com.epam.carrental.services.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    @Autowired
    ServerInfo serverInfo;

    public MainWindow(){
        initGUI();
    }

    public void initGUI() {
        setTitle("Car-rental");
        setSize(400, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        JButton testConnectionButton = new JButton("Test connection");
        testConnectionButton.addActionListener(getListener());
        panel.add(testConnectionButton);
        add(panel);
        setVisible(true);
    }


    public ActionListener getListener() {
        return e -> {
            String message;
            try {
                ServerInfoDTO serverInfoDTO = serverInfo.getServerInfo();
                message="Server returned " + serverInfoDTO.getIpAddress() + " IP address. Server time is " + serverInfoDTO.getTime();
            } catch (Exception exc) {
                message = "There is no connection to server!";
            }
            JOptionPane.showMessageDialog(getComponent(0), message);
        };
    }
}
