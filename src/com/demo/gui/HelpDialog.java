package com.demo.gui;

import com.demo.util.PathUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author lym
 * @Date 2019/12/31 9:21
 * @qq 1317113287
 * @Description 帮助对话框
 */
public class HelpDialog {

    /**
     * 显示一个自定义的对话框
     *
     * @param owner 对话框的拥有者
     * @param parentComponent 对话框的父级组件
     */
     public static void show(JFrame owner, JComponent parentComponent) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "画图小程序使用教程", true);
        dialog.setIconImage(new ImageIcon(PathUtils.getIconImgPath()).getImage());
        // 设置对话框的宽高
        dialog.setSize(600, 400);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        // 创建一个标签显示消息内容
        JLabel l1 = new JLabel("1.新建或者打开文件");
        JLabel l2 = new JLabel("2.选择工具栏的图案进行绘制");
        JLabel l3 = new JLabel("3.保存文件");


        // 创建对话框的内容面板, 在面板内可以根据自己的需要添加任何组件并做任意是布局
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4,1,10,5));

        // 添加组件到面板
        panel.add(l1);
        panel.add(l2);
        panel.add(l3);

        // 设置对话框的内容面板
        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);
    }

}
