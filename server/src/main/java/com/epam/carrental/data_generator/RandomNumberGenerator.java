package com.epam.carrental.data_generator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNumberGenerator {

    public int generateWithin(int start, int end) {
        Random r = new Random();
        return r.nextInt(end - start) + start;
    }
}