package com.brshstudio.jcg;

import com.brshstudio.jcg.i18n.I18nUtil;
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
        // 根据系统默认语言初始化
        //        Locale defaultLocale = Locale.getDefault();
        SwingUtilities.invokeLater(() -> {
            try {
                JCGSetting.load();
                I18nUtil.init(JCGSetting.JCG_SETTING_SYSTEM_LANGUAGE.getLocale());
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
