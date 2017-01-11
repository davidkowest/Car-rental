package com.epam.carrental.statistics;

import com.epam.carrental.dto.DayStatisticsDTO;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class RentalClassUtilizationPerDayTableModel extends AbstractSwingTableModel<DayStatisticsDTO> {

    public RentalClassUtilizationPerDayTableModel() {
        this.tableName = "Rental class utilization per day";
        this.columnAndActionMap.put("Rental class", DayStatisticsDTO::getRentalClassName);

        this.columnAndActionMap.put("Monday", historyStatisticsDTO -> historyStatisticsDTO.getUtilizationPerDay().get(DayOfWeek.MONDAY));
        this.columnAndActionMap.put("Tuesday", historyStatisticsDTO -> historyStatisticsDTO.getUtilizationPerDay().get(DayOfWeek.TUESDAY));
        this.columnAndActionMap.put("Wednesday", historyStatisticsDTO -> historyStatisticsDTO.getUtilizationPerDay().get(DayOfWeek.WEDNESDAY));
        this.columnAndActionMap.put("Thursday", historyStatisticsDTO -> historyStatisticsDTO.getUtilizationPerDay().get(DayOfWeek.THURSDAY));
        this.columnAndActionMap.put("Friday", historyStatisticsDTO -> historyStatisticsDTO.getUtilizationPerDay().get(DayOfWeek.FRIDAY));
        this.columnAndActionMap.put("Saturday", historyStatisticsDTO -> historyStatisticsDTO.getUtilizationPerDay().get(DayOfWeek.SATURDAY));
        this.columnAndActionMap.put("Sunday", historyStatisticsDTO -> historyStatisticsDTO.getUtilizationPerDay().get(DayOfWeek.SUNDAY));
    }
}