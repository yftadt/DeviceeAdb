package com.adb.manager.execute;

import com.adb.listener.DevAdbListener;
import com.adb.manager.command.Adb;
import com.cmd.CmdBase;

import javax.swing.*;

//获取ADB 信息
public class AdbMsg extends SwingWorker {
    private DevAdbListener listener;

    public AdbMsg(DevAdbListener listener) {
        this.listener = listener;
    }

    @Override
    protected Object doInBackground() throws Exception {
        Adb.getInstance().onAdbMsg(new CmdBase.OnCmdBack() {

            @Override
            public void onCmdState(int state, String res) {
                System.out.println("结果回调1 " + state + " " + res);
                listener.onMsgBack(String.valueOf(state), res, null);
            }

            @Override
            public void onCmdMsg(int state, String res) {
                System.out.println("结果回调2 " + state + " " + res);
                listener.onMsgBack(String.valueOf(state), res, null);
            }
        });
        return 0;
    }

    //完成
    @Override
    protected void done() {
        System.out.println("这里后完成");
        super.done();
    }
}
