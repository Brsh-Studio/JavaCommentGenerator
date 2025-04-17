package com.brshstudio.jcg.ui.setting.panels.general;

import com.brshstudio.jcg.ui.setting.panels.base.AbstractSettingsPanel;
import com.brshstudio.jcg.ui.setting.layout.SettingLayout;
import com.brshstudio.jcg.ui.setting.panels.GeneralSettingsPanel;
import com.brshstudio.jcg.utils.SettingsLayoutUtil;
import lombok.Getter;
import javax.swing.*;

/**
 * 主题设置面板
 */
@SuppressWarnings("unused")
public class ThemeSettingsPanel extends AbstractSettingsPanel {

    /**
     * 主面板
     */
    @Getter
    private final JScrollPane mainPanel;

    public ThemeSettingsPanel(SettingLayout settingLayout) {
        // 初始化内容面板
        this.mainPanel = SettingsLayoutUtil.initContentPanel(this);
        // 初始化组件
        initComponents(settingLayout, this);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initComponents(SettingLayout settingLayout, AbstractSettingsPanel that) {
        // 继承父类主题配置区域
        // 获取父项Panel
        AbstractSettingsPanel panel = settingLayout.getPanel(GeneralSettingsPanel.class);
        if (panel instanceof GeneralSettingsPanel general) {
            general.themeConfig(settingLayout, that);
        }
    }

    /**
     * 应用设置
     */
    @Override
    public void applySettings() {
        // 应用设置逻辑
        // 如果是调用的父组件面板，则无需实现应用
    }
}
