package com.epam.carrental.gui;

import com.epam.carrental.gui.controller.FleetController;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.builders.impl.FleetViewBuilder;
import com.epam.carrental.gui.view.builders.impl.TestConnectionViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class MainWindow {

    @Autowired
    private TestConnectionViewBuilder testConnectionViewBuilder;

    private FleetViewBuilder fleetViewBuilder;

    private final JFrame mainFrame = new JFrame();
    private final JTabbedPane jTabbedPane = new JTabbedPane();

    @PostConstruct
    public void init() {
        SwingUtilities.invokeLater(this::initGUI);
    }

    private void initGUI() {
        fleetViewBuilder = new FleetViewBuilder("Fleet",new FleetController());
        mainFrame.setTitle("Car-rental");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane.addTab("Other", null, testConnectionViewBuilder.build(), "Do you want to test connection");
        jTabbedPane.addTab("Fleet", null, fleetViewBuilder.build(), "Fleet");
        mainFrame.add(jTabbedPane);
        mainFrame.pack();
        //set component relativeTo null so that window will be shown in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
