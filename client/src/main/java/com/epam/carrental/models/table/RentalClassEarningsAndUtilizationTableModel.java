package com.epam.carrental.models.table;

import com.epam.carrental.dto.TotalStatisticsDTO;
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