package com.epam.carrental.gui.listeners;

import com.epam.carrental.controller.RentalClassController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

@Component
@Slf4j
public class RentalClassComboBoxListener implements PopupMenuListener {

    @Autowired
    private RentalClassController rentalClassController;

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        rentalClassController.refreshData();
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        log.debug("popupMenuWillBecomeInvisible is not needed");
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        log.debug("popupMenuWillBecomeInvisible is not needed");
    }
}
