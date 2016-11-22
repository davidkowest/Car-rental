package com.epam.carrental.gui.view.hanlders.impl;

import com.epam.carrental.controller.FleetController;
import com.epam.carrental.dto.CarDTO;
import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.gui.utils.RentalClassRenderer;
import com.epam.carrental.gui.view.hanlders.UserInputHandler;
import com.epam.carrental.models.UpdatableListComboBoxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class CarUserInputHandler implements UserInputHandler {

    @Value("${car.model.label}")
    private String carModelLabel;

    @Value("${car.reg.number.label}")
    private String carRegNumberLabel;

    @Value("${car.class.name.label}")
    private String carClassLabel;

    @Value("${car.input.message}")
    private String message;

    @Autowired
    private FleetController fleetController;
    @Autowired
    private UpdatableListComboBoxModel<RentalClassDTO> updatableListComboBoxModel;

    public void handleInput() {
        JTextField carModelField = new JTextField();
        JTextField carNumberField = new JTextField();

        JComboBox<RentalClassDTO> rentalClassComboBox = new JComboBox(updatableListComboBoxModel);
        rentalClassComboBox.setRenderer(new RentalClassRenderer());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(prepareInputPanel(carModelField, carModelLabel), BorderLayout.NORTH);
        inputPanel.add(prepareInputPanel(carNumberField, carRegNumberLabel), BorderLayout.CENTER);
        inputPanel.add(prepareComboBoxPanel(rentalClassComboBox,carClassLabel), BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(null, inputPanel,
                message, JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            fleetController.save(new CarDTO(carModelField.getText(), carNumberField.getText(), (RentalClassDTO) rentalClassComboBox.getSelectedItem()));
        }
    }
}
