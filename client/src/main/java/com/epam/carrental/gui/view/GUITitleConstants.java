package com.epam.carrental.gui.view;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GUITitleConstants {


    private final Map<String,String> titles = new HashMap<>();

    public GUITitleConstants(){
        titles.put("Fleet","Fleet of cars");
    }
    public String getTitleFor(String tabName) {
        return titles.get(tabName);
    }
}
