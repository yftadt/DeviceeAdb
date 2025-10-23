package com.utile;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

/**
 * 错误：java.security.InvalidKeyException: Illegal key size
 * https://www.cnblogs.com/dzcWeb/p/18531859
 * AES 加密解密示例
 */
public class AESManager {
    //加密模式之 ECB，AES/ECB/PKCS5Padding-----算法/模式/补码方式
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";

    //加密模式之 CBC，算法/模式/补码方式
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";

    //加密模式之 CFB，算法/模式/补码方式
    private static final String AES_CFB = "AES/CFB/PKCS5Padding";
    //AES 中的 IV 必须是 16 字节（128位）长
    private static final Integer IV_LENGTH = 16;

    /***
     * 空值校验
     * @param str 需要判断的值
     */
    public boolean isEmpty(Object str) {
        return null == str || "".equals(str);
    }

    /***
     * 字符串转byte字节
     * @param str 需要转换的字符串
     */
    public byte[] getBytes(String str) {
        if (isEmpty(str)) {
            return null;
        }
        try {
            return str.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 初始化向量（IV），它是一个随机生成的字节数组，用于增加加密和解密的安全性
     */
    public String getIV() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < IV_LENGTH; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /***
     * 获取一个 AES 密钥规范
     */
    public SecretKeySpec getSecretKeySpec(String key) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getBytes(key), "AES");
        return secretKeySpec;
    }

    /**
     * 加密模式 --- ECB
     *
     * @param text 需要加密的文本内容
     * @param key  加密的密钥 key
     */
    public String encrypt(String text, String key) {
        if (isEmpty(text) || isEmpty(key)) {
            return null;
        }
        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_ECB);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            // 加密字节数组，将明文转换为字节数组进行加密
            byte[] encryptedBytes = cipher.doFinal(getBytes(text));
            // 将密文转换为 Base64 编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密模式 --- ECB
     *
     * @param text 需要解密的文本内容
     * @param key  解密的密钥 key
     */
    public String decrypt(String text, String key) {
        if (isEmpty(text) || isEmpty(key)) {
            return null;
        }

        // 将密文转换为16字节的字节数组
        byte[] textBytes = Base64.getDecoder().decode(text);

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(AES_ECB);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            // 解密字节数组,将明文转换为字节数组进行解密
            byte[] decryptedBytes = cipher.doFinal(textBytes);
            // 将明文转换为字符串
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密 - 自定义加密模式
     *
     * @param text 需要加密的文本内容
     * @param key  加密的密钥 key
     * @param iv   初始化向量
     * @param mode 加密模式
     */
    public String encrypt(String text, String key, String iv, String mode) {
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return null;
        }

        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(mode);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥和初始化向量
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(getBytes(iv)));

            // 加密字节数组，将明文转换为字节数组进行加密
            byte[] encryptedBytes = cipher.doFinal(getBytes(text));

