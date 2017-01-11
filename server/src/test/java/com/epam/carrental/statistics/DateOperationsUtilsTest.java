package com.epam.carrental.statistics;

import com.epam.carrental.utils.DateOperationsUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Map;

public class DateOperationsUtilsTest {

    DateOperationsUtils dateOperationsUtils = new DateOperationsUtils();

    private ZonedDateTime getTime(String time) {
        return ZonedDateTime.parse(time);
    }

    @Test
    public void minutesInOneDayPeriod() {

        ZonedDateTime startDate = getTime("2016-12-01T01:00:00Z");
        ZonedDateTime endDate = getTime("2016-12-01T03:30:00Z");

        Map<DayOfWeek, Long> expectedMapWithDays = dateOperationsUtils.prepareMapWithDays();
        expectedMapWithDays.put(DayOfWeek.THURSDAY, 150L);

        Map<DayOfWeek, Long> resultMapWithDays = dateOperationsUtils.calculateMinutesPerDayOfWeek(startDate, endDate);

        Assert.assertEquals(expectedMapWithDays, resultMapWithDays);

    }

    @Test
    public void minutesInTwoDaysPeriod() {

        ZonedDateTime startDate = getTime("2016-12-01T23:00:00Z");
        ZonedDateTime endDate = getTime("2016-12-02T01:30:00Z");

        Map<DayOfWeek, Long> expectedMapWithDays = dateOperationsUtils.prepareMapWithDays();
        expectedMapWithDays.put(DayOfWeek.THURSDAY, 60L);
        expectedMapWithDays.put(DayOfWeek.FRIDAY, 90L);

        Map<DayOfWeek, Long> resultMapWithDays = dateOperationsUtils.calculateMinutesPerDayOfWeek(startDate, endDate);

        Assert.assertEquals(expectedMapWithDays, resultMapWithDays);

    }

    @Test
    public void minutesInThreeDaysPeriod() {

        ZonedDateTime startDate = getTime("2016-12-01T23:00:00Z");
        ZonedDateTime endDate = getTime("2016-12-03T01:30:00Z");

        Map<DayOfWeek, Long> expectedMapWithDays = dateOperationsUtils.prepareMapWithDays();
        expectedMapWithDays.put(DayOfWeek.THURSDAY, 60L);
        expectedMapWithDays.put(DayOfWeek.FRIDAY, 1440L);
        expectedMapWithDays.put(DayOfWeek.SATURDAY, 90L);

        Map<DayOfWeek, Long> resultMapWithDays = dateOperationsUtils.calculateMinutesPerDayOfWeek(startDate, endDate);

        Assert.assertEquals(expectedMapWithDays, resultMapWithDays);

    }

    @Test
    public void minutesInOneWeekPeriod() {

        ZonedDateTime startDate = getTime("2016-12-01T23:00:00Z");
        ZonedDateTime endDate = getTime("2016-12-07T01:30:00Z");

        Map<DayOfWeek, Long> expectedMapWithDays = dateOperationsUtils.prepareMapWithDays();
        expectedMapWithDays.put(DayOfWeek.THURSDAY, 60L);
        expectedMapWithDays.put(DayOfWeek.FRIDAY, 1440L);
        expectedMapWithDays.put(DayOfWeek.SATURDAY, 1440L);
        expectedMapWithDays.put(DayOfWeek.SUNDAY, 1440L);
        expectedMapWithDays.put(DayOfWeek.MONDAY, 1440L);
        expectedMapWithDays.put(DayOfWeek.TUESDAY, 1440L);
        expectedMapWithDays.put(DayOfWeek.WEDNESDAY, 90L);

        Map<DayOfWeek, Long> resultMapWithDays = dateOperationsUtils.calculateMinutesPerDayOfWeek(startDate, endDate);

        Assert.assertEquals(expectedMapWithDays, resultMapWithDays);

    }

    @Test
    public void minutesInOneMonthPeriod() {

        ZonedDateTime startDate = getTime("2016-12-01T23:00:00Z");
        ZonedDateTime endDate = getTime("2016-12-31T01:30:00Z");

        Map<DayOfWeek, Long> expectedMapWithDays = dateOperationsUtils.prepareMapWithDays();
        expectedMapWithDays.put(DayOfWeek.THURSDAY, 5820L);
        expectedMapWithDays.put(DayOfWeek.FRIDAY, 7200L);
        expectedMapWithDays.put(DayOfWeek.SATURDAY, 5850L);
        expectedMapWithDays.put(DayOfWeek.SUNDAY, 5760L);
        expectedMapWithDays.put(DayOfWeek.MONDAY, 5760L);
        expectedMapWithDays.put(DayOfWeek.TUESDAY, 5760L);
        expectedMapWithDays.put(DayOfWeek.WEDNESDAY, 5760L);

        Map<DayOfWeek, Long> resultMapWithDays = dateOperationsUtils.calculateMinutesPerDayOfWeek(startDate, endDate);

        Assert.assertEquals(expectedMapWithDays, resultMapWithDays);
    }


}