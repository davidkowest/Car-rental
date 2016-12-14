package com.epam.carrental.controller;

import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.utils.ScheduleDataConverter;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.models.chart.ScheduleModel;
import com.epam.carrental.services.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChartController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;
    @Autowired
    private ChartService chartService;
    @Autowired
    private ScheduleModel scheduleModel;
    @Autowired
    private ScheduleDataConverter scheduleDataConverter;

    public void refresh() {
        inBackgroundWorker.execute(
                chartService::findAllData,
                chartDataDTO -> scheduleModel.setDataAndRefreshSchedule(scheduleDataConverter.convert(chartDataDTO)),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}

