package com.epam.carrental.gui;

import com.epam.carrental.gui.view.builders.TestConnectionViewBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class MainWindow {

    @Autowired
    TestConnectionViewBuilder testConnectionViewBuilder;

    private JFrame jFrame = new JFrame();

    @PostConstruct
    public void init() {
        SwingUtilities.invokeLater(() ->initGUI() );
    }

    private void initGUI() {
        jFrame.setTitle("Car-rental");
        jFrame.setLayout(new FlowLayout());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(testConnectionViewBuilder.build());

        jFrame.pack();
        //set component relativeTo null so that window will be shown in the center of the screen
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

}
