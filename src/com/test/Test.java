package com.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Created by guom on 2019/12/19.
 */
public class Test extends JFrame {
    private void t0() {
        //得到窗口的容器
        Container conn = getContentPane();
        //创建一个标签 并设置初始内容
        JLabel L1 = new JLabel("123");

        conn.add(L1);
        //设置窗口的属性 窗口位置以及窗口的大小
        //setBounds(200, 200, 300, 200);
    }

    private void t1() {
        setLayout(new BorderLayout());
        add(new JButton("North"), BorderLayout.NORTH);
        add(new JButton("South"), BorderLayout.SOUTH);
        add(new JButton("WEST"), BorderLayout.WEST);
        add(new JButton("EAST"), BorderLayout.EAST);
        add(new JButton("CENTER"));

    }

    private void t2() {
        // 创建内容面板，指定使用 流式布局
        JPanel panel = new JPanel(new FlowLayout());
        //
        JButton btn01 = new JButton("按钮01");
        JButton btn02 = new JButton("按钮02");
        JButton btn03 = new JButton("按钮03");
        JButton btn04 = new JButton("按钮04");
        JButton btn05 = new JButton("按钮05");
        //
        panel.add(btn01);
        panel.add(btn02);
        panel.add(btn03);
        panel.add(btn04);
        panel.add(btn05);
        add(panel, BorderLayout.NORTH);
        // setContentPane(panel);
        //
        JPanel panel2 = new JPanel(new FlowLayout());
        JButton btn06 = new JButton("按钮05");
        panel2.add(btn06);
        // setContentPane(panel2);
        add(panel2, BorderLayout.SOUTH);
    }


    private void test3() {
        Container con = this.getContentPane();
        con.setLayout(new BorderLayout());
        //定义字符串
        String[] a = {"身份证", "军人证", "残疾证", "护照", "身份证", "军人证", "残疾证", "护照"};
        //实例化列表框
        JList jlist = new JList(a);

         //实例化JScrollPane面板
        JScrollPane layoutSP = new JScrollPane(jlist);
        //设置JScrollPane大小
        layoutSP.setPreferredSize(new Dimension(100, 200));
        con.add(BorderLayout.EAST, layoutSP);
        //---------------------------------
        //实例化下拉列表
        JComboBox jcombo = new JComboBox(a);
        //实例化JPanel面板
        JPanel layputJP = new JPanel();
        layputJP.add(jcombo);
        con.add(BorderLayout.WEST, layputJP);

    }
}
