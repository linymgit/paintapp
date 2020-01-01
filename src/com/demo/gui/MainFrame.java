package com.demo.gui;

import com.demo.util.Context;
import com.demo.util.FileUtils;
import com.demo.util.PathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
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
    JMenuItem newFile, saveFile, openFile, closeFile, helpDoc, undo;
    JScrollPane js;

    /**
     * 选项卡
     */
    JTabbedPane tp;

    JButton recBtn, recFBtn, ellBtn, ellFBtn, lineBtn, clsBtn, panBtn, colorBtn;

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

        recFBtn = new JButton();
        recFBtn.setToolTipText("实心矩形");
        recFBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "rec.png"));
        recFBtn.addActionListener(this);

        ellBtn = new JButton();
        ellBtn.setToolTipText("椭圆");
        ellBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "ellipse.png"));
        ellBtn.addActionListener(this);

        ellFBtn = new JButton();
        ellFBtn.setToolTipText("实心椭圆");
        ellFBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "ell.png"));
        ellFBtn.addActionListener(this);


        lineBtn = new JButton();
        lineBtn.setToolTipText("直线");
        lineBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "line.png"));
        lineBtn.addActionListener(this);

        clsBtn = new JButton();
        clsBtn.setToolTipText("橡皮擦");
        clsBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "eraser.png"));
        clsBtn.addActionListener(this);

        panBtn = new JButton();
        panBtn.setToolTipText("画笔");
        panBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "pan.png"));
        panBtn.addActionListener(this);

        colorBtn = new JButton();
        colorBtn.setToolTipText("颜色");
        colorBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "color.png"));
        colorBtn.addActionListener(this);

        // 添加 按钮 到 工具栏
        tb.add(recBtn);
        tb.add(recFBtn);
        tb.add(ellBtn);
        tb.add(ellFBtn);
        tb.add(lineBtn);
        tb.add(clsBtn);
        tb.add(panBtn);
        tb.add(colorBtn);
        tb.setOrientation(SwingConstants.VERTICAL);


        mb.add(file);
        mb.add(edit);
        mb.add(help);

        f.setJMenuBar(mb);
        f.setLayout(new BorderLayout(10, 5));


        f.add(tb, BorderLayout.WEST);

        tp = new JTabbedPane();
        tp.addChangeListener(e -> {
            Context.getInstance().setTabIndex(tp.getSelectedIndex());
        });
        tp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        f.add(tp, BorderLayout.CENTER);


        f.setTitle("画图小程序");
        f.setIconImage(new ImageIcon(PathUtils.getIconImgPath()).getImage());

        f.setSize(900, 600);
        f.setLocationRelativeTo(null);
        f.setVisible(true);


        DrawingBoard drawingBoard = new DrawingBoard();
        AtomicInteger tabIndex = DrawingBoard.tabIndex;
        tp.add("未命名" + tabIndex.get(), drawingBoard);
        tp.setSelectedIndex(tp.getTabCount() - 1);
        Context.getInstance().setTabIndex(tabIndex.get());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(helpDoc)) {
            HelpDialog.show(this.f, this.tp);
        }
        if (e.getSource().equals(newFile)) {
            DrawingBoard drawingBoard = new DrawingBoard();
            AtomicInteger tabIndex = DrawingBoard.tabIndex;
            tp.add("未命名" + tabIndex.get(), drawingBoard);
            tp.setSelectedIndex(tp.getTabCount() - 1);
            Context.getInstance().setTabIndex(tabIndex.get());
        }
        if (e.getSource().equals(closeFile)) {
            if (tp.getTabCount() <= 0) {
                return;
            }
            int selectedIndex = tp.getSelectedIndex();
            tp.remove(selectedIndex);
        }
        if (e.getSource().equals(openFile)) {
            File file = FileUtils.openFile(this.f);
            if (Objects.nonNull(file)) {
                DrawingBoard drawingBoard = new DrawingBoard();
                drawingBoard.setFilePath(file.getPath());
                tp.add(file.getName(), drawingBoard);
                int i = tp.getTabCount() - 1;
                tp.setSelectedIndex(i);
            }
        }
        if (e.getSource().equals(saveFile)) {
            DrawingBoard drawingBoard=(DrawingBoard) tp.getSelectedComponent();
            String s = FileUtils.saveFile(drawingBoard.getTargetImg());
            if (Objects.nonNull(s)) {
                tp.setTitleAt(tp.getSelectedIndex(), s);
            }
        }
        if (e.getSource().equals(lineBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_LINE);
            DrawingBoard drawingBoard =(DrawingBoard) tp.getSelectedComponent();
            drawingBoard.setDrawlineCount(0);
        }
        if (e.getSource().equals(ellBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_CIRCLE);
        }
        if (e.getSource().equals(ellFBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_FULL_CIRCLE);
        }
        if (e.getSource().equals(recBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_REC);
        }
        if (e.getSource().equals(recFBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_FULL_REC);
        }
        if (e.getSource().equals(panBtn)) {
            Context.getInstance().setCurrentChart(Context.CHART_PAN);
        }
        if (e.getSource().equals(clsBtn)) {
            Context.getInstance().setCls(true);
        }
        if (e.getSource().equals(undo)) {
            Context.getInstance().undoV2();
            tp.getSelectedComponent().repaint();
        }
        if (e.getSource().equals(colorBtn)) {
            Color color = JColorChooser.showDialog(f, "选取颜色", null);
            // 如果用户取消或关闭窗口, 则返回的 color 为 null
            if (color == null) {
                return;
            }
            Context.getInstance().setCurrentColor(color);
        }
        if (Objects.nonNull(tp.getSelectedComponent())) {
            if (Context.getInstance().isCls()) {
                String fileName = PathUtils.getResourcePath() + "eraser.png";
                Toolkit tk = Toolkit.getDefaultToolkit();
                Image img = tk.getImage(fileName);
                Cursor cu = tk.createCustomCursor(img, new Point(1, 1), "clear");
                tp.getSelectedComponent().setCursor(cu);
            } else {
                tp.getSelectedComponent().setCursor(Cursor.getDefaultCursor());
            }
        }

    }

}

