package com.demo.gui;

import com.demo.entity.Circle;
import com.demo.entity.Line;
import com.demo.entity.Rectangle;
import com.demo.util.Context;

import javax.swing.*;
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

        if (Context.getInstance().isCls()) {
            Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("ellipse.png").getImage(),new Point(10,20), "stick");
        }
        Context.getInstance().paint(graphics);


    }

    class DefaultMouseListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            sX = e.getX();
            sY = e.getY();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (Context.getInstance().isLine()) {
                return;
            }
            sX = e.getX();
            sY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            eX = e.getX();
            eY = e.getY();
            int currentChart = Context.getInstance().getCurrentChart();
            if ((currentChart & Context.CHART_CIRCLE)>0) {
                Context.getInstance().addChart(new Circle(sX, sY, eX, eY));
                getGraphics().drawOval(sX,sY, eX,eY);
            }
            if ((currentChart & Context.CHART_LINE)>0) {
                Context.getInstance().addChart(new Line(sX, sY, eX, eY));
                getGraphics().drawLine(sX,sY, eX,eY);
            }
            if ((currentChart & Context.CHART_REC)>0) {
                Context.getInstance().addChart(new Rectangle(sX, sY, eX, eY));
                getGraphics().drawRect(sX, sX, eX, eY);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            eX = e.getX();
            eY = e.getY();
            if ((Context.getInstance().getCurrentChart() & Context.CHART_REC)>0) {
                getGraphics().drawRect(sX, sX, eX, eY);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

}
