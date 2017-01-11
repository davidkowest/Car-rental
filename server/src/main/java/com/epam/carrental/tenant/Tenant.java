package com.epam.carrental.tenant;

import org.springframework.stereotype.Component;

@Component
public class Tenant {
    public final ThreadLocal<String> id = new ThreadLocal<>();
}