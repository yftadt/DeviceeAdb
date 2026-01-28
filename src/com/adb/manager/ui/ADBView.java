package com.adb.manager.ui;

import com.adb.listener.DevAdbListener;
import com.adb.manager.command.Adb;
import com.adb.manager.command.DevManager;
import com.view.JPanelFixed;
import com.view.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//左侧ADB UI
public class ADBView {
    private void setTopUi(JPanel panel) {
        JButton dbBtn = new JButton("获取ADB信息");
        dbBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("runBtn");
                msgADBJl.setText(adbStr + "开始获取");
                DevManager.getInstance().adbMsg(new DevAdbListener() {

                    @Override
                    public void onMsgBack(String code, String msg, Object obj) {
                        msgADBJl.setText(adbStr + code + " " + msg);

                    }
                });
            }
        });
        panel.add(BorderLayout.NORTH, dbBtn);
    }

    private JTextArea msgADBJl;
    private String adbStr = "ADB信息：";

    private void setMiddleUi(JPanel panel) {
        msgADBJl = Views.getInstance().getJTextArea();
        //背景透明
        msgADBJl.setOpaque(false);
        //去边框
        msgADBJl.setBorder(new EmptyBorder(0, 0, 0, 0));
        JScrollPane temp = Views.getInstance().getJScrollPane(msgADBJl);
        //
        msgADBJl.setText(adbStr);
        panel.add(temp);
    }

    private void setBotUi(JPanel panel) {
        //
        JPanel botView = new JPanel(new GridLayout(4, 1));//3行 1列
        //标签
        JLabel adbPathJl = new JLabel();
        adbPathJl.setSize(10, 1000);//无效
        adbPathJl.setText("输入新的ADB路径：");
        //包裹标签
        JPanel adbPathJP = new JPanel();
        adbPathJP.add(adbPathJl);
        adbPathJP.setLayout(new FlowLayout(FlowLayout.LEFT));
        botView.add(adbPathJP, new FlowLayout(FlowLayout.LEFT));
        //ADB 输入框
        JTextField adbET = new JTextField();
        adbET.setSize(200, 25);
        adbET.setColumns(10);
        botView.add(adbET, new FlowLayout(FlowLayout.LEFT));
        //设置新的ADB
        JButton adbBtn = new JButton("设置新的ADB");
        adbBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                String adbPath = adbET.getText().toString();
                System.out.println("新的ADB->" + adbPath);
                String res = Adb.setAdbPath(adbPath);
                msgADBJl.setText(res);
            }
        });
        botView.add(adbBtn, new FlowLayout(FlowLayout.LEFT));
        //设置新的ADB
        JButton adbRestBtn = new JButton("重置ADB");
        adbRestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                Adb.setAdbPathRest();
                msgADBJl.setText("重置ADB完成");
            }
        });
        botView.add(adbRestBtn, new FlowLayout(FlowLayout.LEFT));
        botView.setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 2));
        panel.add(BorderLayout.SOUTH, botView);
    }

    //获取ADB信息ui
    public JPanel getADBUi() {
        JPanel panel = new JPanelFixed(180, new BorderLayout());
        setTopUi(panel);
        setMiddleUi(panel);
        setBotUi(panel);
        return panel;
    }
}
