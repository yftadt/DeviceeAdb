package com.adb.manager.execute;

import com.adb.manager.command.Adb;
import com.cmd.CmdBase;
import com.adb.listener.DevAdbListener;

import javax.swing.*;

//运行或者停止设备
public class AdbStart extends SwingWorker {
    private DevAdbListener listener;

    public AdbStart(DevAdbListener listener) {
        this.listener = listener;
    }

    @Override
    protected Object doInBackground() throws Exception {
        Adb.getInstance().onAdbStart(new CmdBase.OnCmdBack() {

            @Override
            public void onCmdResult(int state, String res) {
                System.out.println("onCmdResult结果 " + state + " " + res);
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
