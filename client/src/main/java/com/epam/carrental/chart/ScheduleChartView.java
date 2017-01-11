package com.epam.carrental.chart;

import com.epam.carrental.gui.view.TabView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import schedule.view.ScheduleView;
import schedule.view.TaskRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;

@Component
public class ScheduleChartView extends TabView {

    private ScheduleModel scheduleModel;

    @Autowired
    public ScheduleChartView(ChartController chartController, ScheduleModel scheduleModel) {
        this.scheduleModel=scheduleModel;
        addButtonToToolBar("Refresh",chartController::refresh);
    }

    @Override
    public JPanel initView() {
        jPanel.setLayout(new BorderLayout());
        jPanel.add(toolBar, NORTH);
        jPanel.add(prepareSchedulePanel());
        return jPanel;
    }

    private JPanel prepareSchedulePanel() {
        ScheduleView<CarResource, CarUsage> scheduleView = createChart();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scheduleView.getComponent(), CENTER);
        return panel;
    }

    private ScheduleView<CarResource, CarUsage> createChart() {
        ScheduleView<CarResource, CarUsage> chart = new ScheduleView<>(scheduleModel);
        chart.setRowHeight(14);
        chart.setTaskRenderer(renderTasks());
        chart.getComponent().setPreferredSize(new Dimension(1000, 500));
        return chart;
    }

    private TaskRenderer<CarUsage> renderTasks() {
        Map<CarState, Color> colorMap = new EnumMap<>(CarState.class);
        colorMap.put(CarState.BOOKED, Color.ORANGE);
        colorMap.put(CarState.RENTED, Color.red);
        colorMap.put(CarState.HISTORY, Color.GREEN);

        TaskRenderer.Default<CarUsage> renderer = new TaskRenderer.Default<CarUsage>() {
            @Override
            public JComponent getRenderingComponent(CarUsage task) {
                setBackground(colorMap.get(task.getCarState()));
                return super.getRenderingComponent(task);
            }
        };
        renderer.setOpaque(true);
        return renderer;
    }
}
