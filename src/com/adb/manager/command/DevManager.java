package com.adb.manager.command;

import com.adb.listener.DevAdbListener;
import com.adb.listener.DevLinkListener;
import com.adb.listener.DevReadListener;
import com.adb.listener.DevRunListener;
import com.adb.manager.execute.*;

/**
 * 响应ui的 操作ADB命令，处理返回结果
 */
public class DevManager {
    private static DevManager devManager;

    public static DevManager getInstance() {
        if (devManager == null) {
            devManager = new DevManager();
        }
        return devManager;
    }

    //无线连接一个设备
    public void devLink(DevLinkListener devLinkListener, String ip, String port) {
        new DevLink(devLinkListener, ip, port).execute();
    }

    //获取已链接的设备
    public void devRead(DevReadListener devReadListener) {
        new DevRead(devReadListener).execute();
    }


    private DevRun devRun;

    //开始运行
    public void devRun(DevRunListener devRunListener) {
        if (devRun == null) {
            devRun = new DevRun(devRunListener);
            devRun.execute();
        }
    }

    //停止运行
    public void devRunStop() {
        if (devRun == null) {
            return;
        }
        devRun.setStop();
        devRun = null;
    }

    public void adbStop(DevAdbListener listener) {
        new AdbStop(listener).execute();
    }
    public void adbStart(DevAdbListener listener) {
        new AdbStart(listener).execute();
    }
    public void adbMsg(DevAdbListener listener) {
        new AdbMsg(listener).execute();
    }


}
