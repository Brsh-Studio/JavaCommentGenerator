package com.brshstudio.jcg.ui.setting.panels.base;

import com.brshstudio.jcg.i18n.jcomponents.I18nPanel;
import com.brshstudio.jcg.ui.setting.layout.SettingLayout;
import javax.swing.*;

/**
 * 抽象设置面板
 */
public abstract class AbstractSettingsPanel extends I18nPanel {

    /**
     * 初始化组件
     */
    protected abstract void initComponents(SettingLayout settingLayout, AbstractSettingsPanel that);

    /**
     * 应用设置
     */
    public abstract void applySettings();

    /**
     * 获取主面板
     */
    public abstract JComponent getMainPanel();

    /**
     * 重新被激活
     */
    public void activated(SettingLayout settingLayout, AbstractSettingsPanel that) {
        that.removeAll();
        initComponents(settingLayout, that);
    }
}
