package com.epam.carrental.utils;

public class Tenant {
    private static final ThreadLocal<String> id = new ThreadLocal<String>();

    public static String getId() {
        return id.get();
    }

    public static void setId(String value) {
        id.set(value);
    }
}