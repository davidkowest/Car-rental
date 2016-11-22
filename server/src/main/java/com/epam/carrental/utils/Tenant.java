package com.epam.carrental.utils;

import org.springframework.stereotype.Component;

@Component
public class Tenant {
    public final ThreadLocal<String> id = new ThreadLocal<>();
}