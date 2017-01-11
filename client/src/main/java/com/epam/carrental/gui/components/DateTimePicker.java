package com.epam.carrental.gui.components;


import java.awt.*;
import java.time.ZonedDateTime;

public interface DateTimePicker {

    ZonedDateTime getDateTime();
    Component getComponent();
}
