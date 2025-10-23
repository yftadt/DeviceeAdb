package com.adb.item.ui;

import com.adb.bean.ItemBaen;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 * Created by guom on 2020/2/28.
 */
public class CellRenderer implements ListCellRenderer {
    private boolean isSearch;

    public CellRenderer(boolean isSearch) {
        this.isSearch = isSearch;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        JPanel jPanel = new JPanel();
        ItemBaen itemBaen = (ItemBaen) value;
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        if (isSearch) {
            JCheckBox box = new JCheckBox();
            jPanel.add(box);
            box.setSelected(itemBaen.isSelect);
        }
        //
        JTextField text = new JTextField();
        //背景透明
        text.setOpaque(false);
        //去边框
        text.setBorder(new EmptyBorder(0, 0, 0, 0));
        jPanel.add(text);
        //
        text.setText(itemBaen.name);

        return jPanel;
    }
}
