package com.epam.carrental.gui.view;


import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MessageView {

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Message",JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message, "ERROR",JOptionPane.ERROR_MESSAGE);
    }


}
