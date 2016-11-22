package com.epam.carrental.gui.utils;


import com.epam.carrental.dto.RentalClassDTO;

import javax.swing.*;
import java.awt.*;

public class RentalClassRenderer extends JLabel implements ListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setOpaque(true);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        if(value!=null) {
            String text = ((RentalClassDTO) value).getName();
            setText(text);
        }
        return this;
    }
}
