package com.epam.carrental.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MathUtils {

    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
