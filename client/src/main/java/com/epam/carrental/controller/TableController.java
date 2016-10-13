package com.epam.carrental.controller;

public interface TableController<DTO> {

    void refreshTableModel();

    void save(DTO dto);

}
