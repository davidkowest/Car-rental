package com.epam.carrental.gui.view.views.impl;

import com.epam.carrental.controller.ServerInfoController;
import com.epam.carrental.gui.view.views.TabView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestConnectionView extends TabView {

    @Autowired
    public TestConnectionView(ServerInfoController serverInfoController){
        addButtonToPanel("Test connection", serverInfoController::getMessageForTestConnectionButton);
    }

}
