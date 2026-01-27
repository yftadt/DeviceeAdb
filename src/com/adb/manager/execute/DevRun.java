package com.adb.manager.execute;

import com.adb.manager.command.Adb;
import com.cmd.CmdBase;
import com.adb.bean.ItemBaen;
import com.adb.listener.DevRunListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

//运行或者停止设备
public class DevRun extends SwingWorker {
    //运行标签
    private boolean isRun = true;
    private DevRunListener devRunListener;
    //延时
    private int timeDelay = 60;//单位秒

    public DevRun(DevRunListener devRunListener) {
        this.devRunListener = devRunListener;
    }

    public void setStop() {
        isRun = false;
    }


    @Override
    protected Object doInBackground() throws Exception {
        ArrayList<ItemBaen> devs = devRunListener.getData();
        if (devs.size() == 0) {
            return 0;
        }
        while (devs.size() > 0) {
            int timeTemp = timeDelay;
            for (int i = 0; i < devs.size(); i++) {
                ItemBaen dev = devs.get(i);
                dev.runType = 1;
                setRun(dev);
                //setDevRun(dev);
            }
            while (timeTemp > 0) {
                //停止运行
                if (!isRun) {
                    String msg = "执行：停止运行";
                    System.out.println(msg);
                    publish(0);
                    return 0;
                }
                timeTemp -= 1;
                Thread.sleep(1000);
                publish(timeTemp);
            }
            devs = devRunListener.getData();
        }
        return 0;
    }

    //开始运行
    private void setRun(ItemBaen dev) {
        switch (dev.runType) {
            case 1:
                //上下动
                setDevRun(dev);
                break;
            case 2:
                //点击
                setDevRunClick(dev);
                break;
            case 3:
                //返回，
                setDevRunBack(dev);
                break;
            case 4:
                //获取最上面的act名称
                setDevRunActTop(dev);
                break;
            default:
                System.out.println("执行：运行类型错误");
                break;
        }
    }

    private void setDevRun(ItemBaen dev) {
        Adb.getInstance().onDevicesRun(dev.name, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdResult(int state, String res) {
                System.out.println("onCmdResult 运行结果：" + state + " :" + res);
                //没有找到这台设备
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
                }
            }
        });
    }

    private void setDevRunClick(ItemBaen dev) {
        Adb.getInstance().onDevicesRunClick(dev.name, dev.x, dev.y, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdResult(int state, String res) {
                System.out.println("onCmdResult 运行：" + state + " :" + res);
                //没有找到这台设备
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
                } else {
                    setRun(dev);
                }
            }
        });
    }

    private void setDevRunBack(ItemBaen dev) {
        Adb.getInstance().onDevicesRunBack(dev.name, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdResult(int state, String res) {
                System.out.println("onCmdResult 运行：" + state + " :" + res);
                //没有找到这台设备
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
                } else {
                    setRun(dev);
                }
            }
        });
    }

    private void setDevRunActTop(ItemBaen dev) {
        Adb.getInstance().onDevicesRunTopAct(dev.name, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdResult(int state, String res) {
                System.out.println("onCmdResult 运行：" + state + " :" + res);
                //没有找到这台设备
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
                } else {
                    setRun(dev);
                }
            }
        });
    }

    //进度
    @Override
    protected void process(List chunks) {
        super.process(chunks);
        int time = 0;
        for (Object chunk : chunks) {
            if (chunk == null) {
                continue;
            }
            System.out.println("等待" + chunk);
            if (chunk instanceof Integer) {
                time = (Integer) chunk;
                break;
            }

        }
        switch (time) {
            case 0:
                devRunListener.onUpdateUi(null, true, "停止运行");
                break;
            case 1:
                devRunListener.onUpdateUi(null, true, "正在运行");
                break;
            default:
                devRunListener.onUpdateUi(null, true, time + "s后开始运行");
                break;
        }
    }

    //完成
    @Override
    protected void done() {
        super.done();
    }
}
