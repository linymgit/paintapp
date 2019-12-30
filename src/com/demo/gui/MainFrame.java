package com.demo.gui;

import com.demo.util.PathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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

    Map<Integer, DrawingBoard> okMap = new HashMap<>();

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
        JButton recBtn = new JButton();
        recBtn.setToolTipText("矩形");
        recBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "rectangle.png"));

        JButton ellBtn = new JButton();
        ellBtn.setToolTipText("椭圆");
        ellBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "ellipse.png"));

        JButton lineBtn = new JButton();
        lineBtn.setToolTipText("直线");
        lineBtn.setIcon(new ImageIcon(PathUtils.getResourcePath() + "line.png"));

        // 添加 按钮 到 工具栏
        tb.add(recBtn);
        tb.add(ellBtn);
//        tb.add(lineBtn);
        tb.setOrientation(SwingConstants.VERTICAL);


        mb.add(file);
        mb.add(help);

        f.setJMenuBar(mb);
        f.setLayout(new BorderLayout(10, 5));


        f.add(tb, BorderLayout.WEST);
//        f.add(ta);

        tp = new JTabbedPane();
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
            tp.add("未命名", drawingBoard);
            okMap.put(tp.getTabCount(), drawingBoard);
            f.add(tp, BorderLayout.CENTER);
        }
        if (e.getSource().equals(saveFile)) {
            int selectedIndex = tp.getSelectedIndex();
            Image image = tp.createImage(tp.getWidth(), tp.getHeight());
            tp.setSelectedIndex(2);
            okMap.get(tp.getSelectedIndex()).getGraphics().drawImage(image,0, 0, null);
            tp.repaint();

        }

    }

    public static void main(String[] args) {
        new MainFrame();
    }
}

