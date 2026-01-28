package com.adb.manager.command;

import com.cmd.CmdBase;

import java.io.File;

/**
 * Created by guom on 2019/12/19.
 */
public class Adb extends CmdBase {
    @org.junit.Test
    public void test() {
        getDevices(new OnCmdBack() {
            @Override
            public void onCmdState(int state, String res) {

            }

            @Override
            public void onCmdMsg(int state, String res) {

            }
        });
    }

    @org.junit.Test
    public void test1() {
        //onDevicesRun("95RKW8SK99999999");
    }

    @org.junit.Test
    public void test2() {
        onShowApp("95RKW8SK99999999", null);
    }

    //true 计算机已经配置好了adb
    private static boolean isInstallAdb = true;

    private static String getAdbPath() {
        if (isInstallAdb) {
            return "";
        }
        String path = System.getProperty("user.dir");
        path += "/adb/";
        System.out.println(path);
        return path;
    }

    private static Adb adb = new Adb();

    public static Adb getInstance() {
        return adb;
    }

    //获取设备
    public void getDevices(OnCmdBack onCmdBack) {
        //设备
        String cmd = getAdbPath() + "adb devices";
        onRunCmd(cmd, onCmdBack);
    }

    //0 运行正常 1 设备不存在
    //滑动屏幕
    public void onDevicesRun(String devName, OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb -s " + devName + " shell";
        String cmd2 = getAdbPath() + "input swipe 250 250 250 -900";
        String cmd3 = cmd + " " + cmd2;
        onRunCmd(cmd3, onCmdBack);
    }

    /**
     * 模拟点击事件
     * adb -s 31141JEHN14666 shell input tap 200(x) 300(y)
     *
     * @param devName
     * @param onCmdBack
     */
    public void onDevicesRunClick(String devName, int x, int y, OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb -s " + devName + " shell";
        String cmd2 = getAdbPath() + "shell input tap " + x + " " + y;
        String cmd3 = cmd + " " + cmd2;
        onRunCmd(cmd3, onCmdBack);
    }

    /**
     * 模拟按返回键
     * adb -s 31141JEHN14666 shell input keyevent KEYCODE_BACK
     *
     * @param devName
     * @param onCmdBack
     */
    public void onDevicesRunBack(String devName, OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb -s " + devName + " shell";
        String cmd2 = getAdbPath() + "shell input keyevent KEYCODE_BACK";
        String cmd3 = cmd + " " + cmd2;
        onRunCmd(cmd3, onCmdBack);
    }

    /**
     * 获取app最上层activity
     * adb -s 31141JEHN14666 shell dumpsys window | findstr mCurrentFocus
     *
     * @param devName
     * @param onCmdBack
     */
    public void onDevicesRunTopAct(String devName, OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb -s " + devName + " shell";
        String cmd2 = getAdbPath() + "shell  dumpsys window | findstr mCurrentFocus";
        String cmd3 = cmd + " " + cmd2;
        onRunCmd(cmd3, onCmdBack);
    }

    /**
     * 获取日志
     * adb shell  logcat -v time > C:\Users\Administrator\Desktop\logcat.txt
     *
     * @param devName
     * @param onCmdBack
     */
    public void onDevicesRunLog(String devName, OnCmdBack onCmdBack, File file) {
        String cmd = getAdbPath() + "adb -s " + devName + " shell";
        String cmd2 = getAdbPath() + "shell  logcat -v time" + " " + file.getPath();
        String cmd3 = cmd + " " + cmd2;
        onRunCmd(cmd3, onCmdBack);
    }


    private int statePort;

    //设置端口号
    public int onDevSetPort(String devName, String devPort) {
        //0 完成设置
        statePort = 1;
        String cmd = getAdbPath() + "adb -s " + devName + " tcpip " + devPort;
        onRunCmd(cmd, new OnCmdBack() {
            @Override
            public void onCmdState(int state, String res) {

            }

            @Override
            public void onCmdMsg(int state, String res) {
                //端口号设置成功
                if (res != null && res.contains("restarting")) {
                    statePort = 0;
                    return;
                }

                if (statePort == 0) {
                    return;
                }
                //没有找到这台设备
                if (res == null || res.contains("")) {
                    statePort = 1;
                }
            }
        });
        return statePort;
    }


    //使用IP、端口号  方式链接
    public void onDevConnectWifi(String ip, String devPort, OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb connect " + ip + ":" + devPort;
        onRunCmd(cmd, onCmdBack);

    }
    //adb shell am force-stop xxxxxx
    //adb  shell am start -n xxxxxx/xxxx
    //adb shell pm list package
    //获取当前应用

    public void onShowApp(String devName, OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb -s " + devName + " shell";
        String cmd2 = getAdbPath() + "pm list package";
        String cmd3 = cmd + " " + cmd2;
        onRunCmd(cmd3, onCmdBack);
    }

    public void onAdbStop(OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb kill-server";
        onRunCmd(cmd, onCmdBack);

    }

    public void onAdbStart(OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb start-server";
        onRunCmd(cmd, onCmdBack);

    }

    public void onAdbMsg(OnCmdBack onCmdBack) {
        String cmd = getAdbPath() + "adb version";
        onRunCmd(cmd, onCmdBack);

    }
}
