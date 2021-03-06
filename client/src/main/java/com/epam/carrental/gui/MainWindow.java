package com.epam.carrental.gui;

import com.epam.carrental.chart.ScheduleChartView;
import com.epam.carrental.connection.TestConnectionView;
import com.epam.carrental.gui.view.TableTabView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@Component
public class MainWindow {

    @Autowired
    ConfigurableApplicationContext applicationContext;

    @Autowired
    private TestConnectionView testConnectionView;

    @Autowired
    private TableTabView fleetView;

    @Autowired
    private TableTabView customerView;

    @Autowired
    private TableTabView rentCarView;

    @Autowired
    private TableTabView currentRentalsView;

    @Autowired
    private TableTabView rentalHistoryView;

    @Autowired
    private TableTabView rentalClassView;

    @Autowired
    private TableTabView bookCarView;

    @Autowired
    private ScheduleChartView scheduleChartView;

    @Autowired
    private TableTabView statisticsView;

    @PostConstruct
    @SuppressWarnings("unused")
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
        jTabbedPane.addTab("Available to rent", null, rentCarView.initView(), "Available to rent");
        jTabbedPane.addTab("Current rentals", null, currentRentalsView.initView(), "Current rentals");
        jTabbedPane.addTab("Rental history", null, rentalHistoryView.initView(), "Rental history");
        jTabbedPane.addTab("Rental classes", null, rentalClassView.initView(), "Rental classes");
        jTabbedPane.addTab("Available to book", null, bookCarView.initView(), "Available to book");
        jTabbedPane.addTab("Schedule chart", null, scheduleChartView.initView(), "Schedule chart");
        jTabbedPane.addTab("Statistics", null, statisticsView.initView(), "Statistics");
        mainFrame.add(jTabbedPane);
        mainFrame.pack();
        //set component relativeTo null so that window will be shown in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                applicationContext.close();
            }
        });
    }
}
