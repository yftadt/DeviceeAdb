package com.adb.bean;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by guom on 2019/12/19.
 */
public class ItemBaen extends Vector {
    public String name;
    public boolean isSelect;
    //true 已设置端口号
    public boolean isSetPort;
    //运行类型
    //1 上下动，2 点击，3 返回，4 获取最上面的acy名称
    public ArrayList<Integer> runTypes = new ArrayList<>();
    //记录运行位置
    public int runType = -1;

    public int x, y;
}
