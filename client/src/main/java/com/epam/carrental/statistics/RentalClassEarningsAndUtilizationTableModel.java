package com.epam.carrental.statistics;

import com.epam.carrental.dto.TotalStatisticsDTO;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import org.springframework.stereotype.Component;

@Component
public class RentalClassEarningsAndUtilizationTableModel extends AbstractSwingTableModel<TotalStatisticsDTO> {

    public RentalClassEarningsAndUtilizationTableModel() {
        this.tableName = "Rental class earnings and utilization";
        this.columnAndActionMap.put("Rental class", TotalStatisticsDTO::getRentalClassName);
        this.columnAndActionMap.put("Earnings", TotalStatisticsDTO::getEarnings);
        this.columnAndActionMap.put("Utilization", TotalStatisticsDTO::getUtilization);
    }
}