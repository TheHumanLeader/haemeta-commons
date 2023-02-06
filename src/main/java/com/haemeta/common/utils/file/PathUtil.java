package com.haemeta.common.utils.file;

import java.io.File;

/**
 * 在windows和linux系统下均可正常使用
 * Create by yster@foxmail.com 2018/6/6/006 14:51
 */
public class PathUtil {

    /**
     * CLASS PATH 项目根目录
     */
    public final static String classPath;

    /**
     * 用这个，写项目用这个就完事了
     */
    public final static String ROOT_LOCATION;

    static {
        //获取的是classpath路径，适用于读取resources下资源
        classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File root = new File(System.getProperty("user.dir") + File.separator);
        ROOT_LOCATION = root.toString();
    }

    /**
     * 项目根目录
     */
    public static String getRootPath() {
        return RootPath("");
    }

    /**
     * 自定义追加路径
     */
    public static String getRootPath(String u_path) {
        return RootPath("/" + u_path);
    }

    /**
     * 私有处理方法
     */
    private static String RootPath(String u_path) {
        String rootPath = "";
        //windows下
        if ("\\".equals(File.separator)) {
            //System.out.println(classPath);
            rootPath = classPath + u_path;
            rootPath = rootPath.replaceAll("/", "\\\\");
            if (rootPath.substring(0, 1).equals("\\")) {
                rootPath = rootPath.substring(1);
            }
        }
        //linux下
        if ("/".equals(File.separator)) {
            //System.out.println(classPath);
            rootPath = classPath + u_path;
            rootPath = rootPath.replaceAll("\\\\", "/");
        }
        return rootPath;
    }

}
