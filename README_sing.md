签名相关：


//#######验证本地签名证书的哈希值########
keytool -list -v -keystore 你的签名文件路径.jks -alias 你的别名
keytool -list -v -keystore D:\apk\xbkjnbc.jks -alias nbc
//输出
输入密钥库口令:
别名: nbc
创建日期: 2025年6月24日
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
所有者: C=-?, ST=江西, L=宜春, O=xbkj, OU=xbkj, CN=xbkj
发布者: C=-?, ST=江西, L=宜春, O=xbkj, OU=xbkj, CN=xbkj
序列号: 1
生效时间: Tue Jun 24 09:55:23 CST 2025, 失效时间: Sat Jun 18 09:55:23 CST 2050
证书指纹:
SHA1: A1:65:D6:13:8F:AF:1A:CC:32:90:BE:D8:29:8B:75:7D:DB:58:65:FE
SHA256: F3:F8:66:BE:6A:6E:B6:DE:41:2B:F8:1F:61:98:B8:C1:F4:5B:04:84:93:83:14:B6:19:BF:A4:F2:D3:FB:99:86
签名算法名称: SHA256withRSA
主体公共密钥算法: 2048 位 RSA 密钥
版本: 1

//#######验证APK签名########
apksigner cd  D:\Android\Sdk\build-tools\30.0.2\apksigner.bat
apksigner verify --verbose 你的APK文件.apk
apksigner verify --verbose D:\apk\OppoSignVerify_true.apk
//输出
Verifies
Verified using v1 scheme (JAR signing): true
Verified using v2 scheme (APK Signature Scheme v2): true
Verified using v3 scheme (APK Signature Scheme v3): true
Verified using v4 scheme (APK Signature Scheme v4): false
Verified for SourceStamp: false
Number of signers: 1

//#######/直接给apk签名########
apksigner sign --ks 你的签名证书路径.jks --ks-key-alias 你的密钥别名 --out 签名后的APK路径.apk 待签名的APK路径.apk
apksigner sign --ks D:\apk\xbkjnbc.jks --ks-key-alias nbc --out  D:\apk\OppoSignVerify_true_2.apk D:\apk\OppoSignVerify.apk


 