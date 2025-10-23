package com.adb.manager.execute;

import com.adb.manager.command.Adb;
import com.cmd.CmdBase;
import com.adb.listener.DevLinkListener;

import javax.swing.*;

//无线连接
public class DevLink extends SwingWorker {
    private String ip, port;
    private DevLinkListener devLinkListener;

    public DevLink(DevLinkListener devLinkListener, String ip, String port) {
        this.ip = ip;
        this.port = port;
        this.devLinkListener = devLinkListener;
    }

    //true 已链接
    private boolean isConnected = false;

    @Override
    protected Integer doInBackground() {
        Adb.getInstance().onDevConnectWifi(ip, port, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdResult(int state, String res) {
                System.out.println("onCmdResult 链接情况：" + state + " " + res);
                if (res != null && res.contains("connected")) {
                    isConnected = true;
                }
                //早已连接
                if (res != null && res.contains("already")) {
                    isConnected = true;
                }
            }
        });
        return 0;
    }

    //完成
    @Override
    protected void done() {
        super.done();
        //Integer state = (Integer) get();

        devLinkListener.onDevLinkState(isConnected, ip, port);
    }
}
