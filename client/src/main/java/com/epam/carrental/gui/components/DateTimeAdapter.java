package com.epam.carrental.gui.components;


import org.springframework.context.annotation.Scope;

import java.awt.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@org.springframework.stereotype.Component
@Scope("prototype")
public class DateTimeAdapter implements DateTimePicker {

    private DateTimePickerJX dateTimePickerJX=new DateTimePickerJX();

    @Override
    public ZonedDateTime getDateTime() {
        return ZonedDateTime.ofInstant(dateTimePickerJX.getDate().toInstant(),
                ZoneId.systemDefault());

    }
    @Override
    public Component getComponent() {
        return dateTimePickerJX;
    }
}
