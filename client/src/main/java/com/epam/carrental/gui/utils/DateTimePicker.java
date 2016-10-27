package com.epam.carrental.gui.utils;


import java.awt.*;
import java.time.ZonedDateTime;

public interface DateTimePicker {

    ZonedDateTime getDateTime();
    Component getComponent();
}
