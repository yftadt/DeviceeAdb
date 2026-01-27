package com.adb.manager.execute;

import com.adb.manager.command.Adb;
import com.cmd.CmdBase;
import com.adb.bean.ItemBaen;
import com.adb.listener.DevReadListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//获取已链接的设备
public class DevRead extends SwingWorker {
    private DevReadListener listener;

    public DevRead(DevReadListener devReadListener) {
        this.listener = devReadListener;
    }

    @Override
    protected ArrayList<String> doInBackground() {
        ArrayList<String> devs = new ArrayList<>();
        Adb.getInstance().getDevices(new CmdBase.OnCmdBack() {
            @Override
            public void onCmdResult(int state, String res) {
                System.out.println("onCmdResult 获取链接的设备：state=" + state + " res=" + res);
                listener.onMsgBack(String.valueOf(state), res, null);
                if (res.contains("不是内部或外部命令")) {
                    return;
                }
                if (Adb.isAdbCode(state)) {
                    return;
                }
                if (res.contains("offline")) {
                    //离线状态
                    return;
                }
                //error: protocol fault (couldn't read status): connection reset
                if (res.contains("error")) {
                    //发生错误
                    return;
                }
                if (res.contains("kill")) {
                    //被杀
                    return;
                }
                //单一设备
                //List of devices attached 10.168.5.143:40975 device
                //List of devices attached RRCX1066YVD device
                res = res.replace("List of devices attached", "");
                String[] devices = res.split("device");
                if (devices == null) {
                    return;
                }
                for (int i = 0; i < devices.length; i++) {
                    String dev = devices[i];
                    dev = dev.replace(" ", "");
                    devs.add(dev);
                }
            }
        });
        return devs;
    }

    //完成
    @Override
    protected void done() {
        super.done();
        ArrayList<ItemBaen> temp = null;
        boolean isRead = false;
        try {
            ArrayList<String> devs = (ArrayList<String>) get();
            temp = setDevices(devs);
            isRead = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("获取设备出错" + e.getMessage());
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("获取设备出错" + e.getMessage());
        }
        listener.onDevLinkState(isRead, temp);
    }

    private ArrayList<ItemBaen> setDevices(ArrayList<String> devs) {
        ArrayList<ItemBaen> temp = new ArrayList();
        for (int i = 0; i < devs.size(); i++) {
            String name = devs.get(i);
            ItemBaen b = new ItemBaen();
            b.name = name;
            temp.add(b);
        }
        return temp;
    }
}
