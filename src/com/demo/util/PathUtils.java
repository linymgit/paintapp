package com.demo.util;

/**
 * @author lym
 * @Date 2019/12/21 20:56
 * @qq 1317113287
 * @Description 路径工具类
 */
public class PathUtils {

    private static String clsPath;

    static  {
        clsPath =  PathUtils.class.getResource("/").getPath();
    }

    public static String getIconImgPath() {
        return clsPath+"resource/icon.jpg";
    }

    public static String getResourcePath() {
        return clsPath+"resource/";
    }
}
