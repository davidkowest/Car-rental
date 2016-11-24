package com.epam.carrental.gui.listeners;

import com.epam.carrental.controller.RentalClassController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

@Component
public class RentalClassComboBoxListener implements PopupMenuListener {

    @Autowired
    private RentalClassController rentalClassController;

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        rentalClassController.refreshData();
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
