package com.epam.carrental.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSwingTableModel<DTO> extends AbstractTableModel {

    String tableName;
    List<String> columnNames;
    List<DTO> data = new ArrayList<>();

    public AbstractSwingTableModel(String tableName, List<String> columnNames) {
        this.tableName = tableName;
        this.columnNames = columnNames;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames.get(columnIndex);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    public void setData(List<DTO> values) {
        this.data = values;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DTO getModel(int rowIndex) {
        return data.get(rowIndex);
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }
}
