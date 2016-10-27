package com.epam.carrental.data_generator;


import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class CurrentTimeUtil {

    private ZonedDateTime currentTime=null;

    public ZonedDateTime getCurrentTime() {
        return currentTime==null?ZonedDateTime.now(ZoneId.systemDefault()):currentTime;
    }

    void setCurrentTime(ZonedDateTime currentTime) {
        this.currentTime = currentTime;
    }
}
