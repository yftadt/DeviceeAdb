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

    //运行cmd命令
    protected void onRunCmd(String cmd, OnCmdBack onCmdBack) {
        onRunCmd(cmd, onCmdBack, false);
    }

    protected void onRunCmd(String cmd, OnCmdBack onCmdBack, boolean isState) {
        System.out.println("执行命令：cmd=>>> " + cmd);
        int processState = 0;
        try {
            onCmdBack.onCmdState(ADB_RUN_START, "执行开始");
            Runtime run = Runtime.getRuntime();
            Process process = run.exec(cmd);
            if (isState) {
                processState = process.waitFor();
                onCmdBack.onCmdState(processState, "");
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
            System.out.println("执行结果合并: " + msgRes);
            isr.close();
            onCmdBack.onCmdState(ADB_RUN_COMPLETE, "执行成功");
            onCmdBack.onCmdMsg(ADB_RUN_COMPLETE, msgRes);
        } catch (IOException e) {
            onCmdBack.onCmdState(ADB_RUN_ERROR, "执行错误" + e.getMessage());
        } catch (InterruptedException e) {
            onCmdBack.onCmdState(ADB_RUN_ERROR, "执行错误" + e.getMessage());
        }
    }

    public interface OnCmdBack {
        //执行状态
        void onCmdState(int state, String res);

        //执行成功之后的信息
        void onCmdMsg(int state, String res);
    }
}
