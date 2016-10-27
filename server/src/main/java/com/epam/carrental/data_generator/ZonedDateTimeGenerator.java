package com.epam.carrental.data_generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class ZonedDateTimeGenerator {

    @Value("${rented.time.min.sec}")
    private long timeMin;

    @Value("${rented.time.max.sec}")
    private long timeMax;

    @Value("${rented.time.initial}")
    private String timeInitial;

    public ZonedDateTime getTimeAfter(ZonedDateTime beginTime) {
        long beginTimeAsLong=beginTime.toEpochSecond();
        beginTimeAsLong = beginTimeAsLong + (long) (Math.random() * (timeMax-timeMin));

        Instant instant = Instant.ofEpochSecond(beginTimeAsLong);
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
