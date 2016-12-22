package com.epam.carrental.data_generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class ZonedDateTimeGenerator {

    @Value("${rented.time.min.hours}")
    Long rentalTimeMin;
    @Value("${rented.time.max.hours}")
    Long rentalTimeMax;
    @Value("${gap.time.min.hours}")
    Long gapTimeMin;
    @Value("${gap.time.max.hours}")
    Long gapTimeMax;

    public ZonedDateTime getStartTimeAfter(ZonedDateTime beginTime) {
        long additionalTime = gapTimeMin + (long) (Math.random() * (gapTimeMax - gapTimeMin));
        return beginTime.plusHours(additionalTime);
    }

    public ZonedDateTime getEndTimeAfter(ZonedDateTime generatedBeginTime) {
        long additionalTime = rentalTimeMin + (long) (Math.random() * (rentalTimeMax - rentalTimeMin));
        return generatedBeginTime.plusHours(additionalTime);
    }
}
