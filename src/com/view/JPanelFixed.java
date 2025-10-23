package com.view;

import javax.swing.*;
import java.awt.*;

public class JPanelFixed extends JPanel {
    //固定宽度
    private int fixedWidth;

    public JPanelFixed(int fixedWidth, LayoutManager var1) {
        super(var1, true);
        this.fixedWidth = fixedWidth;
    }

    //设置固定宽度
    public JPanelFixed(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(fixedWidth, 0); // 高度设置为0表示高度会被布局管理器决定
    }
}
