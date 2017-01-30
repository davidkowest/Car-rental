package com.epam.carrental.jms;

import com.epam.carrental.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public class DataChangedMessage implements Serializable {
    @Getter
    private CustomerDTO customerDTO;
}