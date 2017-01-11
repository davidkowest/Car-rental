package com.epam.carrental.rentals.available;

import com.epam.carrental.dto.RentalClassDTO;
import com.epam.carrental.rental_classes.RentalClassComboBoxListener;
import com.epam.carrental.gui.components.DateTimeAdapter;
import com.epam.carrental.rental_classes.RentalClassRenderer;
import com.epam.carrental.gui.view.TableTabView;
import com.epam.carrental.gui.models.AbstractSwingTableModel;
import com.epam.carrental.gui.models.UpdatableListComboBoxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;


@Component
public class RentCarView extends TableTabView {

    @Autowired
    RentalClassComboBoxListener rentalClassComboBoxListener;
    @Autowired
    DateTimeAdapter dateUntilPicker;
    @Autowired
    private RentCarController rentCarController;

    @Autowired
    private UpdatableListComboBoxModel<RentalClassDTO> updatableListComboBoxModel;

    public RentCarView(AbstractSwingTableModel availableToRentCarTableModel) {
        super(availableToRentCarTableModel);
    }

    @Override
    public JPanel initView() {
        super.initView();
        JComboBox rentalClassComboBox = prepareComboBox(updatableListComboBoxModel, new RentalClassRenderer(), rentalClassComboBoxListener);

        addButtonToToolBar("Refresh table", () -> rentCarController.refreshTableView((RentalClassDTO) rentalClassComboBox.getSelectedItem(), dateUntilPicker.getDateTime()));
        addButtonToToolBar("Rent a car", () -> rentCarController.handleUserInput(getSelectedRow()));
        addComponentToToolBar(rentalClassComboBox);
        addPickerToToolBar("Available until", dateUntilPicker.getComponent());
        return jPanel;
    }

}

