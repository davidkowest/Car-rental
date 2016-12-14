package com.epam.carrental.gui.view.hanlders.impl;


import com.epam.carrental.controller.BookCarController;
import com.epam.carrental.dto.BookedCarDTO;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import com.epam.carrental.models.table.AbstractSwingTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.ZonedDateTime;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.SpringLayout.NORTH;

@Component
public class BookUserInputHandler implements UserInputHandler {

    @Autowired
    AbstractSwingTableModel<CustomerDTO> customerTableModel;
    @Autowired
    private BookCarController bookCarController;
    @Autowired
    private MessageView messageView;

    public void handleInputUsing(CarDTO carDTO, ZonedDateTime startDate, ZonedDateTime endDate) {
        JTable customersTable = new JTable(customerTableModel);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel(customerTableModel.getTableName()), NORTH);
        inputPanel.add(new JScrollPane(customersTable), CENTER);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                "Book  [" + carDTO.getModel() + " & " + carDTO.getRegistrationNumber() + "]", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int selectedRow=customersTable.getSelectedRow();
            if (selectedRow < 0) {
                messageView.showErrorMessage("No rows selected!");
            }else {
                bookCarController.bookCar(new BookedCarDTO(carDTO, customerTableModel.getModel(selectedRow), startDate, endDate));
            }
        }
    }

}

