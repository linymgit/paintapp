package com.demo.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author lym
 * @Date 2019/12/31 8:48
 * @qq 1317113287
 * @Description 文件工具类
 */
public class FileUtils {

    public static String saveFile(BufferedImage targetImg) {
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();
        //后缀名过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter("图片文件(*.png)", "png");
        chooser.setFileFilter(filter);
        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {    //假如用户选择了保存
            File file = chooser.getSelectedFile();
            String fname = chooser.getName(file);    //从文件名输入框中获取文件名
            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if (!fname.contains(".png")) {
                file = new File(chooser.getCurrentDirectory(), fname + ".png");
            }
            try (
                    FileOutputStream fos = new FileOutputStream(file);
            ) {
                //写文件操作……
//                fos.write(bs);
                ImageIO.write(targetImg, "png", fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return file.getName();
        }
        return null;
    }

    public static File openFile(JFrame jf) {
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();
        //后缀名过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter("画图小程序工程文件(*.png)", "png");
        chooser.setFileFilter(filter);
        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().endsWith(".png")) {
                // 消息对话框无返回, 仅做通知作用
                JOptionPane.showMessageDialog(
                        jf,
                        "不是工程文件,不能打开",
                        "错误",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            return file;
        }
        return null;
    }
}
