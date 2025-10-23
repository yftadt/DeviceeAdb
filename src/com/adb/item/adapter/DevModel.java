package com.adb.item.adapter;

import com.adb.bean.ItemBaen;

import javax.swing.*;
import java.util.List;

public class DevModel extends AbstractListModel {
    private List<ItemBaen> people;


    public void setData(List<ItemBaen> people) {
        this.people = people;
    }

    @Override
    public int getSize() {
        return people.size();
    }

    @Override
    public ItemBaen getElementAt(int index) {
        return people.get(index);
    }
}
