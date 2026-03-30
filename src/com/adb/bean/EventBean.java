package com.adb.bean;

import com.sun.org.apache.bcel.internal.generic.PUSH;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by guom on 2019/12/19.
 */
public class EventBean implements Serializable {
    //数据类型
    public int dataType;
    public int w, h;
    //x=538 y=2121
    public int videoBtnX, videoBtnY;//赚钱按钮
    public String adSlide;//滑动 250 250 250 -10
    //x=919 y=1592
    public int actBtnX, actBtnY;//活动按钮
    public int actBtnX2, actBtnY2;//活动按钮2

    //x=517 y=2013
    public int taskBtnX, taskBtnY;//任务按钮

    //x=849 y=1607
    public int adBtnX, adBtnY;//广告按钮
    public int adNum;//广告次数
    //1 看视频 2 看广告
    private int runCode = 1;
    //（上一次的状态）1 看视频 2 看广告
    private int runCodeLast = 1;
    //看视频时间 秒
    private int videoTime = 0;

    public int getRunCode(int adTimeSpace) {
        if (videoTime >= adTimeSpace && adNum > 0) {
            runCode = 2;
        } else {
            runCode = 1;
        }
        return runCode;
    }

    public void setRunCodeLast(int runCodeLast) {
        if (runCodeLast == 2) {
            videoTime = 0;
            adNum -= 1;
            System.out.println("这里adNum:" + adNum);
        }
        this.runCodeLast = runCodeLast;
    }

    public int getRunCodeLast() {
        return runCodeLast;
    }

    public void setVideoTime(int time) {
        videoTime += time;
    }

    public int getVideoTime() {
        return videoTime;
    }

    //获取一个内容上划坐标
    public String getUpwardXY() {
        int minW = 100;
        int maxW = w - 100;
        //
        int minH = (h / 3) * 2;
        int maxH = h - 100;
        int x1 = getRandom(minW, maxW);
        int y1 = getRandom(minH, maxH);
        //
        int x2 = getRandom(x1 - 50, x1 + 50);
        int maxMove = h - minH;
        if (maxMove < 900) {
            maxMove = 900;
        }
        int move = getRandom(500, maxMove);
        int y2 = getRandom(y1 - move, y1 - move);
        //
        return x1 + " " + y1 + " " + x2 + " " + y2;
    }

    //minI:最小值    maxI:最大值
    private int getRandom(int minI, int maxI) {
        Random random = new Random();
        int min = minI; // 最小值
        int max = maxI; // 最大值
        int randomInt = random.nextInt(max - min + 1) + min;
        return randomInt;
    }
}
