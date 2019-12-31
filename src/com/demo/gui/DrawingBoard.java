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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lym
 * @Date 2019/12/30 11:55
 * @qq 1317113287
 * @Description 画板
 */
public class DrawingBoard extends Panel {

    public static AtomicInteger tabIndex = new AtomicInteger(0);

    private int myTabIndex;

    private DrawingBoard Self;

    private int sX, sY, eX, eY;

    DrawingBoard() {
        DefaultMouseListener defaultMouseListener = new DefaultMouseListener();
        this.addMouseListener(defaultMouseListener);
        this.addMouseMotionListener(defaultMouseListener);
        Self = this;
        myTabIndex = tabIndex.addAndGet(1);
    }

    public void paint(Graphics graphics) {
        // 1.调用父类完成初始化任务
        super.paint(graphics);
        Context.getInstance().setTabIndex(myTabIndex);
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
            if ((currentChart & Context.CHART_CIRCLE) > 0) {
                Context.getInstance().addChart(new Circle(sX, sY, eX, eY));
                getGraphics().drawOval(sX, sY, eX, eY);
            }
            if ((currentChart & Context.CHART_LINE) > 0) {
                Context.getInstance().addChart(new Line(sX, sY, eX, eY));
                getGraphics().drawLine(sX, sY, eX, eY);
            }
            if ((currentChart & Context.CHART_REC) > 0) {
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
//            eX = e.getX();
//            eY = e.getY();
//            if ((Context.getInstance().getCurrentChart() & Context.CHART_REC)>0) {
//                getGraphics().drawRect(sX, sX, eX, eY);
//            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    public int getMyTabIndex() {
        return myTabIndex;
    }

    public void setMyTabIndex(int myTabIndex) {
        this.myTabIndex = myTabIndex;
    }
}
