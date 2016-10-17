package com.epam.carrental.gui.view.hanlders.impl;


import com.epam.carrental.controller.RentCarController;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import com.epam.carrental.models.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;

@Component
public class RentalUserInputHandler implements UserInputHandler {

    @Autowired
    AbstractSwingTableModel<CustomerDTO> customerTableModel;
    @Autowired
    private RentCarController rentCarController;

    public void handleInputUsing(CarDTO carDTO) {

        JTable customersTable = new JTable(customerTableModel);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel(customerTableModel.getTableName()), NORTH);
        inputPanel.add(new JScrollPane(customersTable), CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Rent  [" + carDTO.getModel() +" & "+ carDTO.getRegistrationNumber()+ "]", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            rentCarController.save(new RentedCarDTO(carDTO, customerTableModel.getModel(customersTable.getSelectedRow())));
        }
    }

}
