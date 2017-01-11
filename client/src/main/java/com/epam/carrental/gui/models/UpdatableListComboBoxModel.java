package com.epam.carrental.gui.models;


import org.jdesktop.swingx.combobox.ListComboBoxModel;

import java.util.List;


public class UpdatableListComboBoxModel<E> extends ListComboBoxModel<E> {

    public UpdatableListComboBoxModel(List<E> list) {
        super(list);
    }

    public void setData(List<E> list){
        super.data.clear();
        super.data.addAll(list);
        if(!list.isEmpty()) {
            this.selected = list.get(0);
        }
    }

    public void refreshModel(){
        this.fireContentsChanged(this, 0, this.getSize() - 1);
    }
    public void setDataAndRefresh(List<E> list){
        setData(list);
        refreshModel();
    }
}