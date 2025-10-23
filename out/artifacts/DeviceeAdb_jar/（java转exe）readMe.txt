https://blog.csdn.net/qq_43461350/article/details/144187030
使用exe4j  可以是jar转为可执行程序
1.jre必须是32位定位，且版本号最高为1.8。为对应jre，本项目设置sdk版本是 1.8.0_151
2.jar编译版本（sdk版本）必须对应jre版本，即1.8
3.exe4j 转化时，可读取 配置.exe4j
4.该目录下的配置  是使用1.8.0_151_32_jre，里面含有 keytool命令，也有程序运行需要的java环境，
因此1.8.0_151_32_jre 不能删除，是绑定关系
jre是1.8.0_151_32_jre 版

5.exe4j中，报错：This executable was created with an evaluation version of exe4j
https://blog.csdn.net/u013456370/article/details/79214037
解决：exe4j 要注册
注册码如下：
A-XVK258563F-1p4lv7mg7sav
A-XVK209982F-1y0i3h4ywx2h1
A-XVK267351F-dpurrhnyarva
A-XVK204432F-1kkoilo1jy2h3r
A-XVK246130F-1l7msieqiwqnq
A-XVK249554F-pllh351kcke50
A-XVK238729F-25yn13iea25i
A-XVK222711F-134h5ta8yxbm0
A-XVK275016F-15wjjcbn4tpj
A-XVK275016F-15wjjcbn4tpj
 

其它
keytool -list -v -alias androiddebugkey -keystore "C:/Users/Administrator/.android/debug.keystore" -storepass android -keypass android

-genkeypair：原 -genkey，Java 1.6 之后更改，表示生成密钥对

-alias：产生别名，每个 keystore 都会关联这一个独一无二的 alias，alias 不区分大小写

-keyalg：指定产生密钥的算法

-keypass：指定别名条目的密码（私钥的密码）

-sigalg：签名算法名称

-dname：唯一判别名，cn 所有者名称，ou 组织单位名称，o 组织名称，l 城市或区域名称，st 州或省份名称，c 两字母国家代码

-validity：有效天数

-keystore：密钥库名称

-storetype：密钥库类型

-storepass：密钥库口令

使用指定的keytool 定位到jre的ben目录
keytool.exe  -list -v -keystore D:\出包\重新签名\yueyunclient.jks -storepass 258369

//先进入 bin 目录 再执行获取签名信息命令，两个cmd命令之间用&&连接
cd bin && keytool  -list -v -keystore D:\出包\重新签名\yueyunclient.jks -storepass 258369

//运行指定目录下的命令
1.最好使用 cmd.exe
比如：
String command = "cmd.exe /c keytool " + codeType + " -list -v -keystore " + keys[0] + " -storepass " + keys[1];
process = runtime.exec(command, null, file)//file 表示命令在这个目录下

cmd /c dir 是执行完dir命令后关闭命令窗口。

cmd /k dir 是执行完dir命令后不关闭命令窗口。

cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。

cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会关闭。


