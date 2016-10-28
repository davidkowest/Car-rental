package com.epam.carrental.data_generator;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.ZonedDateTime;

import static org.testng.Assert.assertTrue;

public class ZonedDateTimeGeneratorTest {

    @Test(dataProvider = "provideTime" )
    public void testGeneratedTime(String start, long timeMin, long timeMax) {
        //arrange
        ZonedDateTimeGenerator generator = new ZonedDateTimeGenerator();
        generator.timeMin = timeMin;
        generator.timeMax = timeMax;

        //act
        ZonedDateTime startTime = ZonedDateTime.parse(start);
        ZonedDateTime time = generator.getTimeAfter(startTime);

        //assert
        assertTrue(time.isAfter(startTime), "Found time: "+time+ "is not after StartTime: "+startTime);
        assertTrue(Duration.between(startTime, time).toHours()>=timeMin, "Difference expected more than "+timeMin+" but found "+Duration.between(startTime, time).toHours());
        assertTrue(Duration.between(startTime,time).toHours()<=timeMax, "Difference between expected less than "+timeMax+" but found "+Duration.between(startTime,time).toHours());
    }

    @DataProvider
    private Object[][] provideTime(){
        return new Object[][]{
                {"2015-08-16T15:23:01Z",3L,10L},
                {"2012-01-09T02:29:01Z",12L,1200L},
                {"1000-11-29T13:13:01Z",1L,5L},
        };
    }
}
