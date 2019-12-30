package com.demo.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author lym
 * @Date 2019/12/30 11:55
 * @qq 1317113287
 * @Description 画板
 */
public class DrawingBoard extends Panel {
    public static final int RectangleType = 1;
    public static final int EllipseType = 2;

    //类型
    private int drawType;

    private DrawingBoard Self;

    private int sX,sY,eX,eY;

    DrawingBoard(){
        DefaultMouseListener defaultMouseListener = new DefaultMouseListener();
        this.addMouseListener(defaultMouseListener);
        this.addMouseMotionListener(defaultMouseListener);
        Self = this;
    }
//
    public void paint(Graphics graphics) {
        // 1.调用父类完成初始化任务
        super.paint(graphics);
        // 简单的画一个圆圈 使用该方法drawOval 参数为 x 坐标 y 坐标 宽度 高度 单位都是像素
        // x 坐标和 y 坐标 为距离我们GUI界面左上角的位置的像素
//        graphics.drawOval(10, 10, 30, 30);
//        graphics.draw3DRect(50, 50, 50, 50, true);
    }

    class DefaultMouseListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(e.getX());
            System.out.println(e.getY());

            sX = e.getX();
            sY = e.getY();
        }

        @Override
        public void mousePressed(MouseEvent e) {


        }

        @Override
        public void mouseReleased(MouseEvent e) {
            eX = e.getX();
            eY = e.getY();
            Self.getGraphics().drawLine(sX,sY,eX,eY);
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

}
