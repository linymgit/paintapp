package com.demo.util;

import com.demo.entity.Chart;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @author lym
 * @Date 2019/12/31 8:48
 * @qq 1317113287
 * @Description 文件工具类
 */
public class FileUtils {

    public static String saveFile(BufferedImage targetImg, List<Chart> charts) {
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();
        //后缀名过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter("图片文件(*.jpeg)", "jpeg");
        chooser.setFileFilter(filter);
        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {    //假如用户选择了保存
            File file = chooser.getSelectedFile();
            String fname = chooser.getName(file);    //从文件名输入框中获取文件名
            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if (!fname.contains(".jpeg")) {
                file = new File(chooser.getCurrentDirectory(), fname + ".jpeg");
            }
            File projFile = new File(file.getPath().substring(0, file.getPath().lastIndexOf(".")) + ".proj");
            try (
                    FileOutputStream fos = new FileOutputStream(file);

                    FileOutputStream fosProj = new FileOutputStream(projFile);

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fosProj)
            ) {
                //写文件操作……
//                fos.write(bs);
                ImageIO.write(targetImg, "JPEG", fos);
                objectOutputStream.writeObject(charts);
                return fname;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static OpenFileInfo openFile(JFrame jf) {
        //弹出文件选择框
        JFileChooser chooser = new JFileChooser();
        //后缀名过滤器
        FileNameExtensionFilter filter = new FileNameExtensionFilter("画图小程序工程文件(*.proj)", "proj");
        chooser.setFileFilter(filter);
        //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
        int option = chooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().endsWith(".proj")) {
                // 消息对话框无返回, 仅做通知作用
                JOptionPane.showMessageDialog(
                        jf,
                        "不是工程文件,不能打开",
                        "错误",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return new OpenFileInfo((List<Chart>) ois.readObject(),file.getName());
//                charts.forEach(chart -> System.out.println(chart));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static class OpenFileInfo{

        List<Chart> charts;
        String fileName;

        public OpenFileInfo(List<Chart> charts, String fileName) {
            this.charts = charts;
            this.fileName = fileName;
        }

        public List<Chart> getCharts() {
            return charts;
        }

        public void setCharts(List<Chart> charts) {
            this.charts = charts;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}
