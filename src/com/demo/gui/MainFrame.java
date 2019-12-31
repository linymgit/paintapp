package com.demo.gui;

import com.demo.util.Context;
import com.demo.util.FileUtils;
import com.demo.util.PathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
    JMenuItem newFile, saveFile, openFile,closeFile, helpDoc,undo;
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
        closeFile = new JMenuItem("关闭当前文件");

        helpDoc = new JMenuItem("说明文档");

        undo = new JMenuItem("撤回");

        newFile.addActionListener(this);
        saveFile.addActionListener(this);
        openFile.addActionListener(this);
        closeFile.addActionListener(this);
        helpDoc.addActionListener(this);
        undo.addActionListener(this);



        file = new JMenu("文件");
        edit = new JMenu("编辑");
        help = new JMenu("帮助");

        file.add(newFile);
        file.add(saveFile);
        file.add(openFile);
        file.add(closeFile);

        help.add(helpDoc);

        edit.add(undo);

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
        mb.add(edit);
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


        DrawingBoard drawingBoard = new DrawingBoard();
        AtomicInteger tabIndex = DrawingBoard.tabIndex;
        tp.add("未命名"+tabIndex.get(), drawingBoard);
        tp.setSelectedIndex(tp.getTabCount()-1);
        Context.getInstance().setTabIndex(tabIndex.get());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(helpDoc)) {
            HelpDialog.show(this.f, this.tp);
        }
        if (e.getSource().equals(newFile)) {
            DrawingBoard drawingBoard = new DrawingBoard();
            AtomicInteger tabIndex = DrawingBoard.tabIndex;
            tp.add("未命名"+tabIndex.get(), drawingBoard);
            tp.setSelectedIndex(tp.getTabCount()-1);
            Context.getInstance().setTabIndex(tabIndex.get());
        }
        if (e.getSource().equals(closeFile)) {
            if (tp.getTabCount()<=0) {
                return;
            }
            int selectedIndex = tp.getSelectedIndex();
            tp.remove(selectedIndex);
        }
        if (e.getSource().equals(openFile)) {
            FileUtils.OpenFileInfo openFileInfo = FileUtils.openFile(this.f);
            if (Objects.nonNull(openFileInfo)) {
                DrawingBoard drawingBoard = new DrawingBoard();
                tp.add(openFileInfo.getFileName(), drawingBoard);
                int i = tp.getTabCount()- 1;
                tp.setSelectedIndex(i);
                Context.getInstance().putCharts(drawingBoard.getMyTabIndex(), openFileInfo.getCharts());
            }
        }
        if (e.getSource().equals(saveFile)) {
            final BufferedImage targetImg = new BufferedImage(900, 600, BufferedImage.TYPE_INT_RGB);
            final Graphics2D g2d = targetImg.createGraphics();
            Context.getInstance().paint(g2d);
            String s = FileUtils.saveFile(targetImg, Context.getInstance().getCurrentCharts());
            if (Objects.nonNull(s)) {
                tp.setTitleAt(tp.getSelectedIndex(), s);
            }
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
        if (e.getSource().equals(undo)) {
            Context.getInstance().undo();
            tp.getSelectedComponent().repaint();
        }
    }

//    public static void main(String[] args) {
//        new MainFrame();
//    }
}

