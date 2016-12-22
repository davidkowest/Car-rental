package com.epam.carrental.gui.utils;

import org.jdesktop.swingx.JXDatePicker;
import org.springframework.context.annotation.Scope;

import java.awt.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@org.springframework.stereotype.Component
@Scope("prototype")
public class DateAdapter implements DateTimePicker {

    private JXDatePicker datePickerJX = new JXDatePicker();

    @Override
    public ZonedDateTime getDateTime() {
        return ZonedDateTime.ofInstant(datePickerJX.getDate().toInstant(),
                ZoneId.systemDefault());

    }

    @Override
    public Component getComponent() {
        return datePickerJX;
    }
}