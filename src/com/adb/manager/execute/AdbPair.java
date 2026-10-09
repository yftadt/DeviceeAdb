package com.adb.manager.execute;

import com.adb.listener.DevAdbListener;
import com.adb.manager.command.Adb;
import com.cmd.CmdBase;

import javax.swing.*;

//配对无线调试设备
public class AdbPair extends SwingWorker {
    private final DevAdbListener listener;
    private final String address;
    private final String pairingCode;

    public AdbPair(DevAdbListener listener, String address, String pairingCode) {
        this.listener = listener;
        this.address = address;
        this.pairingCode = pairingCode;
    }

    @Override
    protected Object doInBackground() {
        Adb.getInstance().onDevPair(address, pairingCode, new CmdBase.OnCmdBack() {
            @Override
            public void onCmdState(int state, String res) {
                if (state != CmdBase.ADB_RUN_COMPLETE) {
                    listener.onMsgBack(String.valueOf(state), res, null);
                }
            }

            @Override
            public void onCmdMsg(int state, String res) {
                listener.onMsgBack(String.valueOf(state), res, null);
            }
        });
        return 0;
    }
}
