package com.adb.item.ui;

import com.adb.bean.ItemBaen;

import java.awt.*;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 * Created by guom on 2020/2/28.
 */
public class DevItemUi implements ListCellRenderer {


    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        //
        JPanel jPanel = new JPanel();
        //设置宽度
        jPanel.setPreferredSize(new Dimension(300, 40));

        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //
        ItemBaen itemBaen = (ItemBaen) value;
        //
        JCheckBox box = new JCheckBox();
        box.setSelected(itemBaen.isSelect);
        jPanel.add(box);
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
