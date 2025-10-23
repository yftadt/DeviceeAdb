package com.view;

import javax.swing.*;
import java.awt.*;

public class Views {
    //标签字体大小
    private int fontJLabel = 16;
    //输入框字体大小
    private int fontInput = 16;
    //消息字体大小
    private int fontMsg = 16;    //设置标签
    public Component getJLabel(String hint) {
        JLabel jLabel = new JLabel(hint);
        setFont(jLabel, fontJLabel);
        //jLabel.setBackground(Color.RED);
        return jLabel;
    }
    //推荐使用布局来设置高宽
    public JPanel getJPanelFlowLEFT(){
        //
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //设置外边距 // 上、左、下、右的内边距
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0)); // 也可以使用这种方式设置外边距
        //
        //panel.setMaximumSize(new Dimension(0, 45));
        //panel.setBackground(Color.BLACK);
        return panel;
    }
    //推荐使用布局来设置高宽
    public JPanel getJPanelFlowCENTER(){
        //
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //设置外边距 // 上、左、下、右的内边距
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // 也可以使用这种方式设置外边距
        //
        //panel.setMaximumSize(new Dimension(0, 45));
        //panel.setBackground(Color.BLACK);
        return panel;
    }
    //设置输入框
    public JTextArea getJTextArea() {
        JTextArea textArea = new JTextArea(); // 创建一个可以显示2行的文本区域，每行最多20个字符
        textArea.setRows(3);
        textArea.setLineWrap(true); // 启用自动换行
        textArea.setWrapStyleWord(true); // 设置换行时保持单词的完整性
        Insets insets = new Insets(5, 10, 5, 10);
        // 应用内边距到JTextArea
        textArea.setMargin(insets);
        setFont(textArea, fontInput);
        textArea.setMinimumSize(new Dimension(380, 0));
        //textArea.setPreferredSize(new Dimension(275, 80));
        //textArea.setMaximumSize(new Dimension(275, 80));
        return textArea;
    }
    //设置输入框内的滑动 -竖滑
    public JScrollPane getJScrollPane(Component view) {
        JScrollPane jScrollPane = new JScrollPane(view);
        jScrollPane.setMinimumSize(new Dimension(380, 65));
        jScrollPane.setPreferredSize(new Dimension(380, 65));
        jScrollPane.setMaximumSize(new Dimension(380, 65));
        return jScrollPane;
    }
    private void setFont(Component textArea, int size) {
        // 创建字体对象并设置字体大小
        Font font = new Font("Serif", Font.PLAIN, size);
        // 应用字体到JTextArea
        textArea.setFont(font);
    }
    private static Views aesManager;

    public static Views getInstance() {
        if (aesManager == null) {
            aesManager = new Views();
        }
        return aesManager;
    }
}
