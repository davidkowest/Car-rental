package com.epam.carrental.gui;

import com.epam.carrental.gui.view.builders.impl.TableTabView;
import com.epam.carrental.gui.view.builders.impl.TestConnectionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class MainWindow {

    @Autowired
    private TestConnectionView testConnectionView;

    @Autowired
    private TableTabView fleetView;

    @Autowired
    private TableTabView customerView;

    @Autowired
    private TableTabView rentCarView;

    @PostConstruct
    public void init() {
        SwingUtilities.invokeLater(this::initGUI);
    }

    private void initGUI() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Car-rental");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Other", null, testConnectionView.initView(), "Do you want to test connection");
        jTabbedPane.addTab("Fleet", null, fleetView.initView(), "Fleet");
        jTabbedPane.addTab("Customer", null, customerView.initView(), "Customer");
        jTabbedPane.addTab("Rent car", null, rentCarView.initView(), "Rent car");
        mainFrame.add(jTabbedPane);
        mainFrame.pack();
        //set component relativeTo null so that window will be shown in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
