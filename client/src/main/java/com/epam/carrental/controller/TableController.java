package com.epam.carrental.controller;

public interface TableController<DTO> {


    void save(DTO dto);

    void handleUserInput();

    void refreshTableView();
}
