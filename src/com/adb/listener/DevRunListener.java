package com.adb.listener;

import com.adb.bean.ItemBaen;

import java.util.ArrayList;

//链接监听
public interface DevRunListener {
    ArrayList<ItemBaen> getData();

    void onUpdateUi(ItemBaen itemBaen, boolean isRun, String msg);

}
