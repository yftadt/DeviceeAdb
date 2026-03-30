package com.adb.manager.execute;

import com.adb.bean.EventBean;
import com.adb.manager.command.Adb;
import com.cmd.CmdBase;
import com.adb.bean.ItemBaen;
import com.adb.listener.DevRunListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//运行或者停止设备
public class DevRun extends SwingWorker {
    //运行标签
    private boolean isRun = true;
    private DevRunListener devRunListener;
    //-1 停止运行，1 上下动，2 点击，3 返回，4 获取最上面的acy名称
    private int runType = -1;

    public void setRunType(int runType) {
        this.runType = runType;
    }

    public int getRunType() {
        return runType;
    }

    private EventBean evenData;

    public DevRun(DevRunListener devRunListener) {
        this.devRunListener = devRunListener;
        evenData = devRunListener.getEventData();
    }

    public void setStop() {
        isRun = false;
    }

    //延时（获取随机数值） 单位秒
    private int getTimeDelay() {
        //秒
        int times = 0;

        //1 上下动，2 点击看广告，3 返回，4 获取最上面的act名称
        switch (runType) {
            case 1:
                Random random = new Random();
                int min = 1; // 最小值
                int max = 5; // 最大值
                int randomInt = random.nextInt(max - min + 1) + min;
                times = randomInt * 60;
                break;
            case 2:
                //看广告的间隔
                random = new Random();
                min = 11 * 60; // 最小值
                max = 12 * 60; // 最大值
                times = random.nextInt(max - min + 1) + min;
                break;
            case 3:
                //正在看广告
                random = new Random();
                min = 50; // 最小值
                max = 70; // 最大值
                times = random.nextInt(max - min + 1) + min;
                break;
            case 4:
                times = 30;
                break;
        }
        return times;
    }

    @Override
    protected Object doInBackground() throws Exception {
        ArrayList<ItemBaen> devs = devRunListener.getData();
        if (devs.size() == 0) {
            return 0;
        }
        while (devs.size() > 0) {
            for (int i = 0; i < devs.size(); i++) {
                ItemBaen dev = devs.get(i);
                setRun(dev);
            }
            int timeTemp = getTimeDelay();
            while (timeTemp > 0) {
                //停止运行
                if (runType == -1) {
                    isRun = false;
                }
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

    private boolean isAdCheck = false;

    //开始运行
    private void setRun(ItemBaen dev) {
        switch (runType) {
            case 1:
                //上下动
                setDevRun(dev);
                break;
            case 2:
                //广告点击
                setDevRunClick(dev);
                //一分钟之后检查 页面
                runType = 3;
                break;
            case 3:
                //返回，
                setDevRunBack(dev);
                runType = 2;
                break;
            case 4:
                //获取最上面的act名称
                setDevRunActTop(dev);
                runType = 2;
                break;
            default:
                System.out.println("执行：运行类型错误");
                break;
        }
    }

    private void setDevRun(ItemBaen dev) {
        String xy = evenData.getUpwardXY();
        Adb.getInstance().onDevicesRun(dev.name, xy, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdState(int state, String res) {
                System.out.println("setDevRun结果回调1：" + state + " :" + res);

            }

            @Override
            public void onCmdMsg(int state, String res) {
                System.out.println("setDevRun结果回调2：" + state + " :" + res);
                //没有找到这台设备
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
                }
            }
        });
    }

    private void setDevRunClick(ItemBaen dev) {
       /* dev.x = 827;
        dev.y = 1610;*/
        dev.x = evenData.adBtnX;
        dev.y = evenData.adBtnY;
        Adb.getInstance().onDevicesRunClick(dev.name, dev.x, dev.y, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdState(int state, String res) {
                System.out.println("结果回调1：" + state + " :" + res);
            }

            @Override
            public void onCmdMsg(int state, String res) {
                System.out.println("结果回调2：" + state + " :" + res);
                //没有找到这台设备
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
                }
            }
        });
    }

    private void setDevRunBack(ItemBaen dev) {
        Adb.getInstance().onDevicesRunBack(dev.name, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdState(int state, String res) {
                System.out.println("结果回调1：" + state + " :" + res);

            }

            @Override
            public void onCmdMsg(int state, String res) {
                System.out.println("结果回调2：" + state + " :" + res);
                //没有找到这台设备
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
                }
            }
        });
    }

    private void setDevRunActTop(ItemBaen dev) {
        Adb.getInstance().onDevicesRunTopAct(dev.name, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdState(int state, String res) {
                System.out.println("结果回调1 " + state + " " + res);
            }

            @Override
            public void onCmdMsg(int state, String res) {
                System.out.println("结果回调2 " + state + " " + res);
                //没有找到这台设备
                if (res.contains("")) {

                }
                if (res.contains("not found")) {
                    devRunListener.onUpdateUi(dev, false, res);
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
