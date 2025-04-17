package com.brshstudio.jcg;

import com.brshstudio.jcg.ui.JCGUI;
import com.brshstudio.jcg.resource.JCGSetting;

import javax.swing.*;

/**
 * 主应用程序类
 */
public class MainApplication {

    /**
     * 界面标识
     */
    public static JCGUI gui;

    // 在 main 方法中调用
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JCGSetting.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            gui = new JCGUI();
            JCGSetting.reloadTheme();
            // 显示
            gui.setVisible(true);
            // 居中
            gui.setLocationRelativeTo(null);
        });
    }
}
