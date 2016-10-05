package com.epam.carrental;

import com.epam.carrental.config.ClientConfig;
import com.epam.carrental.gui.MainWindow;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientMain {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ClientConfig.class);
        MainWindow mainWindow = (MainWindow)ctx.getBean(MainWindow.class);
        mainWindow.initGUI();
    }

}
