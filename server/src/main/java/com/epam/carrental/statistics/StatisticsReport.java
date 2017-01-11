package com.epam.carrental.statistics;

import com.epam.carrental.dto.DayStatisticsDTO;
import com.epam.carrental.dto.StatisticsDTO;
import com.epam.carrental.dto.TotalStatisticsDTO;
import com.epam.carrental.rentals.history.RentedCarHistory;
import com.epam.carrental.utils.DateOperationsUtils;
import com.epam.carrental.utils.MathUtils;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class StatisticsReport {

    @Autowired
    private StatisticsCalculator statisticsCalculator;
    @Autowired
    private DateOperationsUtils dateOperationsUtils;
    @Autowired
    private MathUtils mathUtils;

    private List<TotalStatisticsDTO> prepareTotalStatistics(Long totalDaysInPeriod,Long totalCars,Long totalCarsUsage,Map<String, Float> totalEarnings, Map<String, Float> totalUtilizationMap, Set<String> rentalClassNames) {
        List<TotalStatisticsDTO> totalStatisticsList = new ArrayList<>();

        float sumOfTotalEarnings = 0.0f;


        for (String className : rentalClassNames) {

            float earnings = totalEarnings.containsKey(className) ? totalEarnings.get(className) : 0.0f;
            float utilization = totalUtilizationMap.containsKey(className) ? totalUtilizationMap.get(className) : 0.0f;

            TotalStatisticsDTO totalStatistics = new TotalStatisticsDTO();
            totalStatistics.setRentalClassName(className);
            totalStatistics.setUtilization(utilization);
            totalStatistics.setEarnings(earnings);

            totalStatisticsList.add(totalStatistics);
            sumOfTotalEarnings += earnings;
        }
        float totalUtilization=statisticsCalculator.calculateTotalUtilization(totalDaysInPeriod,totalCarsUsage,totalCars);

        TotalStatisticsDTO totalStatisticsForAll = new TotalStatisticsDTO();
        totalStatisticsForAll.setRentalClassName("Total:");
        totalStatisticsForAll.setUtilization(totalUtilization);
        totalStatisticsForAll.setEarnings(sumOfTotalEarnings);

        totalStatisticsList.add(totalStatisticsForAll);

        return totalStatisticsList;
    }

    private List<DayStatisticsDTO> prepareDailyStatistics(Table<String, DayOfWeek, Float> utilizationPerDay,Set<String> rentalClassNames) {
        List<DayStatisticsDTO> dayStatisticsList = new ArrayList<>();

        for (String rentalClassName : rentalClassNames) {
            DayStatisticsDTO dayStatistics = new DayStatisticsDTO();
            dayStatistics.setRentalClassName(rentalClassName);

            Map<DayOfWeek, Float> utilizationPerDayMap = Arrays.stream(DayOfWeek.values()).collect(Collectors.toMap(o -> o, o -> 0F)) ;
            utilizationPerDayMap.putAll(utilizationPerDay.row(rentalClassName));
            dayStatistics.setUtilizationPerDay(utilizationPerDayMap);

            dayStatisticsList.add(dayStatistics);
        }
        return dayStatisticsList;
    }


    public StatisticsDTO generate(Map<String, Long> carAmountPerClass, List<RentedCarHistory> rentedCarHistories, ZonedDateTime startDate, ZonedDateTime endDate) {

        Map<String, Float> totalEarningsPerClass = statisticsCalculator.calculateTotalEarningsPerClass(rentedCarHistories);
        Map<String, Float> totalUtilizationPerClass = statisticsCalculator.calculateTotalUtilizationPerClass(carAmountPerClass, rentedCarHistories, startDate, endDate);
        Table<String, DayOfWeek, Float> totalUtilizationPerClassAndDayTable = statisticsCalculator.calculateUtilizationPerClassAndDay(carAmountPerClass, rentedCarHistories, startDate, endDate);

        long totalDaysInPeriod=dateOperationsUtils.getDaysBetweenDates(startDate,endDate);
        long totalCarsNumber= carAmountPerClass.values().stream().mapToLong(Long::longValue).sum();
        long sumOfCarsUsageHours = statisticsCalculator.getTotalHoursCarsUsage(rentedCarHistories);


        Set<String> rentalClassNames = carAmountPerClass.keySet();
        List<TotalStatisticsDTO> totalStatisticsDTOS = prepareTotalStatistics(totalDaysInPeriod,totalCarsNumber,sumOfCarsUsageHours,totalEarningsPerClass, totalUtilizationPerClass, rentalClassNames);
        List<DayStatisticsDTO> dayStatisticsDTOS = prepareDailyStatistics(totalUtilizationPerClassAndDayTable,rentalClassNames);

        StatisticsDTO statisticsDTO = new StatisticsDTO();
        statisticsDTO.setDayStatisticsDTO(dayStatisticsDTOS);
        statisticsDTO.setTotalStatisticsDTO(totalStatisticsDTOS);
        return statisticsDTO;
    }
}
