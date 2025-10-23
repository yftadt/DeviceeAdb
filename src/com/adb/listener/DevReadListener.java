package com.adb.listener;

import com.adb.bean.ItemBaen;

import java.util.ArrayList;

//链接监听
public interface DevReadListener extends DevAdbListener{
    void onDevLinkState(boolean isConnected, ArrayList<ItemBaen> datas);
}
