package com.view;

import javax.swing.*;
import java.awt.*;

public class JPanelMin extends JPanel {
    private int minWidth;
    public JPanelMin(int minWidth, LayoutManager var1) {
        super(var1, true);
        this.minWidth = minWidth;
    }
    //设置最小宽度
    public JPanelMin(int minWidth) {
        this.minWidth = minWidth;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(minWidth, 0);
    }


}
