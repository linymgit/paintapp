package com.demo.entity;

import java.awt.*;
import java.io.Serializable;

/**
 * @auth linyimin
 * @QQ: 1317113287
 * @desc: 矩形
 **/
public class RectangleFull implements Chart, Serializable {
    private int sX;
    private int sY;
    private int eX;
    private int eY;
    private Color color;

    public RectangleFull(int sX, int sY, int eX, int eY,Color color) {
        this.sX = sX;
        this.sY = sY;
        this.eX = eX;
        this.eY = eY;
        this.color = color;
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
        graphics.setColor(color);
        graphics.fillRect(sX, sX, Math.abs(eX-sX), Math.abs(eY-sY));
    }
}
