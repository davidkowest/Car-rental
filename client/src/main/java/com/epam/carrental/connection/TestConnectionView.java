package com.epam.carrental.connection;

import com.epam.carrental.gui.view.TabView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestConnectionView extends TabView {

    @Autowired
    public TestConnectionView(ServerInfoController serverInfoController){
        addButtonToPanel("Test connection", serverInfoController::getMessageForTestConnectionButton);
    }

}
