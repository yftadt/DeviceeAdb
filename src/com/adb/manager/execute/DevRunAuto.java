package com.adb.manager.execute;

import com.adb.bean.EventBean;
import com.adb.bean.ItemBaen;
import com.adb.listener.DevRunListener;
import com.adb.manager.command.Adb;
import com.cmd.CmdBase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//自动运行
public class DevRunAuto extends SwingWorker {
    //运行标签
    private boolean isRun = true;
    private DevRunListener devRunListener;
    //-1 停止运行，1 上下动，2 点击，3 返回，4 获取最上面的acy名称
    //100 自动化运行
    private int runType = -1;

    public void setRunType(int runType) {
        this.runType = runType;
    }

    public int getRunType() {
        return runType;
    }

    public DevRunAuto(DevRunListener devRunListener) {
        this.devRunListener = devRunListener;
    }

    public void setStop() {
        isRun = false;
    }

    //延时（获取随机数值） 单位秒
    private int getTimeDelay(int delayType) {
        //秒
        int times = 0;
        //1 上下动，2 点击看广告，3 返回，4 获取最上面的act名称
        switch (delayType) {
            case 1:
                Random random = new Random();
                int min = 1; // 最小值
                int max = 5; // 最大值
                int randomInt = random.nextInt(max - min + 1) + min;
                times = randomInt * 60;
                break;
            case 2:
                //看广告的间隔
                /*if (true) {
                    //测试用
                    times = 10;
                    break;
                }*/
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
        if (runType == 100) {
            runDevAuto(devs);
        } else {
            //runDev(devs);
        }
        return 0;
    }

    //看视广告隔时间
    private int adTimeSpace;
    private EventBean evenData;

    //自动运行
    private void runDevAuto(ArrayList<ItemBaen> devs) throws Exception {
        while (devs.size() > 0) {
            if (runType == -1) {
                isRun = false;
            }
            evenData = devRunListener.getEventData();
            if (evenData == null) {
                isRun = false;
            }
            if (!isRun) {
                String msg = "执行：停止运行";
                System.out.println(msg);
                publish(0);
                return;
            }
            if (adTimeSpace == 0) {
                adTimeSpace = getTimeDelay(2);
            }
            System.out.println("看广告的间隔时间：" + adTimeSpace);
            int code = evenData.getRunCode(adTimeSpace);
            int codeLast = evenData.getRunCodeLast();
            switch (code) {
                case 1:
                    if (codeLast == 2) {
                        //调用 返回按钮 4 次
                        for (int i = 0; i < devs.size(); i++) {
                            ItemBaen dev = devs.get(i);
                            //
                            setDevRunBack(dev);
                            runTimeDelay(5);
                            //
                            setDevRunBack(dev);
                            runTimeDelay(5);
                            //
                            setDevRunBack(dev);
                            runTimeDelay(5);
                            //
                            if (evenData.dataType == 2) {
                                setDevRun(dev, "250 250 250 100");
                                runTimeDelay(5);
                            }
                            //
                            setDevRunBack(dev);
                            runTimeDelay(5);
                        }
                    }
                    //看视频
                    for (int i = 0; i < devs.size(); i++) {
                        ItemBaen dev = devs.get(i);
                        setDevRun(dev, "250 250 250 -900");
                    }
                    evenData.setRunCodeLast(1);
                    int timeTemp = getTimeDelay(1);
                    runTimeDelay(timeTemp);
                    evenData.setVideoTime(timeTemp);
                    System.out.println("设置看视频时间 timeTemp=" + timeTemp + " 总时间=" + evenData.getVideoTime());
                    break;
                case 2:
                    if (codeLast == 1) {
                        //调用 点击按钮 4 次
                        for (int i = 0; i < devs.size(); i++) {
                            ItemBaen dev = devs.get(i);
                            //
                            setDevRunClick(dev, evenData.videoBtnX, evenData.videoBtnY);
                            runTimeDelay(5);
                            //
                            if (evenData.dataType == 1) {
                                setDevRunClick(dev, evenData.actBtnX, evenData.actBtnY);
                                runTimeDelay(5);
                            }
                            if (evenData.dataType == 2) {
                                setDevRun(dev, evenData.adSlide);
                                runTimeDelay(5);
                                setDevRunClick(dev, evenData.actBtnX2, evenData.actBtnY2);
                                runTimeDelay(5);
                            }
                            //
                            setDevRunClick(dev, evenData.taskBtnX, evenData.taskBtnY);
                            runTimeDelay(5);
                            //
                            setDevRunClick(dev, evenData.adBtnX, evenData.adBtnY);
                            runTimeDelay(5);
                        }
                        timeTemp = getTimeDelay(3);
                        runTimeDelay(timeTemp);
                    }
                    evenData.setRunCodeLast(2);
                    //刷新看广告是按
                    adTimeSpace = getTimeDelay(2);
                    break;
            }
            devs = devRunListener.getData();
        }
    }

    //延时操作
    private void runTimeDelay(int time) throws Exception {
        int tempTime = time;
        while (tempTime > 0) {
            //停止运行
            if (runType == -1) {
                isRun = false;
            }
            if (!isRun) {
                String msg = "执行：停止运行";
                System.out.println(msg);
                publish(0);
                return;
            }
            tempTime -= 1;
            Thread.sleep(1000);
            publish(tempTime);
        }
    }

    //上划
    private void setDevRun(ItemBaen dev, String xyxy) {
        Adb.getInstance().onDevicesRun(dev.name, xyxy, new CmdBase.OnCmdBack() {
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

    //点击
    private void setDevRunClick(ItemBaen dev, int x, int y) {
        dev.x = 827;
        dev.y = 1610;
        Adb.getInstance().onDevicesRunClick(dev.name, x, y, new CmdBase.OnCmdBack() {
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

    //返回
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

    //获取顶部act
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
                String str = "";
                if (evenData != null) {
                    str = ",广告间隔：" + adTimeSpace + "s" + " 视频已看时间：" + evenData.getVideoTime() + " 广告次数：" + evenData.adNum;
                }
                devRunListener.onUpdateUi(null, true, time + "s后开始运行" + str);
                break;
        }
    }

    //完成
    @Override
    protected void done() {
        super.done();
    }
}
