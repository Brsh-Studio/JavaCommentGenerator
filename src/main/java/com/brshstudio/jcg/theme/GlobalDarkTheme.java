package com.brshstudio.jcg.theme;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import lombok.SneakyThrows;
import javax.swing.*;
import java.awt.*;

/**
 * 全局轻主题类
 */
public class GlobalDarkTheme {

    /**
     * 主题设置
     */
    @SneakyThrows
    private static void themeSetting() {
        // 配置主题
        FlatDarkLaf.setup();
        UIManager.put("MenuBar.background", new Color(0, 0, 0, 0));
    }

    /**
     * 更新主题
     */
    public static void updateTheme() {
        themeSetting();
        FlatLaf.updateUI();
    }
}
