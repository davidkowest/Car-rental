package com.epam.carrental.data_generator;


import org.testng.annotations.Test;

import java.time.ZonedDateTime;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class CurrentTimeUtilTest {

    @Test
    public void getCurrentTimeAfterSettingTest(){
        //arrange
        CurrentTimeUtil currentTimeUtil = new CurrentTimeUtil();

        // act
        ZonedDateTime timeToSet = ZonedDateTime.now();
        currentTimeUtil.setCurrentTime(timeToSet);
        ZonedDateTime actualTime = currentTimeUtil.getCurrentTime();

        //assert
        assertEquals(actualTime,timeToSet,"actual time is: "+ actualTime+" expected time is: "+timeToSet);
    }

    @Test
    public void getCurrentTimeBeforeSettingTest(){
        //arrange
        CurrentTimeUtil currentTimeUtil = new CurrentTimeUtil();

        // act
        ZonedDateTime actualTime = currentTimeUtil.getCurrentTime();

        //assert
        assertTrue(actualTime!=null);
    }
}