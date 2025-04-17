package com.brshstudio.jcg.ui.setting.panels.modelargs;

import com.brshstudio.jcg.ui.setting.panels.base.AbstractSettingsPanel;
import com.brshstudio.jcg.ui.setting.layout.SettingLayout;
import com.brshstudio.jcg.ui.setting.panels.ModelArgsSettingsPanel;
import com.brshstudio.jcg.utils.SettingsLayoutUtil;
import lombok.Getter;
import javax.swing.*;

/**
 * 参数设置面板
 */
@SuppressWarnings("unused")
public class ArgsSettingsPanel extends AbstractSettingsPanel {

    /**
     * 主面板
     */
    @Getter
    private final JScrollPane mainPanel;

    public ArgsSettingsPanel(SettingLayout settingLayout) {
        // 初始化主面板
        this.mainPanel = SettingsLayoutUtil.initContentPanel(this);
        // 初始化布局
        initComponents(settingLayout, this);
    }

    /**
     * 初始化组件
     */
    @Override
    protected void initComponents(SettingLayout settingLayout, AbstractSettingsPanel that) {
        settingLayout.getPanel(ModelArgsSettingsPanel.class).modelArgsPanel(settingLayout, that);
    }

    /**
     * 应用设置
     */
    @Override
    public void applySettings() {
        // 应用设置逻辑
    }
}
