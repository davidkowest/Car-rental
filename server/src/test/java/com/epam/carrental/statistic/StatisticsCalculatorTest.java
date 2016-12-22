package com.epam.carrental.statistic;

import com.epam.carrental.entity.Car;
import com.epam.carrental.entity.Customer;
import com.epam.carrental.entity.RentalClass;
import com.epam.carrental.entity.RentedCarHistory;
import com.epam.carrental.utils.MathUtils;
import com.google.common.collect.Table;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.*;

public class StatisticsCalculatorTest {

    private StatisticsCalculator statisticsCalculator;

    private Map<String, RentalClass> classes = getClasses();
    private List<Car> economyCars = economyCars();
    private List<Car> compactCars = compactCars();
    private List<Car> fullsizeCars = fullsizeCars();
    private Map<String, Long> rentalClasses = prepareFleetMap();

    @BeforeTest
    public void prepare() {
        statisticsCalculator = new StatisticsCalculator();
        MathUtils mathUtils = new MathUtils();
        DateOperationsUtils dateOperationsUtils = new DateOperationsUtils();
        statisticsCalculator.mathUtils = mathUtils;
        statisticsCalculator.dateOperationsUtils = dateOperationsUtils;
    }

    @Test
    public void testUtilizationForShortPeriod() throws Exception {
        //arrange
        ZonedDateTime startDate = getTime("2015-09-14T00:00:00Z"); // is not testing crop overlapping,
        ZonedDateTime endDate = getTime("2015-09-18T00:00:00Z");   // all rentals within this range - 4 days
        List<RentedCarHistory> rentedCarHistories = getListOfHistoriesForShortPeriod();
        //act
        Map<String, Float> result = statisticsCalculator.calculateTotalUtilization(rentalClasses, rentedCarHistories, startDate, endDate);
        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result.get("economy"), 9.00f, "Economy: expected: 9.00f, actual:  " + result.get("economy")); //(rentedHours)/(cars*(18thOfDecember,00.00-14thOfDecember,00.00))=> (108)/(3*(4 days))
        sa.assertEquals(result.get("compact"), 15.33f, "Compact: expected: 15.33f, actual:  " + result.get("compact")); //(184)/(3*4)
        sa.assertEquals(result.get("fullsize"), 15.75f, "Fullsize: expected: 15.75f, actual:  " + result.get("fullsize")); //(189)/(3*4)
        sa.assertAll();
    }

    @Test
    public void testUtilizationForLongPeriod() throws Exception {
        //arrange
        ZonedDateTime startDate = getTime("2015-09-14T00:00:00Z"); // is not testing crop overlapping,
        ZonedDateTime endDate = getTime("2016-09-14T00:00:00Z");   // all rentals within this range - 366 days
        List<RentedCarHistory> rentedCarHistories = getListOfHistoriesForLongPeriod();
        //act
        Map<String, Float> result = statisticsCalculator.calculateTotalUtilization(rentalClasses, rentedCarHistories, startDate, endDate);
        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result.get("economy"), 8.12f, "Economy: expected: 8.12f, actual:  " + result.get("economy")); // 8916 /(3*366)
        sa.assertEquals(result.get("compact"), 14.81f, "Compact: expected: 14.81f, actual:  " + result.get("compact")); // 16264/(3*366)
        sa.assertEquals(result.get("fullsize"), 22.82f, "Fullsize: expected: 22.82f, actual:  " + result.get("fullsize")); // 25053/(3*366)
        sa.assertAll();
    }

    @Test
    public void testEarningsForShortPeriod() throws Exception {
        //arrange
        List<RentedCarHistory> rentedCarHistories = getListOfHistoriesForShortPeriod();
        //act
        Map<String, Float> result = statisticsCalculator.calculateTotalEarnings(rentedCarHistories);
        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result.get("economy"), 264.6f, "Economy: expected: 264.6f, actual:  " + result.get("economy")); //(108)*2.45f
        sa.assertEquals(result.get("compact"), 625.6f, "Compact: expected: 625.6f, actual:  " + result.get("compact")); ////(184)*3.4f
        sa.assertEquals(result.get("fullsize"), 888.3f, "Fullsize: expected: 888.3f, actual:  " + result.get("fullsize")); //(189)*4.7f
        sa.assertAll();
    }

    @Test
    public void testUtilizationPerDayForShortPeriod() {
        //arrange
        List<RentedCarHistory> rentedCarHistories = getListOfHistoriesForShortPeriod();
        ZonedDateTime startDate = getTime("2015-09-14T00:00:00Z");
        ZonedDateTime endDate = getTime("2015-09-18T00:00:00Z");
        //act
        Table<String, DayOfWeek, Float> result = statisticsCalculator.calculateUtilizationPerDay(rentalClasses, rentedCarHistories, startDate, endDate);
        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result.get("economy", DayOfWeek.MONDAY), 7.33f, "Economy-MON: expected: 7.33f, actual: " + result.get("economy", DayOfWeek.MONDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.TUESDAY), 14.0f, "Economy-TUE: expected: 14.0f, actual: " + result.get("economy", DayOfWeek.TUESDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.WEDNESDAY), 12.0f, "Economy-WED: expected: 12.0f, actual: " + result.get("economy", DayOfWeek.WEDNESDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.THURSDAY), 2.67f, "Economy-THU: expected: 2.67f, actual: " + result.get("economy", DayOfWeek.THURSDAY));

        sa.assertEquals(result.get("compact", DayOfWeek.MONDAY), 7.33f, "Compact-MON: expected: 7.33f, actual:  " + result.get("compact", DayOfWeek.MONDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.TUESDAY), 18.0f, "Compact-TUE: expected: 18.0f, actual:  " + result.get("compact", DayOfWeek.TUESDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.WEDNESDAY), 21.33f, "Compact-WED: expected: 21.33f, actual:  " + result.get("compact", DayOfWeek.WEDNESDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.THURSDAY), 14.67f, "Compact-THU: expected: 14.67f, actual:  " + result.get("compact", DayOfWeek.THURSDAY));

        sa.assertEquals(result.get("fullsize", DayOfWeek.MONDAY), 10.67f, "Fullsize-MON: expected: 10.67f, actual:  " + result.get("fullsize", DayOfWeek.MONDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.TUESDAY), 16.67f, "Fullsize-TUE: expected: 16.67f, actual:  " + result.get("fullsize", DayOfWeek.TUESDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.WEDNESDAY), 21.67f, "Fullsize-WED: expected: 21.67f, actual:  " + result.get("fullsize", DayOfWeek.WEDNESDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.THURSDAY), 14.0f, "Fullsize-THU: expected: 14.0f, actual:  " + result.get("fullsize", DayOfWeek.THURSDAY));
        sa.assertAll();

    }

    @Test
    public void testUtilizationPerDayForLongPeriod() {
        //arrange
        List<RentedCarHistory> rentedCarHistories = getListOfHistoriesForLongPeriod();
        ZonedDateTime startDate = getTime("2015-09-14T00:00:00Z");
        ZonedDateTime endDate = getTime("2016-09-18T00:00:00Z");
        //act
        Table<String, DayOfWeek, Float> result = statisticsCalculator.calculateUtilizationPerDay(rentalClasses, rentedCarHistories, startDate, endDate);
        //assert
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(result.get("economy", DayOfWeek.MONDAY), 8.14f, "Economy-MON: expected: 8.14f, actual: " + result.get("economy", DayOfWeek.MONDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.TUESDAY), 8.42f, "Economy-TUE: expected: 8.42f, actual: " + result.get("economy", DayOfWeek.TUESDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.WEDNESDAY), 8.08f, "Economy-WED: expected: 8.08f, actual: " + result.get("economy", DayOfWeek.WEDNESDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.THURSDAY), 7.9f, "Economy-THU: expected: 7.9f, actual: " + result.get("economy", DayOfWeek.THURSDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.FRIDAY), 7.85f, "Economy-FRI: expected: 7.85f, actual: " + result.get("economy", DayOfWeek.FRIDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.SATURDAY), 7.85f, "Economy-SAT: expected:7.85f, actual: " + result.get("economy", DayOfWeek.SATURDAY));
        sa.assertEquals(result.get("economy", DayOfWeek.SUNDAY), 8.0f, "Economy-SUN: expected: 8.0f, actual: " + result.get("economy", DayOfWeek.SUNDAY));

        sa.assertEquals(result.get("compact", DayOfWeek.MONDAY), 14.78f, "Compact-MON: expected: 14.78f, actual:  " + result.get("compact", DayOfWeek.MONDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.TUESDAY), 14.83f, "Compact-TUE: expected: 14.83f, actual:  " + result.get("compact", DayOfWeek.TUESDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.WEDNESDAY), 14.74f, "Compact-WED: expected: 14.74f, actual:  " + result.get("compact", DayOfWeek.WEDNESDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.THURSDAY), 14.62f, "Compact-THU: expected: 14.62f, actual:  " + result.get("compact", DayOfWeek.THURSDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.FRIDAY), 14.34f, "Compact-FRI: expected: 14.34f, actual:  " + result.get("compact", DayOfWeek.FRIDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.SATURDAY), 14.49f, "Compact-SAT: expected: 14.49f, actual:  " + result.get("compact", DayOfWeek.SATURDAY));
        sa.assertEquals(result.get("compact", DayOfWeek.SUNDAY), 14.77f, "Compact-SUN: expected: 14.77f, actual:  " + result.get("compact", DayOfWeek.SUNDAY));

        sa.assertEquals(result.get("fullsize", DayOfWeek.MONDAY), 22.69f, "Fullsize-MON: expected: 22.69f, actual:  " + result.get("fullsize", DayOfWeek.MONDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.TUESDAY), 22.96f, "Fullsize-TUE: expected: 22.96f, actual:  " + result.get("fullsize", DayOfWeek.TUESDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.WEDNESDAY), 22.6f, "Fullsize-WED: expected: 22.6f, actual:  " + result.get("fullsize", DayOfWeek.WEDNESDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.THURSDAY), 22.6f, "Fullsize-THU: expected: 22.6f, actual:  " + result.get("fullsize", DayOfWeek.THURSDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.FRIDAY), 22.34f, "Fullsize-FRI: expected: 22.34f, actual:  " + result.get("fullsize", DayOfWeek.FRIDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.SATURDAY), 22.19f, "Fullsize-SAT: expected: 22.19f, actual:  " + result.get("fullsize", DayOfWeek.SATURDAY));
        sa.assertEquals(result.get("fullsize", DayOfWeek.SUNDAY), 22.62f, "Fullsize-SUN: expected: 22.62f, actual:  " + result.get("fullsize", DayOfWeek.SUNDAY));
        sa.assertAll();

    }

    private List<RentedCarHistory> getListOfHistoriesForShortPeriod() {
        return Arrays.asList(
                // Economy class cars
                new RentedCarHistory(economyCars.get(0), getCustomer(),
                        getTime("2015-09-14T15:00:00Z"), getTime("2015-09-14T20:00:00Z")), // 5 hours in Mo
                new RentedCarHistory(economyCars.get(0), getCustomer(),
                        getTime("2015-09-14T21:00:00Z"), getTime("2015-09-15T20:00:00Z")), // 3 hours in Mo + 20 in Tu
                new RentedCarHistory(economyCars.get(0), getCustomer(),
                        getTime("2015-09-16T04:00:00Z"), getTime("2015-09-16T16:00:00Z")), // 12 hours in We
                new RentedCarHistory(economyCars.get(1), getCustomer(),
                        getTime("2015-09-14T10:00:00Z"), getTime("2015-09-15T08:00:00Z")), // 14 hours in Mo + 8 in Tu
                new RentedCarHistory(economyCars.get(1), getCustomer(),
                        getTime("2015-09-15T10:00:00Z"), getTime("2015-09-15T19:00:00Z")), // 9 hours in Tu
                new RentedCarHistory(economyCars.get(2), getCustomer(),
                        getTime("2015-09-15T19:00:00Z"), getTime("2015-09-17T08:00:00Z")), // 5 hours in Tu + 24 in We + 8 in Th
                // Compact class cars
                new RentedCarHistory(compactCars.get(0), getCustomer(),
                        getTime("2015-09-14T11:00:00Z"), getTime("2015-09-14T15:00:00Z")), // 4 hours in Mo
                new RentedCarHistory(compactCars.get(0), getCustomer(),
                        getTime("2015-09-14T17:00:00Z"), getTime("2015-09-14T23:00:00Z")), // 6 hours in Mo
                new RentedCarHistory(compactCars.get(0), getCustomer(),
                        getTime("2015-09-15T07:00:00Z"), getTime("2015-09-15T23:00:00Z")), // 16 hours in Tu
                new RentedCarHistory(compactCars.get(0), getCustomer(),
                        getTime("2015-09-16T05:00:00Z"), getTime("2015-09-17T21:00:00Z")), // 19 hours in We + 21 in Th
                new RentedCarHistory(compactCars.get(1), getCustomer(),
                        getTime("2015-09-15T09:00:00Z"), getTime("2015-09-15T23:00:00Z")), // 14 hours in Tu
                new RentedCarHistory(compactCars.get(1), getCustomer(),
                        getTime("2015-09-16T03:00:00Z"), getTime("2015-09-17T13:00:00Z")), // 21 hours in We + 13 in Th
                new RentedCarHistory(compactCars.get(2), getCustomer(),
                        getTime("2015-09-14T12:00:00Z"), getTime("2015-09-17T10:00:00Z")), // 12 hours in Mo + 24 in Tu + 24 in We + 10 in Th
                //Fullsize class cars
                new RentedCarHistory(fullsizeCars.get(0), getCustomer(),
                        getTime("2015-09-14T06:00:00Z"), getTime("2015-09-14T10:00:00Z")), // 4 hours in Mo
                new RentedCarHistory(fullsizeCars.get(0), getCustomer(),
                        getTime("2015-09-14T16:00:00Z"), getTime("2015-09-15T16:00:00Z")), // 8 hours in Mo + 16 in Tu
                new RentedCarHistory(fullsizeCars.get(0), getCustomer(),
                        getTime("2015-09-15T20:00:00Z"), getTime("2015-09-16T14:00:00Z")), // 4 hours in Tu + 14 in We
                new RentedCarHistory(fullsizeCars.get(0), getCustomer(),
                        getTime("2015-09-16T20:00:00Z"), getTime("2015-09-17T14:00:00Z")), // 4 hours in We + 14 in Th
                new RentedCarHistory(fullsizeCars.get(1), getCustomer(),
                        getTime("2015-09-15T18:00:00Z"), getTime("2015-09-16T23:00:00Z")), // 6 hours in Tu + 23 hours in We
                new RentedCarHistory(fullsizeCars.get(1), getCustomer(),
                        getTime("2015-09-17T04:00:00Z"), getTime("2015-09-17T20:00:00Z")), // 16 hours in Th
                new RentedCarHistory(fullsizeCars.get(2), getCustomer(),
                        getTime("2015-09-14T04:00:00Z"), getTime("2015-09-17T12:00:00Z")));// 20 hours in Mo + 24 in Tu + 24 in We + 12 in Th
    }

    private List<RentedCarHistory> getListOfHistoriesForLongPeriod() {
        List<RentedCarHistory> rentedCarHistories = new ArrayList<>();
        rentedCarHistories.addAll(getListOfHistoriesForShortPeriod());
        // Economy class cars
        rentedCarHistories.add(new RentedCarHistory(economyCars.get(0), getCustomer(),
                getTime("2015-09-14T00:00:00Z"), getTime("2015-10-14T00:00:00Z"))); //was rented for 30 days
        rentedCarHistories.add(new RentedCarHistory(economyCars.get(1), getCustomer(),
                getTime("2015-09-17T00:00:00Z"), getTime("2016-02-17T00:00:00Z"))); //was rented for 153 days
        rentedCarHistories.add(new RentedCarHistory(economyCars.get(2), getCustomer(),
                getTime("2016-03-01T00:00:00Z"), getTime("2016-09-01T00:00:00Z"))); //was rented for 184 days
        // Compact class cars
        rentedCarHistories.add(new RentedCarHistory(compactCars.get(0), getCustomer(),
                getTime("2015-09-14T00:00:00Z"), getTime("2015-12-14T00:00:00Z"))); //was rented 91 days
        rentedCarHistories.add(new RentedCarHistory(compactCars.get(1), getCustomer(),
                getTime("2015-09-14T00:00:00Z"), getTime("2016-09-14T00:00:00Z"))); //was rented 366 days
        rentedCarHistories.add(new RentedCarHistory(compactCars.get(2), getCustomer(),
                getTime("2015-11-14T00:00:00Z"), getTime("2016-06-14T00:00:00Z"))); //was rented 213 days
        //Fullsize class cars
        rentedCarHistories.add(new RentedCarHistory(fullsizeCars.get(0), getCustomer(),
                getTime("2015-09-14T00:00:00Z"), getTime("2016-09-14T00:00:00Z"))); //was rented 366 days
        rentedCarHistories.add(new RentedCarHistory(fullsizeCars.get(1), getCustomer(),
                getTime("2015-09-14T00:00:00Z"), getTime("2015-11-14T00:00:00Z"))); //was rented 61 days
        rentedCarHistories.add(new RentedCarHistory(fullsizeCars.get(1), getCustomer(),
                getTime("2015-11-17T00:00:00Z"), getTime("2016-08-17T00:00:00Z"))); //was rented 274 days
        rentedCarHistories.add(new RentedCarHistory(fullsizeCars.get(2), getCustomer(),
                getTime("2015-09-10T00:00:00Z"), getTime("2016-08-10T00:00:00Z"))); //was rented 335 days

        return rentedCarHistories;
    }

    private List<Car> fullsizeCars() {
        RentalClass fullsizeClass = classes.get("fullsize");
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Chrysler Cirrus", "KR34567", fullsizeClass));
        cars.add(new Car("Chrysler Concorde", "WAR34567", fullsizeClass));
        cars.add(new Car("Honda del Sol", "RZE34567", fullsizeClass));
        return cars;
    }

    private List<Car> economyCars() {
        RentalClass economyClass = classes.get("economy");
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Nissan 200SX", "KR12345", economyClass));
        cars.add(new Car("Jeep Cherokee", "WAR12345", economyClass));
        cars.add(new Car("Kia Sportage", "RZE12345", economyClass));
        return cars;
    }

    private List<Car> compactCars() {
        RentalClass compactClass = classes.get("compact");
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Subaru Legacy", "KR23456", compactClass));
        cars.add(new Car("Honda Passport", "WAR23456", compactClass));
        cars.add(new Car("Honda Prelude", "RZE23456", compactClass));
        return cars;
    }

    private Map<String, RentalClass> getClasses() {
        Map<String, RentalClass> classes = new HashMap<>();
        classes.put("economy", new RentalClass("economy", 2.45f));
        classes.put("compact", new RentalClass("compact", 3.4f));
        classes.put("fullsize", new RentalClass("fullsize", 4.7f));
        return classes;
    }

    private Map<String, Long> prepareFleetMap() {

        Map<String, Long> map = new HashMap<>();
        map.put("economy", (long) economyCars.size());
        map.put("compact", (long) compactCars.size());
        map.put("fullsize", (long) fullsizeCars.size());
        return map;
    }

    private Customer getCustomer() {
        return new Customer("Adam Malysz", "adam.malysz@gmail.com");
    }

    private ZonedDateTime getTime(String time) {
        return ZonedDateTime.parse(time);
    }

}