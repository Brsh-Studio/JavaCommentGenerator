package com.brshstudio.jcg.ui.setting.panels;

import com.brshstudio.jcg.ui.setting.panels.base.AbstractSettingsPanel;
import com.brshstudio.jcg.enums.SystemTheme;
import com.brshstudio.jcg.resource.JCGSetting;
import com.brshstudio.jcg.ui.setting.layout.SettingLayout;
import com.brshstudio.jcg.utils.SettingsLayoutUtil;
import lombok.Getter;
import javax.swing.*;
import java.awt.*;

/**
 * 通用设置面板
 */
@SuppressWarnings("unused")
public class GeneralSettingsPanel extends AbstractSettingsPanel {

    /**
     * 主面板
     */
    @Getter
    private final JScrollPane mainPanel;

    /**
     * 主题选择框
     */
    private final JComboBox<String> themeComboBox = new JComboBox<>();

    /**
     * 是否覆盖注释
     */
    private final JCheckBox overrideComment = new JCheckBox("是否覆盖注释");

    /**
     * 其他设置面板
     */
    private JPanel otherPanel;

    /**
     * 主题面板
     */
    private JPanel themePanel;

    public GeneralSettingsPanel(SettingLayout settingLayout) {
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
        // 主题配置区域
        themeConfig(settingLayout, that);
        otherPanel(settingLayout, that);
    }

    /**
     * 设置面板布局和实例
     */
    private void otherPanel(SettingLayout settingLayout, AbstractSettingsPanel that) {
        if (otherPanel == null) {
            overrideComment.setSelected(JCGSetting.JCG_SETTING_IS_OVERWRITE);
            otherPanel = new JPanel(new GridBagLayout());
            SettingsLayoutUtil.addSectionTitle(otherPanel, "其他设置");
            SettingsLayoutUtil.addComponentRow(otherPanel, overrideComment);
        }
        SettingsLayoutUtil.addComponentRow(that, otherPanel);
    }

    /**
     * 配置主题布局
     */
    public void themeConfig(SettingLayout settingLayout, AbstractSettingsPanel that) {
        if (themePanel == null) {
            // 初始化主题下拉框
            themeComboBox.setModel(new DefaultComboBoxModel<>(SystemTheme.getAllThemeModel()));
            themeComboBox.setSelectedItem(JCGSetting.JCG_SETTING_THEME.getDescription());
            themePanel = new JPanel(new GridBagLayout());
            // 布局构建器
            SettingsLayoutUtil.addSectionTitle(themePanel, "主题设置");
            SettingsLayoutUtil.addLabeledComponent(themePanel, "系统主题", themeComboBox);
        }
        SettingsLayoutUtil.addComponentRow(that, themePanel);
    }

    /**
     * 应用设置
     */
    @Override
    public void applySettings() {
        JCGSetting.JCG_SETTING_THEME = SystemTheme.fromDescription(themeComboBox.getSelectedItem().toString());
        JCGSetting.JCG_SETTING_IS_OVERWRITE = overrideComment.isSelected();
    }
}
