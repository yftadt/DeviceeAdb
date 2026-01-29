package com.main;

import com.adb.manager.command.Adb;
import com.aes.manger.AESWindow;
import com.adb.manager.AdbWindow;
import com.test.Calculator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppMain {

    public static void main(String[] args) {
        //AESRunIng();
        adbRunIng();
        //test12();

        //imgSetSize();
    }

    private static void test12() {
        Throwable t1 = new Throwable("2345");
        Throwable t2 = new Throwable(t1);
        System.out.println(t2.getLocalizedMessage());
    }

    private static void test1() {
        Calculator frame = new Calculator();
        frame.setVisible(true);
    }

    //adb ；链接程序
    private static void adbRunIng() {
        Adb.getInstance().isInstallAdb = true;
        new AdbWindow().initView();
    }

    //cfb 解密小程序
    private static void AESRunIng() {
        new AESWindow().initView();
    }

    //app 桌面图标重新设置大小 并且保存
    private static void imgSetSize() {
        String rootPath = "D:\\appicon\\";
        String imgName = "ic_launcher.png";
        //
        String hdpi48 = rootPath + "mipmap-mdpi";
        imgSetSize(rootPath + imgName, 48, 48, hdpi48, imgName);
        String hdpi72 = rootPath + "mipmap-hdpi";
        imgSetSize(rootPath + imgName, 72, 72, hdpi72, imgName);
        String hdpi96 = rootPath + "mipmap-xhdpi";
        imgSetSize(rootPath + imgName, 96, 96, hdpi96, imgName);
        String hdpi144 = rootPath + "mipmap-xxhdpi";
        imgSetSize(rootPath + imgName, 144, 144, hdpi144, imgName);
        String hdpi192 = rootPath + "mipmap-xxxhdpi";
        imgSetSize(rootPath + imgName, 192, 192, hdpi192, imgName);
    }

    private static void imgSetSize(String imgPath, int width, int height,
                                   String savePath, String imgName) {
        File file = new File(savePath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        try {
            // 1. 加载图像
            File inputFile = new File(imgPath); // 修改为你的图片路径
            BufferedImage image = ImageIO.read(inputFile);

            // 2. 缩放图像（调整到新的宽度和高度）
            int newWidth = width;  // 新宽度
            int newHeight = height; // 新高度
            BufferedImage resizedImage = resizeImage(image, newWidth, newHeight);

            // 3. 保存缩放后的图像
            File outputFile = new File(savePath + "\\" + imgName);// 输出文件路径
            //图片类型
            //jpg,png
            String[] strs = imgName.split("\\.");
            String imgType = "jpg";
            if (strs.length == 2) {
                imgType = strs[1];
            }
            ImageIO.write(resizedImage, imgType, outputFile);  // 保存为PNG格式
            //
            System.out.println("图片大小修改并保存成功：" + outputFile.getPath());
        } catch (IOException e) {
            System.out.println("图片大小修改并保存失败：" + e.getMessage());
        }
    }

    // 缩放图像
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        // 使用Graphics2D进行图像缩放
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //BufferedImage resizedImage = new BufferedImage(width, height, originalImage.getType());
        // 获取Graphics2D对象并绘制图像
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();  // 释放资源
        return resizedImage;
    }


}
