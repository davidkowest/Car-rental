package com.epam.carrental.statistics;

import com.epam.carrental.rental_classes.RentalClass;
import com.epam.carrental.rentals.history.RentedCarHistory;
import com.epam.carrental.utils.DateOperationsUtils;
import com.epam.carrental.utils.MathUtils;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatisticsCalculator {

    @Autowired
    MathUtils mathUtils;
    @Autowired
    DateOperationsUtils dateOperationsUtils;

    public Table<String, DayOfWeek, Float> calculateUtilizationPerClassAndDay(Map<String, Long> fleetMap, List<RentedCarHistory> rentedCarHistories,
                                                                              ZonedDateTime startDate, ZonedDateTime endDate) {

        Table<String, DayOfWeek, Long> sumOfMinutesPerClassAndDay = calculateMinutesPerClassAndDay(rentedCarHistories);

        Map<DayOfWeek, Long> sumMinutesPerDayInWholePeriod = dateOperationsUtils.calculateMinutesPerDayOfWeek(startDate, endDate);
        Table<String, DayOfWeek, Float> utilizationPerDayAndClass = HashBasedTable.create();

        for (Table.Cell<String, DayOfWeek, Long> cell : sumOfMinutesPerClassAndDay.cellSet()) {
            String rentalClassName = cell.getRowKey();
            DayOfWeek dayOfWeek=cell.getColumnKey();

            float result = 0;
            long carAmount = fleetMap.get(rentalClassName);
            long maxMinutesInDayOfWeek = sumMinutesPerDayInWholePeriod.get(dayOfWeek);
            @SuppressWarnings("ConstantConditions")
            long sumOfCarsUsageMinutes = cell.getValue();

            if (carAmount != 0 && maxMinutesInDayOfWeek != 0 && sumOfCarsUsageMinutes != 0) {
                result = mathUtils.round(sumOfCarsUsageMinutes * 24 / (carAmount * maxMinutesInDayOfWeek * 1.0F), 2);
            }

            utilizationPerDayAndClass.put(rentalClassName, dayOfWeek, result);
        }

        return utilizationPerDayAndClass;
    }

    public Map<String, Float> calculateTotalEarningsPerClass(List<RentedCarHistory> carClassHistoryList) {
        return getHoursPerRentalClass(carClassHistoryList)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(o -> o.getKey().getName(),
                        o -> mathUtils.round(o.getValue() * o.getKey().getHourlyRate(), 2)));
    }

    public Map<String, Float> calculateTotalUtilizationPerClass(Map<String, Long> carsPerRentalClass, List<RentedCarHistory> carClassHistoryList, ZonedDateTime startDate, ZonedDateTime endDate) {
        long totalDaysRange = Duration.between(startDate, endDate).toDays();
        Map<RentalClass, Long> hoursPerRentalClass = getHoursPerRentalClass(carClassHistoryList);

        return hoursPerRentalClass
                .entrySet()
                .stream()
                .collect(Collectors
                        .toMap(o -> o.getKey().getName(), o -> mathUtils.round(o.getValue() * 1.0f / (carsPerRentalClass.get(o.getKey().getName()) * totalDaysRange), 2)));
    }

    public Float calculateTotalUtilization(long totalDaysInRange, long sumOfCarUsageHours,long carsAmount) {
        return mathUtils.round(sumOfCarUsageHours*1.0f/(carsAmount*totalDaysInRange),2);
    }

    private Table<String, DayOfWeek, Long> calculateMinutesPerClassAndDay(List<RentedCarHistory> rentedCarHistories) {

        Table<String, DayOfWeek, Long> sumOfMinutesPerClassAndDay = HashBasedTable.create();

        for (RentedCarHistory rentedCarHistory : rentedCarHistories) {
            String rentalClassName = rentedCarHistory.getCar().getRentalClass().getName();

            Map<DayOfWeek, Long> minutesPerDayOfWeek = dateOperationsUtils.calculateMinutesPerDayOfWeek(rentedCarHistory.getStartDate(), rentedCarHistory.getEndDate());
            for (Map.Entry<DayOfWeek, Long> entry : minutesPerDayOfWeek.entrySet()) {
                DayOfWeek dayOfWeek = entry.getKey();

                long sum = sumOfMinutesPerClassAndDay.contains(rentalClassName, dayOfWeek) ? sumOfMinutesPerClassAndDay.get(rentalClassName, dayOfWeek) : 0L;
                sum += entry.getValue();
                sumOfMinutesPerClassAndDay.put(rentalClassName, dayOfWeek, sum);
            }
        }
        return sumOfMinutesPerClassAndDay;
    }

    private Map<RentalClass, Long> getHoursPerRentalClass(List<RentedCarHistory> carClassHistoryList) {
        return carClassHistoryList
                .stream()
                .collect(Collectors.groupingBy(o -> o.getCar().getRentalClass(),
                        Collectors.summingLong(v -> Duration.between(v.getStartDate(), v.getEndDate()).toHours())));
    }

    Long getTotalHoursCarsUsage(List<RentedCarHistory> carClassHistoryList){
        return carClassHistoryList
                .stream()
                .collect(Collectors.summingLong(v -> Duration.between(v.getStartDate(), v.getEndDate()).toHours()));
    }
}
