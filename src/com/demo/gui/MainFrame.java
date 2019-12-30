package com.demo.gui;

import com.demo.util.Context;
import com.demo.util.PathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author lym
 * @Date 2019/12/30 10:54
 * @qq 1317113287
 * @Description 主界面
 */
public class MainFrame implements ActionListener {
    JFrame f;
    /**
     * 菜单栏
     */
    JMenuBar mb;
    /**
     * 工具栏
     */
    JToolBar tb;
    /**
     * 主菜单
     */
    JMenu file, edit, help;
    /**
     * 菜单选项
     */
    JMenuItem cut, copy, paste, selectAll;
    JMenuItem newFile, saveFile, openFile;
    JScrollPane js;

    /**
     * 选项卡
     */
    JTabbedPane tp;

    JButton recBtn, ellBtn, lineBtn, clsBtn;

    public MainFrame() {
        f = new JFrame();
        js = new JScrollPane();
        mb = new JMenuBar();
        tb = new JToolBar("工具栏");

        newFile = new JMenuItem("创建");
        saveFile = new JMenuItem("保存");
        openFile = new JMenuItem("打开");

        newFile.addActionListener(this);
        saveFile.addActionListener(this);
        openFile.addActionListener(this);


        file = new JMenu("文件");
        help = new JMenu("帮助");

        file.add(newFile);
        file.add(saveFile);
        file.add(openFile);

        // ---------------工具栏-----------------
        // 创建 工具栏按钮
        recBtn = new JButton();
        recBtn.setToolTipText("矩形");
        recBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "rectangle.png"));
        recBtn.addActionListener(this);

        ellBtn = new JButton();
        ellBtn.setToolTipText("椭圆");
        ellBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "ellipse.png"));
        ellBtn.addActionListener(this);

        lineBtn = new JButton("直线");
        lineBtn.setToolTipText("直线");
        lineBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "line.png"));
        lineBtn.addActionListener(this);

        clsBtn = new JButton("橡皮擦");
        clsBtn.setToolTipText("橡皮擦");
        clsBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "line.png"));
        clsBtn.addActionListener(this);

        // 添加 按钮 到 工具栏
        tb.add(recBtn);
        tb.add(ellBtn);
        tb.add(lineBtn);
        tb.add(clsBtn);
        tb.setOrientation(SwingConstants.VERTICAL);


        mb.add(file);
        mb.add(help);

        f.setJMenuBar(mb);
        f.setLayout(new BorderLayout(10, 5));


        f.add(tb, BorderLayout.WEST);
//        f.add(ta);

        tp = new JTabbedPane();
        tp.addChangeListener(e -> {
            Context.getInstance().setTabIndex(tp.getSelectedIndex());
        });
        tp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


//        DrawingBoard drawingBoard = new DrawingBoard();
//        tp.add("未命名",drawingBoard );
//        drawingBoard.setBackground();
        f.add(tp, BorderLayout.CENTER);


        f.setTitle("画图小程序");
        f.setIconImage(new ImageIcon(PathUtils.getIconImgPath()).getImage());

        f.setSize(900, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(newFile)) {
            DrawingBoard drawingBoard = new DrawingBoard();
            tp.add("未命名"+tp.getTabCount(), drawingBoard);
            f.add(tp, BorderLayout.CENTER);
            tp.setSelectedIndex(tp.getTabCount()-1);
        }
        if (e.getSource().equals(saveFile)) {
            int selectedIndex = tp.getSelectedIndex();
            Image image = tp.createImage(tp.getWidth(), tp.getHeight());
            tp.setSelectedIndex(2);
            tp.repaint();

        }
        if (e.getSource().equals(lineBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_LINE);
        }
        if (e.getSource().equals(ellBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_CIRCLE);
        }
        if (e.getSource().equals(recBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_REC);
        }
        if (e.getSource().equals(clsBtn)) {
            Context.getInstance().setCls(true);
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

