package com.demo.gui;

import com.demo.util.Context;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
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

    private BufferedImage targetImg;

    private Graphics2D g;

    private String filePath;

    private int drawlineCount;
    Toolkit tool = this.getToolkit();

    DrawingBoard() {
        DefaultMouseListener defaultMouseListener = new DefaultMouseListener();
        this.addMouseListener(defaultMouseListener);
        this.addMouseMotionListener(defaultMouseListener);
        Self = this;
        myTabIndex = tabIndex.addAndGet(1);
        targetImg = new BufferedImage(900, 600, BufferedImage.TYPE_INT_ARGB);
        g = targetImg.createGraphics();
        this.setBackground(Color.lightGray);
        g.setBackground(Color.lightGray);
    }

    public void paint(Graphics graphics) {
        // 1.调用父类完成初始化任务
        super.paint(graphics);
        if (Objects.nonNull(filePath) && !filePath.isEmpty()) {
            paint4Img();
        }
        doPaint();
    }

    private void paint4Img(){
        Image image = tool.getImage(filePath);
        getGraphics().drawImage(image, 0, 0, image.getWidth(this),image.getHeight(this), this);
        Image image2 = tool.getImage(filePath);
        g.drawImage(image2, 0, 0, image2.getWidth(Self),image2.getHeight(Self), Self);
    }

    private void doPaint(){
        getGraphics().drawImage(targetImg, 0, 0, targetImg.getWidth(this),targetImg.getHeight(this), this);
    }


    class DefaultMouseListener implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (Context.getInstance().isLine()&&!Context.getInstance().isCls() ) {
                if (drawlineCount>0) {
                    g.setColor(Context.getInstance().getCurrentColor());
                    g.drawLine(sX, sY, e.getX(), e.getY());
                    doPaint();
                }
                sX = e.getX();
                sY = e.getY();
                drawlineCount++;
                return;
            }

            sX = e.getX();
            sY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Graphics graphics = getGraphics();
            Color color = Context.getInstance().getCurrentColor();
            graphics.setColor(color);
            eX = e.getX();
            eY = e.getY();
            if (Context.getInstance().isCls()) {
                g.clearRect(sX, sY, eX, eY);
                doPaint();
                return;
            }
            int currentChart = Context.getInstance().getCurrentChart();
            if ((currentChart & Context.CHART_CIRCLE) > 0) {
                g.setColor(color);
                g.drawOval(sX, sY, eX, eY);
                doPaint();
            }
            if ((currentChart & Context.CHART_REC) > 0) {
                g.setColor(color);
                g.drawRect(sX, sY, eX, eY);
                doPaint();
            }
            if ((currentChart & Context.CHART_FULL_CIRCLE) > 0) {
                g.setColor(color);
                g.fillOval(sX, sY, eX, eY);
                doPaint();
            }
            if ((currentChart & Context.CHART_FULL_REC) > 0) {
                g.setColor(color);
                g.fillRect(sX, sY, eX, eY);
                doPaint();
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
            if (Context.getInstance().isPan()) {
                eX = e.getX();
                eY = e.getY();
                g.setColor(Context.getInstance().getCurrentColor());
                g.drawLine(sX, sY, eX, eY);
                sX = e.getX();
                sY = e.getY();
//                repaint();
                doPaint();
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

    public BufferedImage getTargetImg() {
        return targetImg;
    }

    public void setTargetImg(BufferedImage targetImg) {
        this.targetImg = targetImg;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getDrawlineCount() {
        return drawlineCount;
    }

    public void setDrawlineCount(int drawlineCount) {
        this.drawlineCount = drawlineCount;
    }
}
