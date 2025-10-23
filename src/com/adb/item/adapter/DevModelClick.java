package com.adb.item.adapter;

import com.adb.listener.DevOptionsListener;
import com.adb.bean.ItemBaen;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DevModelClick implements MouseListener {
    private JList devices;
    private DevOptionsListener devOptionsListener;

    public DevModelClick(JList devices, DevOptionsListener devOptionsListener) {
        this.devices = devices;
        this.devOptionsListener = devOptionsListener;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int index = devices.getSelectedIndex();
        Object obj = devices.getSelectedValue();
        ItemBaen itemBaen = (ItemBaen) obj;
        System.out.print("你选中了：\nindex=" + index + " \nname=" + itemBaen.name);
        devices.clearSelection();
        itemBaen.isSelect = !itemBaen.isSelect;
        devices.updateUI();
        devOptionsListener.onSelect(itemBaen);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
