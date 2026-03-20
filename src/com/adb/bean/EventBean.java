package com.adb.bean;

import java.io.Serializable;

/**
 * Created by guom on 2019/12/19.
 */
public class EventBean implements Serializable {
    //x=538 y=2121
    public int videoBtnX, videoBtnY;//赚钱按钮
    //x=919 y=1592
    public int actBtnX, actBtnY;//活动按钮
    //x=517 y=2013
    public int taskBtnX, taskBtnY;//任务按钮
    //x=849 y=1607
    public int adBtnX, getAdBtnY;//广告按钮
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
}
