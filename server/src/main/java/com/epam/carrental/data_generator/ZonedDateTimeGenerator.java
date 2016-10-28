package com.epam.carrental.data_generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class ZonedDateTimeGenerator {

    @Value("${rented.time.min.hours}")
     Long timeMin;

    @Value("${rented.time.max.hours}")
     Long timeMax;

    public ZonedDateTime getTimeAfter(ZonedDateTime beginTime) {
        long additionalTime = timeMin+(long) (Math.random() * (timeMax - timeMin));

        return beginTime.plusHours(additionalTime);
    }
}
