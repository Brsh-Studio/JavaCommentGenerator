package com.brshstudio.jcg.theme;

import com.brshstudio.jcg.MainApplication;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import lombok.SneakyThrows;
import javax.swing.*;
import java.awt.*;

/**
 * 全局轻主题类
 */
public class GlobalLightTheme {

    /**
     * 主题设置
     */
    @SneakyThrows
    private static void themeSetting() {
        // 配置主题
        FlatLightLaf.setup();
        // 设置JMenuBar背景色为透明
        UIManager.put("MenuBar.background", new Color(0, 0, 0, 0));
        // 设置JTree背景色为透明
        //        UIManager.put("Tree.background", new Color(255, 255, 255, 1));
    }

    /**
     * 更新主题
     */
    public static void updateTheme() {
        if (MainApplication.gui != null) {
            themeSetting();
            FlatLaf.updateUI();
        }
    }
}
