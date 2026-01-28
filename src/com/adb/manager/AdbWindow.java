package com.adb.manager;

import com.adb.bean.DevData;
import com.adb.item.adapter.DevModel;
import com.adb.item.adapter.DevModelClick;
import com.adb.item.adapter.RunModel;
import com.adb.listener.*;
import com.adb.bean.ItemBaen;
import com.adb.item.ui.DevItemUi;
import com.adb.item.ui.CellRenderer;
import com.adb.manager.command.Adb;
import com.adb.manager.command.DevManager;
import com.adb.manager.view.ADBView;
import com.utile.FileUtile;
import com.utile.IPS;
import com.view.JPanelFixed;
import com.view.JPanelMin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 界面UI
 */
public class AdbWindow {
    private JFrame rootView;
    private String keyName = "sign_file_data";

    public void initView() {

        //
        rootView = new JFrame("adb链接");
        rootView.setSize(700, 600);
        rootView.setLayout(new BorderLayout());
        //
        //中间布局
        rootView.add(BorderLayout.CENTER, getSelectUi());
        //右侧布局
        rootView.add(BorderLayout.EAST, getDevicesUi());
        //
        ADBView adbView=   new ADBView();
        rootView.add(BorderLayout.WEST, adbView.getADBUi());
        //
        rootView.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //
        rootView.setVisible(true);
        //
        //读取数据
        Object bean = FileUtile.getObj(keyName);
        if (bean != null) {
            DevData devData = (DevData) bean;
            ipET.setText(devData.ipAddr);
        }
    }


