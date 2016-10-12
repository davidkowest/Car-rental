package com.epam.carrental.gui;

import com.epam.carrental.gui.view.builders.impl.TableTabViewBuilder;
import com.epam.carrental.gui.view.builders.impl.TestConnectionViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class MainWindow {

    @Autowired
    private TestConnectionViewBuilder testConnectionViewBuilder;

    @Autowired
    private TableTabViewBuilder fleetViewBuilder;

    @Autowired
    private TableTabViewBuilder customerViewBuilder;

    @PostConstruct
    public void init() {
        SwingUtilities.invokeLater(this::initGUI);
    }

    private void initGUI() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Car-rental");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Other", null, testConnectionViewBuilder.build(), "Do you want to test connection");
        jTabbedPane.addTab("Fleet", null, fleetViewBuilder.build(), "Fleet");
        jTabbedPane.addTab("Customer", null, customerViewBuilder.build(), "Fleet");
        mainFrame.add(jTabbedPane);
        mainFrame.pack();
        //set component relativeTo null so that window will be shown in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
