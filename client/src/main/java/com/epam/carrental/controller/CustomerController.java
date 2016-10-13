package com.epam.carrental.controller;

import com.epam.carrental.dto.CustomerDTO;
import com.epam.carrental.gui.utils.BackgroundWorker;
import com.epam.carrental.gui.view.MessageView;
import com.epam.carrental.models.CustomerTableModel;
import com.epam.carrental.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class CustomerController implements TableController<CustomerDTO> {

    @Autowired
    private BackgroundWorker inBackgroundWorker;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MessageView messageView;
    @Autowired
    private CustomerTableModel customerTableModel;

    @PostConstruct
    @Override
    public void refreshTableModel(){
        try {
            customerTableModel.setData(customerService.readAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(CustomerDTO customerDTO) {

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(customerDTO);

        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Empty fields or incorrect email address");
        }
        inBackgroundWorker.execute(
                () -> customerService.create(customerDTO),
                o -> customerTableModel.setData(customerService.readAll()),
                e -> messageView.showErrorMessage(e.getCause().getMessage()));
    }
}

