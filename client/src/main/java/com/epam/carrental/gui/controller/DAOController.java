package com.epam.carrental.gui.controller;

import javax.swing.table.DefaultTableModel;

public interface DAOController {

    DefaultTableModel getModel() ;

    void addCarToDB(String ...dtoFields);
}