            // 将密文转换为 Base64 编码字符串
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密 - 自定义解密模式
     *
     * @param text 需要解密的文本内容
     * @param key  解密的密钥 key
     * @param iv   初始化向量
     * @param mode 解密模式
     */
    public String decrypt(String text, String key, String iv, String mode) {
        String str = "";
        if (isEmpty(text) || isEmpty(key) || isEmpty(iv)) {
            return str;
        }
        // 将密文转换为16字节的字节数组
        byte[] textBytes = Base64.getDecoder().decode(text);
        try {
            // 创建AES加密器
            Cipher cipher = Cipher.getInstance(mode);
            // 将密钥转换为SecretKeySpec格式
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 初始化Cipher为加密模式，传入密钥和初始化向量
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(getBytes(iv)));
            // 解密字节数组，将明文转换为字节数组进行解密
            byte[] decryptedBytes = cipher.doFinal(textBytes);
            // 将明文转换为字符串
            str = new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("解密失败：" + e.getCause());
        }
        return str;
    }

    //cfb 模式解密
    public String decryptCFB(String text, String key, String iv) {
        String str = decrypt(text, key, iv, AES_CFB);
        return str;
    }

    public void test() {
        String text = "我就是加密的内容";
        String key = "1234567812345678"; // 16字节的密钥
        String iv = getIV();
        //ECB
        String encryptTextECB = encrypt(text, key);
        System.out.println("ECB 加密后内容：" + encryptTextECB);
        System.out.println("ECB 解密后内容：" + decrypt(encryptTextECB, key));
        System.out.println();
        //CBC
        String encryptTextCBC = encrypt(text, key, iv, AES_CBC);
        System.out.println("CBC 加密IV：" + iv);
        System.out.println("CBC 加密后内容：" + encryptTextCBC);
        System.out.println("CBC 解密后内容：" + decrypt(encryptTextCBC, key, iv, AES_CBC));
        System.out.println();
        //CFB
        String encryptTextCFB = encrypt(text, key, iv, AES_CFB);
        System.out.println("CFB 加密IV：" + iv);
        System.out.println("CFB 加密后内容：" + encryptTextCFB);
        System.out.println("CFB 解密后内容：" + decrypt(encryptTextCFB, key, iv, AES_CFB));
    }

    private static AESManager aesManager;

    public static AESManager getInstance() {
        if (aesManager == null) {
            aesManager = new AESManager();
        }
        return aesManager;
    }

    public void test1() {
        //密匙
        String keyStr = "d45226d7abcd4d3ba1f164e094681b2b";
        //偏移量
        String offsetStr = "7bfff9944eafa5e5";
        //inatall2();
        String str = decrypt(getTxt(), keyStr, offsetStr, AES_CFB);
        System.out.println("解密信息：" + str);
    }

    private String getTxt() {
        String value = "iLs9BQSrx2QmpVl+sWh9CjfqQpdKv8beWrI7fWNmyrR70RIDS0Po14pez+jX9Sx9xAOEVVAASxPyEkLJstPx2UIOHA34PSN0wAeYU2DJxoh83bdYPFoNZSfcLcZvC0lGr4UxV2SLsq+Ap1BUtjZLy71/1duDvhh6ALOqnq0vJRyFozjtwEXdUgtazGlD1COZROeqJpGlNGh4qpiwZqzy+jF0GX1uiDpqdg5w7SQJrvb5Im9Felgpsysoda9lSzlErY3n5lNyGTLyoOP7y/TjzagMg3RWuP94+5UF8xbQPDYqhbkhffMJEIBoTkvcnzfeR2XJmHANOwh2A6BPrmPms6v2u8XbeD4DMLswC217Pn4UWHAqMS7bX5wZhHQrLIgDSypATW6j16aqDeo2/t2LGB+1Ar3rlgCUboFAJnoUUjxyNY99XB/cVtOo9WXm5UfrnIkwW+DzFwnemFOosk09v60SmEpEbbUQ9AGF2aIF5k4NTW7hTOwCKJVbn/SY731erS+xsgHwOJy2OsOTFoRYTBs3HJSZXVw9mcXC0QvWJbSquG8J2jS3a8h9EnSf4yCm/kt5aEiNOwCzEdJhXWbexcucnoR9N5U6yrxfNRlzMjLjmz3U/lqsCeA+d1NMdQCaR7JC9uuqw6tuXTwXk7y592u3rH594ga9irvQbBZAJKadP4v4Be0X9UKyf2dDvwhiV3sxCNBvbEYzv2ebeV65NbzSoEwIixfa0RYDE/5vNVpsS6FAAVWA3TqvaDQiSMk/mzHuRWrjqUHy3Du0buwU/RpZvWvKy5HhmYF/lN8p8FVrJ+BAuMj9CbcVoLPnP8eSrn5AhPKWVMTl5mkDIMJmeg15wW1kBx36rW/PctRjrzCoyPRdDBlheHrEg07e3AMFfCRhea3QWMiWN9JOLbCwHagt0r10Spq0oJ9kKaxdalXs4Whr902+IMzjzjIZzEYreirqL4EewY0zB/NtzBaxUiDtsKi/UYBCFdpOhZarXQsPinbJYc2HoCAOib2wEoRK";
        return value;
    }
}

