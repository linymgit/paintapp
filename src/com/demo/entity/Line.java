package com.demo.entity;

import java.awt.*;

/**
 * @auth linyimin
 * @QQ: 1317113287
 * @desc: 直线
 **/
public class Line implements Chart {
    private int sX;
    private int sY;
    private int eX;
    private int eY;

    public Line(int sX, int sY, int eX, int eY) {
        this.sX = sX;
        this.sY = sY;
        this.eX = eX;
        this.eY = eY;
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

    @Override
    public void paint(Graphics graphics) {
        graphics.drawLine(sX, sX, eX, eY);
    }
}
