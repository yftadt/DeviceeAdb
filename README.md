 
 ![img.png](read_0_存在就删除.png)




一：adb 端口被占用
1.netstat -aon|findstr "5037"
2.taskkill /pid 11228 /t /f    （杀进程 最右边是程序PID，我一般是杀掉出现比较多的那个pid  如11228   有时候到这一步就好了）
3.adb服务，有时候会出现多个。 这时候，可以使用另一个行命令，相对简洁的查看adb命令
查看：tasklist | findstr "adb"
杀死多个：taskkill /pid 11228 /pid 10536/t /f  （杀进程 中间是程序PID，我一般是杀掉出现比较多的那个pid  如11228）
4.上面的方式重复了N遍，死活不行，去环境变量里把adb去掉，重启电脑，打开Android Studio，可以USB直连手机；后面再把adb环境变量配置回去，重启电脑，就好了

adb kill-server
adb start-server



5.安装
adb install app.apk          # 普通安装
adb install -r app.apk       # 覆盖安装
adb install -t  D:\app\test2.apk    # 安装测试版APK
卸载
adb uninstall com.example.app       # 卸载应用（保留数据）
adb uninstall -k com.example.app    # 保留应用数据

6.配对 adb pair 192.168.1.2:5555  然后输入配对码

7.链接手机 adb connect 192.168.1.2:5555

8.断开链接 adb disconnect 192.168.1.2:5555

9.获取链接的设备
adb devices

10.查看adb端口号
# 方式1：查看5037端口是否被ADB占用（管理员CMD/PowerShell）
netstat -ano | findstr "5037"

# 方式2：更精准查看ADB进程对应的端口
tasklist | findstr "adb"  # 先获取ADB进程PID
netstat -ano | findstr "你的ADB进程PID" 例子：netstat -ano | findstr "15568"

11.重新设置端口号
# 1. 停止当前ADB服务
adb kill-server
# 2. 设置新端口（比如5038）
set ADB_SERVER_PORT=5038  # Windows
# 3. 重启ADB服务
adb start-server
# 4. 验证新端口
netstat -ano | findstr "5038"  # Windows

12.重新设置 adb 端口号第二种方法
在环境变量里
# 新增变量名 ANDROID_ADB_SERVER_PORT  变量值 9999
最好选择一个5位数的端口号（10000 ~ 65535），不易重复。
 