package com.epam.carrental.models;

import org.springframework.stereotype.Component;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractCarRentalTableModel<DTO> extends AbstractTableModel {

    String tableName;
    final List<String> columnNames = new ArrayList<>();
    List<List<String>> data;

    public abstract void setData(List<DTO> data);

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }
}