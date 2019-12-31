package com.demo.gui;

import com.demo.entity.*;
import com.demo.entity.Rectangle;
import com.demo.util.Context;

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
            if (!Context.getInstance().isCls() && sX!= e.getX() && sY !=e.getY() && sX!=0 && sY!=0) {
                Graphics graphics = getGraphics();
                Color color = Context.getInstance().getCurrentColor();
                graphics.setColor(color);
                Context.getInstance().addChart(new Line(sX, sY, e.getX(), e.getY(),color));
                graphics.drawLine(sX, sY, e.getX(), e.getY());
            }
            sX = e.getX();
            sY = e.getY();
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            sX = e.getX();
//            sY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Graphics graphics = getGraphics();
            Color color = Context.getInstance().getCurrentColor();
            graphics.setColor(color);
            eX = e.getX();
            eY = e.getY();
            if (Context.getInstance().isCls()) {
                graphics.clearRect(sX, sY, eX, eY);
                Context.getInstance().addChart(new Clear(sX, sY, eX, eY));
                return;
            }
            int currentChart = Context.getInstance().getCurrentChart();
            if ((currentChart & Context.CHART_CIRCLE) > 0) {
                Context.getInstance().addChart(new Circle(sX, sY, eX, eY,color));
                graphics.drawOval(sX, sY, eX, eY);
            }
            if ((currentChart & Context.CHART_LINE) > 0) {
                return;
            }
            if ((currentChart & Context.CHART_REC) > 0) {
                Context.getInstance().addChart(new Rectangle(sX, sY, eX, eY,color));
                graphics.drawRect(sX, sX, eX, eY);
            }
            if ((currentChart & Context.CHART_FULL_CIRCLE) > 0) {
                Context.getInstance().addChart(new CircleFull(sX, sY, eX, eY,color));
                graphics.fillOval(sX, sX, eX, eY);
            }
            if ((currentChart & Context.CHART_FULL_REC) > 0) {
                Context.getInstance().addChart(new RectangleFull(sX, sY, eX, eY,color));
                graphics.fillRect(sX, sX, eX, eY);
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
            if (Context.getInstance().isCls()) {
                return;
            }


            if ((Context.getInstance().getCurrentChart() & Context.CHART_PAN) > 0) {
                eX = e.getX();
                eY = e.getY();
                Graphics graphics = getGraphics();
                graphics.setColor(Context.getInstance().getCurrentColor());
                graphics.drawLine(sX, sY, eX, eY);
                sX = e.getX();
                sY = e.getY();
            }

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


    public int getsX() {
        return sX;
    }

    public void setsX(int sX) {
        this.sX = sX;
    }

    public int getsY() {
        return sY;
    }

    public void setsY(int sY) {
        this.sY = sY;
    }

    public int geteX() {
        return eX;
    }

    public void seteX(int eX) {
        this.eX = eX;
    }

    public int geteY() {
        return eY;
    }

    public void seteY(int eY) {
        this.eY = eY;
    }
}
