package com.epam.carrental.gui.view.hanlders.impl;


import com.epam.carrental.controller.RentCarController;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.dto.RentedCarDTO;
import com.epam.carrental.gui.utils.DateTimeAdapter;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;
import static javax.swing.SpringLayout.SOUTH;

@Component
public class RentalUserInputHandler implements UserInputHandler {

    @Autowired
    AbstractSwingTableModel<CustomerDTO> customerTableModel;
    @Autowired
    private RentCarController rentCarController;

    @Autowired
    DateTimeAdapter plannedReturnDateTimePicker;

    @Autowired
    private MessageView messageView;

    public void handleInputUsing(CarDTO carDTO) {

        JTable customersTable = new JTable(customerTableModel);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel(customerTableModel.getTableName()), NORTH);
        inputPanel.add(new JScrollPane(customersTable), CENTER);
        inputPanel.add(prepareTimePicker(plannedReturnDateTimePicker.getComponent(),"Planned return date:"),SOUTH);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Rent  [" + carDTO.getModel() +" & "+ carDTO.getRegistrationNumber()+ "]", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int selectedRow=customersTable.getSelectedRow();
            if (selectedRow < 0) {
                messageView.showErrorMessage("No rows selected!");
            }else {
                rentCarController.save(new RentedCarDTO(carDTO, customerTableModel.getModel(customersTable.getSelectedRow())
                        , plannedReturnDateTimePicker.getDateTime()));
            }
        }
    }

}
