package com.adb.listener;

//链接监听
public interface DevLinkListener {
    void onDevLinkState(boolean isConnected, String ip, String port);
}
