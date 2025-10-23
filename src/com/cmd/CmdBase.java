package com.cmd;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by guom on 2020/1/7.
 */
public class CmdBase {
    public static int ADB_RUN_START = 199;//执行开始
    public static int ADB_RUN_COMPLETE = 200;//执行完成
    public static int ADB_RUN_ERROR = 400;//执行出错

    public static boolean isAdbCode(int code) {
        return code == ADB_RUN_START || code == ADB_RUN_COMPLETE || code == ADB_RUN_ERROR;
    }

    //运行cmd命令
    protected void onRunCmd(String cmd, OnCmdBack onCmdBack) {
        onRunCmd(cmd, onCmdBack, false);
    }

    protected void onRunCmd(String cmd, OnCmdBack onCmdBack, boolean isState) {
        System.out.println("执行命令：cmd=" + cmd);
        int processState = 0;
        try {
            onCmdBack.onCmdResult(ADB_RUN_START, "执行开始");
            Runtime run = Runtime.getRuntime();
            Process process = run.exec(cmd);
            if (isState) {
                processState = process.waitFor();
                onCmdBack.onCmdResult(processState, "");
                System.out.print("线程完成情况：" + processState);
            }
            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            Scanner sc = new Scanner(isr);
            String msgRes = "";
            while (sc.hasNext()) {
                String msg = sc.next();
                if (msg == null) {
                    msg = "";
                }
                msgRes += " " + msg;
            }
            System.out.println(msgRes);
            onCmdBack.onCmdResult(processState, msgRes);
            isr.close();
            onCmdBack.onCmdResult(ADB_RUN_COMPLETE, "执行完成");
        } catch (IOException e) {
            onCmdBack.onCmdResult(ADB_RUN_ERROR, "执行错误" + e.getMessage());
        } catch (InterruptedException e) {
            onCmdBack.onCmdResult(ADB_RUN_ERROR, "执行错误" + e.getMessage());
        }
    }

    public interface OnCmdBack {
        void onCmdResult(int state, String res);
    }
}
