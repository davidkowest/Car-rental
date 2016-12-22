package com.epam.carrental.controller;

import com.epam.carrental.dto.DayStatisticsDTO;
import com.epam.carrental.dto.TotalStatisticsDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import com.epam.carrental.services.HistoryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class StatisticsController {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private MessageView messageView;

    @Autowired
    private AbstractSwingTableModel<TotalStatisticsDTO> rentalClassEarningsAndUtilizationTableModel;
    @Autowired
    private AbstractSwingTableModel<DayStatisticsDTO> rentalClassUtilizationPerDayTableModel;

    @Autowired
    HistoryStatisticsService historyStatisticsService;

    public void filter(ZonedDateTime dateOfRentDateTime, ZonedDateTime dateOfReturnDateTime) {

        inBackgroundWorker.execute(
                () -> historyStatisticsService.filter(dateOfRentDateTime, dateOfReturnDateTime),
                historyStatisticsDTOS -> {
                    rentalClassEarningsAndUtilizationTableModel.setDataAndRefreshTable(historyStatisticsDTOS.getTotalStatisticsDTO());
                    rentalClassUtilizationPerDayTableModel.setDataAndRefreshTable(historyStatisticsDTOS.getDayStatisticsDTO());
                },
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}