    //中间
    //添加选中的ui
    public JPanel getSelectUi() {
        // 创建内容面包容器，指定使用 边界布局
        JPanel view = new JPanelMin(800, new BorderLayout());
        runBtn = new JButton("开始运行");
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("runBtn");
                onBtnClick(4, "点击运行runBtn");
            }
        });
        view.add(BorderLayout.NORTH, runBtn);
        //已选中的设备
        RunModel runModel = new RunModel();
        devSelectJL = new JList(runModel);
        ArrayList<ItemBaen> datas = new ArrayList();
        runModel.setData(datas);
        devSelectJL.setCellRenderer(new CellRenderer(false));
        JScrollPane layoutSP2 = new JScrollPane(devSelectJL);
        layoutSP2.setPreferredSize(new Dimension(100, 200));
        view.add(BorderLayout.CENTER, layoutSP2);
        //
        JPanel msgJP = new JPanel();
        msgJl = new JLabel();
        msgJl.setSize(10, 1000);
        msgJl.setText("已停止");
        msgJP.add(msgJl);
        msgJP.setLayout(new FlowLayout(FlowLayout.LEFT));
        //设置高度无用  只能设置边距
        msgJP.setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 2)); // 也可以使用这种方式设置外边距
        view.add(BorderLayout.SOUTH, msgJP);
        return view;
    }

    //右侧ui
    private JPanel getDevicesUi() {
        JPanel panel = new JPanelFixed(180, new BorderLayout());
        panel.add(BorderLayout.NORTH, getConnectUi());
        panel.add(BorderLayout.SOUTH, getDevWifiUi());
        return panel;
    }


    //运行btn，获取已链接的设备ui，消息
    private JPanel getConnectUi() {
        //  创建内容面包容器，指定使用 边界布局
        JPanel view = new JPanel(new BorderLayout());
        JButton searchBtn = new JButton("获取设备");
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("portBtn");
                onBtnClick(3, "获取已链接的设备");
            }
        });
        view.add(BorderLayout.NORTH, searchBtn);
        //
        //JList 数据模型
        DevModel listModel = new DevModel();
        //实例化列表框
        devReadsJL = new JList(listModel);
        ArrayList<ItemBaen> items = new ArrayList();
        listModel.setData(items);
        //自定义ui
        devReadsJL.setCellRenderer(new DevItemUi());
        //设置监听
        devReadsJL.addMouseListener(new DevModelClick(devReadsJL, new DevOptionsListener() {
            @Override
            public void onSelect(ItemBaen dev) {
                onDevSelect(dev);
            }
        }));
        //实例化JScrollPane面板
        JScrollPane layoutSP = new JScrollPane(devReadsJL);
        //设置JScrollPane大小
        layoutSP.setPreferredSize(new Dimension(150, 200));
        view.add(BorderLayout.CENTER, layoutSP);
        //add(BorderLayout.EAST, panel);
        return view;
    }

    //设置端口号，wifi 链接
    private JPanel getDevWifiUi() {
        final JPanel view = new JPanel(new GridLayout(7, 1));//6行 1列
        //提示UI
        hintJl = new JTextField();
        //hintJl = new JLabel();
        hintJl.setText("设置连接IP，端口号");
        //背景透明
        hintJl.setOpaque(false);
        //去边框
        hintJl.setBorder(new EmptyBorder(0, 0, 0, 0));
        view.add(hintJl, new FlowLayout(FlowLayout.LEFT));
        //端口号 输入框
        portET = new JTextField();
        portET.setSize(200, 25);
        portET.setColumns(10);
        view.add(portET, new FlowLayout(FlowLayout.LEFT));
        //
        JButton portBtn = new JButton("设置端口");
        portBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("portBtn");
                onBtnClick(2, "设置端口号");
            }
        });
        view.add(portBtn, new FlowLayout(FlowLayout.LEFT));
        //IP 输入框
        ipET = new JTextField();
        ipET.setSize(200, 25);
        ipET.setColumns(10);
        view.add(ipET, new FlowLayout(FlowLayout.LEFT));
        //无线连接按钮
        JButton wifiBtn = new JButton("无线连接");
        wifiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                System.out.println("wifiBtn");
                onBtnClick(1, "无线连接");
            }
        });
        view.add(wifiBtn, new FlowLayout(FlowLayout.LEFT));
        //
        JButton adbStopBtn = new JButton("停止ADB服务");
        adbStopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                System.out.println("停止ADB服务");
                onBtnClick(5, "停止ADB服务");
            }
        });
        view.add(adbStopBtn, new FlowLayout(FlowLayout.LEFT));
        //
        JButton startStopBtn = new JButton("启动ADB服务");
        startStopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
                System.out.println("启动ADB服务");
                onBtnClick(6, "启动ADB服务");
            }
        });
        view.add(startStopBtn, new FlowLayout(FlowLayout.LEFT));

        view.setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 2));
        return view;
    }



    //IP 输入框
    private JTextField ipET;
    //端口号 输入框
    private JTextField portET;
    //消息显示
    private JLabel msgJl;
    //已链接的设备
    private JList devReadsJL;
    //选中的设备
    private JList devSelectJL;
    private ArrayList<ItemBaen> dataSelect = new ArrayList<>();
    //运行按钮
    private JButton runBtn;
    //true 正在运行
    private boolean isDevRun;
    //true 运行出错，再次搜索 然后运行
    private boolean isDevRunAgain;
    //提示选中的设置
    private JTextField hintJl;
    //最近选中的设备
    private ItemBaen devSelect;

    //1 无线连接 2 设置端口号 3 获取设备 4 运行 5 停止adb服务 6 启动adb服务
    private void onBtnClick(int type, String msg) {
        System.out.println("type=" + type + " msg=" + msg);
        switch (type) {
            case 1:
                //链接设备
                String ips = ipET.getText();
                if (!IPS.isIp(ips)) {
                    dialogShow(1, "请输入手机上的IP");
                }
                String port = portET.getText();
                if (port.isEmpty()) {
                    dialogShow(2, "请输入手机上的端口号");
                }
                //
                DevData devData = new DevData();
                devData.ipAddr = ips;
                boolean isSave = FileUtile.saveObj(devData, keyName);
                System.out.println("保存数据成功？" + isSave);
                //
                msgJl.setText("开始链接 " + ips + ":" + port);
                DevManager.getInstance().devLink(new DevLinkListener() {
                    @Override
                    public void onDevLinkState(boolean isConnected, String ip, String port) {
                        System.out.println("链接是否成功：" + isConnected);
                        if (isConnected) {
                            msgJl.setText("链接成功");
                        } else {
                            msgJl.setText("链接失败");
                        }
                    }
                }, ips, port);
                break;
            case 2:
                //设置端口号 功能是有线连接转无线连接的，所以先要有线连接
                port = portET.getText();
                if (devSelect == null) {
                    dialogShowMsg("请先选中设备：功能是有线连接转无线连接的，所以先要有线连接");
                    return;
                }
                int state = Adb.getInstance().onDevSetPort(devSelect.name, port);
                if (state == 0) {
                    devSelect.isSetPort = true;
                }
                break;
            case 3:
                //搜索已链接的设备
                msgJl.setText("正在搜索...");
                DevManager.getInstance().devRead(new DevReadListener() {
                    @Override
                    public void onMsgBack(String code, String msg, Object obj) {
                        msgJl.setText(code + " " + msg);
                    }

                    @Override
                    public void onDevLinkState(boolean isRead, ArrayList<ItemBaen> datas) {
                        if (isRead) {
                            HashMap<String, Integer> map = new HashMap<String, Integer>();
                            for (int i = 0; i < dataSelect.size(); i++) {
                                ItemBaen dev = dataSelect.get(i);
                                map.put(dev.name, i);
                            }
                            for (int i = 0; i < datas.size(); i++) {
                                ItemBaen dev = datas.get(i);
                                Integer index = map.get(dev.name);
                                if (index == null) {
                                    continue;
                                }
                                dev.isSelect = true;
                                map.remove(dev.name);
                            }
                            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                                String key = entry.getKey();
                                Integer value = entry.getValue();
                                System.out.println("Key: " + key + ", Value: " + value);
                                if (value != null) {
                                    dataSelect.remove(value);
                                }
                            }
                            devReadsJL.setListData(datas.toArray());
                            devReadsJL.updateUI();
                            //
                            devSelectJL.setListData(dataSelect.toArray());
                            devSelectJL.updateUI();
                            msgJl.setText("搜索完成...");
                            if (isDevRunAgain) {
                                msgJl.setText("再次运行...");
                                System.out.println("再次运行...");
                                isDevRunAgain = false;
                                onBtnClick(4, "点击运行runBtn");
                            }
                        } else {
                            msgJl.setText("搜索失败...");
                        }
                    }
                });
                break;
            case 4:
                //开始运行
                isDevRun = !isDevRun;
                if (isDevRun) {
                    DevManager.getInstance().devRun(new DevRunListener() {
                        @Override
                        public ArrayList<ItemBaen> getData() {
                            return dataSelect;
                        }

                        @Override
                        public void onUpdateUi(ItemBaen dev, boolean isRun, String msg) {
                            msgJl.setText(msg);
                            if (!isRun && dev != null) {
                                isDevRun = false;
                                isDevRunAgain = true;
                                onBtnClick(3, "获取已链接的设备");
                                runBtn.setText("开始运行");
                            }
                        }
                    });

                } else {
                    //停止运行
                    DevManager.getInstance().devRunStop();
                }
                runBtn.setText(isDevRun ? "运行停止" : "开始运行");
                break;
            case 5:
                //停止adb 服务
                DevManager.getInstance().devRunStop();
                //清空获取到的设备
                ArrayList<ItemBaen> datas = new ArrayList<>();
                devReadsJL.setListData(datas.toArray());
                devReadsJL.updateUI();
                //清空选中的设备
                dataSelect = new ArrayList<>();
                devSelectJL.setListData(dataSelect.toArray());
                devSelectJL.updateUI();
                //执行停止服务命令
                DevManager.getInstance().adbStop(new DevAdbListener() {

                    @Override
                    public void onMsgBack(String code, String msg, Object obj) {
                        msgJl.setText(code + " " + msg);
                    }
                });
                break;
            case 6:
                //启动adb服务
                DevManager.getInstance().adbStart(new DevAdbListener() {

                    @Override
                    public void onMsgBack(String code, String msg, Object obj) {
                        msgJl.setText(code + " " + msg);
                    }
                });
                break;
        }
    }

    //选择了设备
    private void onDevSelect(ItemBaen dev) {
        if (dev.isSelect) {
            dataSelect.add(dev);
            devSelect = dev;
            hintJl.setText("选择了：" + devSelect.name);
        } else {
            for (int i = 0; i < dataSelect.size(); i++) {
                ItemBaen tempDev = dataSelect.get(i);
                if (dev.name.equals(tempDev.name)) {
                    dataSelect.remove(i);
                    devSelect = null;
                    hintJl.setText("设置连接IP，端口号");
                }
            }
        }
        if (dataSelect.size() == 0) {
            DevManager.getInstance().devRunStop();
        }
        devSelectJL.setListData(dataSelect.toArray());
        devSelectJL.updateUI();
    }

    private void dialogShow(int type, String msg) {
        String text = JOptionPane.showInputDialog(rootView, msg);
        switch (type) {
            case 1:
                //输入ip
                ipET.setText(text);
                break;
            case 2:
                //输入端口号
                portET.setText(text);
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    private void dialogShowMsg(String msg) {
        JOptionPane.showMessageDialog(
                rootView,
                msg,
                "消息标题",
                JOptionPane.WARNING_MESSAGE
        );
    }

}
