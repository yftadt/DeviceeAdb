package com.aes.manger;

import com.view.Views;
import com.aes.bean.AESData;
import com.utile.AESManager;
import com.utile.FileUtile;
import com.view.JPanelFixed;
import com.view.JPanelMin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 界面UI
 */
public class AESWindow {
    private JFrame rootView;

    public void initView4() {

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout()); // 确保使用 BorderLayout
        JLabel label = new JLabel("在北部显示的标签");
        frame.add(label, BorderLayout.NORTH); // 添加组件到北部
        label = new JLabel("在南部显示的标签");
        frame.add(getBtnUi(), BorderLayout.SOUTH);
        frame.setSize(300, 200); // 设置框架大小
        frame.setVisible(true); // 设置框架可视
    }

    private AESData aesData;
    private String saveKey = "aes_file";

    public void initView() {
        //读取数据
        Object bean = FileUtile.getObj(saveKey);
        if (bean != null) {
            aesData = (AESData) bean;
        } else {
            aesData = new AESData();
            aesData.keyStr = "d45226d7abcd4d3ba1f164e094681b2b";
            aesData.offsetStr = "7bfff9944eafa5e5";
        }
        //
        rootView = new JFrame("解密");
        rootView.setSize(1000, 600);
        rootView.setLayout(new BorderLayout());
        //
        //底部
        rootView.add(getBtnUi(), BorderLayout.SOUTH);
        //中间布局
        rootView.add(getEncryptUi(), BorderLayout.CENTER);
        //右侧布局
        rootView.add(getDecryptUi(), BorderLayout.EAST);

        //
        rootView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //
        rootView.setVisible(true);
        //

    }

    JTextArea ipET;

    //中间
    //添加选中的ui
    public JPanel getEncryptUi() {
        // 创建内容面包容器，指定使用 边界布局
        JPanel view = new JPanelMin(800, new BorderLayout());
        //JPanel view = new JPanel();
        //view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
        ipET = Views.getInstance().getJTextArea();
        ipET.setSize(800, 0);
        ipET.setColumns(0);
        JScrollPane temp = Views.getInstance().getJScrollPane(ipET);
        view.add(temp);

        return view;
    }

    JTextArea hintJl;

    //右侧ui
    private JPanel getDecryptUi() {
        JPanel view = new JPanelFixed(350, new BorderLayout());
        // JPanel view = new JPanel();
        //view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
        hintJl = Views.getInstance().getJTextArea();
        //背景透明
        hintJl.setOpaque(false);
        //去边框
        hintJl.setBorder(new EmptyBorder(0, 0, 0, 0));
        JScrollPane temp = Views.getInstance().getJScrollPane(hintJl);
        view.add(temp);
        return view;
    }


    private JPanel getBtnUi() {
        if (aesData == null) {
            aesData = new AESData();
        }

        JPanel view = new JPanel();
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
        //

        JTextField encryptEt = new JTextField();
        encryptEt.setPreferredSize(new Dimension(300, 45));
        encryptEt.setText(aesData.keyStr);
        JPanel temp1 = Views.getInstance().getJPanelFlowLEFT();
        temp1.add(Views.getInstance().getJLabel("秘钥："));
        temp1.add(encryptEt);
        view.add(temp1);
        //
        JTextField offsetEt = new JTextField();
        offsetEt.setPreferredSize(new Dimension(300, 45));
        offsetEt.setText(aesData.offsetStr);
        JPanel temp2 = Views.getInstance().getJPanelFlowLEFT();
        temp2.add(Views.getInstance().getJLabel("偏移："));
        temp2.add(offsetEt);
        view.add(temp2);
        //
        JButton portBtn = new JButton("解密");
        // 设置内边距（上、左、下、右的间距）
        Insets insets = new Insets(10, 80, 10, 80); // 上、左、下、右的内边距
        portBtn.setMargin(insets);
        portBtn.setPreferredSize(new Dimension(200, 45));
        JPanel temp3 = Views.getInstance().getJPanelFlowCENTER();
        temp3.add(portBtn);
        view.add(temp3);
        //
        portBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                String context = ipET.getText();
                context = toContext(context);
                String str = AESManager.getInstance().decryptCFB(context, encryptEt.getText(), offsetEt.getText());
                if (str.isEmpty()) {
                    hintJl.setText("解密失败");
                } else {
                    hintJl.setText(str);
                    aesData.keyStr = encryptEt.getText();
                    aesData.offsetStr = offsetEt.getText();
                    boolean isSave = FileUtile.saveObj(aesData, saveKey);
                    System.out.println("保存数据成功？" + isSave);
                }

            }
        });
        //
        return view;
    }

    public static String toContext(String context) {
        if (context.isEmpty()) {
            return context;
        }
        int index = context.indexOf("\"");
        int length = context.length();
        System.out.println("===>index=" + index + " length=" + length);
        while (index >= 0) {
            if (index < length / 2) {
                context = context.substring(index + 1);
            } else {
                context = context.substring(0, index);
            }
            index = context.indexOf("\"");
            length = context.length();
            System.out.println("===>index=" + index + " length=" + length);
        }
        return context;
    }


}
