package com.epam.carrental.gui.view.builders.impl;

import com.epam.carrental.gui.view.hanlders.impl.CarUserInputHandler;
import com.epam.carrental.models.CarTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FleetViewBuilder extends TableTabViewBuilder {

    @Autowired
    public FleetViewBuilder(CarTableModel tableModel, CarUserInputHandler carUserInputHandler) {
        super(tableModel, carUserInputHandler);
    }


}
