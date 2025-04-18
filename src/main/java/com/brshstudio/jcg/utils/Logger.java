package com.brshstudio.jcg.utils;

import com.brshstudio.jcg.MainApplication;
import com.brshstudio.jcg.i18n.I18nUtil;

/**
 * 日志记录器接口
 */
public class Logger {

    /**
     * 打印一行日志信息
     *
     * @param message 信息
     */
    public static void printLineLogger(String message) {
        MainApplication.gui.getLogArea().append(I18nUtil.get(message) + "\n");
    }

    /**
     * 打印日志信息
     *
     * @param message 信息
     */
    public static void printLogger(String message) {
        MainApplication.gui.getLogArea().append(I18nUtil.get(message));
    }

    /**
     * 清空日志
     */
    public static void clearLogger() {
        MainApplication.gui.getLogArea().setText("");
    }
}
