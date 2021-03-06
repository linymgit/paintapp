package com.demo.util;

import com.demo.entity.Chart;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auth linyimin
 * @QQ: 1317113287
 * @desc: 上下文
 **/
public class Context {
    public static final int CHART_LINE = 1;
    public static final int CHART_REC = 1 << 2;
    public static final int CHART_CIRCLE = 1 << 3;
    public static final int CHART_PAN = 1 << 4;
    public static final int CHART_FULL_CIRCLE = 1 << 5;
    public static final int CHART_FULL_REC = 1 << 6;

    /**
     * 当前选择的颜色
     */
    private Color currentColor;

    /**
     * 用户当前选择的图形
     */
    private int CurrentChart;

    /**
     * 选项卡下标
     */
    private int tabIndex;

    /**
     * 选中橡皮擦
     */
    private boolean isCls;

    private Map<Integer, List<Chart>> chartsMap = new ConcurrentHashMap<>();

    private Context() {
    }

    public static Context getInstance() {
        return SHelp.context;
    }

    static class SHelp {
        static Context context = new Context();
    }

    public void addChart(Chart chart) {
        if (Objects.isNull(chart)) {
            return;
        }
        List<Chart> charts = chartsMap.get(tabIndex);
        if (Objects.isNull(charts)) {
            charts = new ArrayList<>();
        }
        charts.add(chart);
        chartsMap.put(tabIndex, charts);
    }

    public void paint(Graphics graphics) {
        List<Chart> charts = chartsMap.get(tabIndex);
        if (Objects.isNull(charts)) {
            return;
        }
        charts.forEach(chart -> chart.paint(graphics));
    }

    public List<Chart> getCurrentCharts() {
        return chartsMap.get(tabIndex);
    }

    public void putCharts(Integer idx, List<Chart> charts) {
        chartsMap.put(idx, charts);
        setTabIndex(idx);
    }

    public void removeCharts(Integer idx) {
        chartsMap.remove(idx);
    }

    public void undo() {
        List<Chart> charts = chartsMap.get(tabIndex);
        if (Objects.isNull(charts)||charts.size() <= 0) {
            return;
        }
        charts.remove(charts.size() - 1);
    }

    public int getCurrentChart() {
        return CurrentChart;
    }

    public void setCurrentChart(int currentChart) {
        CurrentChart = currentChart;
        isCls = false;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public boolean isCircle() {
        return (getCurrentChart() & CHART_CIRCLE) > 0;
    }

    public boolean isLine() {
        return (getCurrentChart() & CHART_LINE) > 0;
    }

    public boolean isRect() {
        return (getCurrentChart() & CHART_REC) > 0;
    }

    public boolean isCls() {
        return isCls;
    }

    public void setCls(boolean cls) {
        isCls = cls;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }
}
