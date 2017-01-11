package com.epam.carrental.utils;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class DateOperationsUtils {

    public Map<DayOfWeek, Long> prepareMapWithDays() {
        return Arrays.stream(DayOfWeek.values()).collect(Collectors.toMap(o -> o, o -> 0L));
    }

    public Map<DayOfWeek, Long> calculateMinutesPerDayOfWeek(ZonedDateTime startDate, ZonedDateTime endDate) {
        Map<DayOfWeek, Long> minutesPerDayOfWeek = prepareMapWithDays();

        ZonedDateTime tempStartDate = startDate;
        ZonedDateTime tempEndDate = getDateAfter(tempStartDate, 1);
        boolean finish = false;

        for (int i = 1; i <= 7 && !finish; i++) {
            long minutes;
            if (tempEndDate.isAfter(endDate)) {
                tempEndDate = endDate;
                finish = true;
            }
            minutes = getMinutesBetweenDates(tempStartDate, tempEndDate);

            //calculate next weeks
            long weeksBetweenStartAndEnd = getWeeksBetween(tempStartDate, endDate);
            if (weeksBetweenStartAndEnd > 0) {

                ZonedDateTime lastWeekStartDate = getDateAfter(tempStartDate, weeksBetweenStartAndEnd * 7);
                ZonedDateTime lastWeekEndDate = getDateAfter(tempEndDate, weeksBetweenStartAndEnd * 7);

                if (lastWeekEndDate.isAfter(endDate)) {
                    lastWeekEndDate = endDate;
                }
                //whole days between
                minutes += (weeksBetweenStartAndEnd - 1) * 24 * 60;
                minutes += getMinutesBetweenDates(lastWeekStartDate, lastWeekEndDate);
            }

            minutesPerDayOfWeek.put(tempStartDate.getDayOfWeek(), minutes);
            tempStartDate = tempEndDate;
            tempEndDate = getDateAfter(tempStartDate, 1);
        }

        return minutesPerDayOfWeek;
    }

    private long getWeeksBetween(ZonedDateTime startDate, ZonedDateTime endDate) {
        return ChronoUnit.WEEKS.between(startDate.toLocalDate(), endDate.toLocalDate());
    }

    private ZonedDateTime getDateAfter(ZonedDateTime dateTime, long days) {
        return ZonedDateTime.of(dateTime.toLocalDate().plusDays(days), LocalTime.MIDNIGHT, dateTime.getZone());
    }

    public Long getMinutesBetweenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
        return Duration.between(startDate, endDate).toMinutes();
    }
    public Long getDaysBetweenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
        return Duration.between(startDate, endDate).toDays();
    }
}
