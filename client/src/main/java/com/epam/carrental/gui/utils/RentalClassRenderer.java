package com.epam.carrental.gui.utils;


import com.epam.carrental.dto.RentalClassDTO;

import javax.swing.*;
import java.awt.*;

public class RentalClassRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String text = null;
        if(value!=null) {
            text = ((RentalClassDTO) value).getName();
        }
        return super.getListCellRendererComponent(list,text,index,isSelected,cellHasFocus);
    }
}
