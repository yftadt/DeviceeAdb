package com.cmd;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
            //创建两个输出容器：
            StringBuilder stdout = new StringBuilder();
            StringBuilder stderr = new StringBuilder();
            //stdout：正常输出，例如配对成功信息
            //stderr：错误输出，例如配对码错误、连接失败
            //同时读取正常输出和错误输出，避免 ADB 输出较多时发生阻塞。
            Thread stdoutThread = new Thread(new StreamReader(process.getInputStream(), stdout));
            Thread stderrThread = new Thread(new StreamReader(process.getErrorStream(), stderr));
            stdoutThread.start();
            stderrThread.start();
            if (isState) {
                processState = process.waitFor();
                onCmdBack.onCmdState(processState, "");
                System.out.print("线程完成情况：" + processState);
            }
            stdoutThread.join();
            stderrThread.join();
            String msgRes = stdout.toString();
            if (stderr.length() > 0) {
                if (msgRes.length() > 0) {
                    msgRes += " ";
                }
                msgRes += stderr;
            }
            System.out.println("执行结果合并: " + msgRes);
            onCmdBack.onCmdState(ADB_RUN_COMPLETE, "执行成功");
            onCmdBack.onCmdMsg(ADB_RUN_COMPLETE, msgRes.trim());
        } catch (IOException e) {
            onCmdBack.onCmdState(ADB_RUN_ERROR, "执行错误" + e.getMessage());
        } catch (InterruptedException e) {
            onCmdBack.onCmdState(ADB_RUN_ERROR, "执行错误" + e.getMessage());
        }
    }

    //读取命令执行结果的线程任务：持续读取 ADB 进程的输出内容，并保存到 StringBuilder 中
    private static class StreamReader implements Runnable {
        private final InputStream inputStream;
        private final StringBuilder result;

        StreamReader(InputStream inputStream, StringBuilder result) {
            this.inputStream = inputStream;
            this.result = result;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (result.length() > 0) {
                        result.append(' ');
                    }
                    result.append(line);
                }
                reader.close();
            } catch (IOException e) {
                if (result.length() > 0) {
                    result.append(' ');
                }
                result.append(e.getMessage());
            }
        }
    }

    public interface OnCmdBack {
        //执行状态
        void onCmdState(int state, String res);

        //执行成功之后的信息
        void onCmdMsg(int state, String res);
    }
}
