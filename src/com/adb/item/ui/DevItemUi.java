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
        ItemBaen itemBaen = (ItemBaen) value;
        //
        JPanel jPanel = new JPanel();
        //设置宽度
        jPanel.setPreferredSize(new Dimension(300, 50));
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //
        JCheckBox box = new JCheckBox();
        box.setSelected(itemBaen.isSelect);
        jPanel.add(box, FlowLayout.LEFT);
        //
        JTextField text = new JTextField();
        text.setOpaque(false);//背景透明
        text.setBorder(new EmptyBorder(0, 0, 0, 0)); //去边框
        jPanel.add(text);
        //
        text.setText(itemBaen.name);
        return jPanel;
    }


}
