package com.brshstudio.jcg.ui.setting.panels;

import com.brshstudio.jcg.enums.SystemLanguage;
import com.brshstudio.jcg.i18n.jcomponents.I18nCheckBox;
import com.brshstudio.jcg.i18n.jcomponents.I18nComboBox;
import com.brshstudio.jcg.i18n.jcomponents.I18nPanel;
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
    private final I18nComboBox<String> themeComboBox = new I18nComboBox<>();

    /**
     * 语言选择框
     */
    private final I18nComboBox<String> languageComboBox = new I18nComboBox<>();

    /**
     * 是否覆盖注释
     */
    private final I18nCheckBox overrideComment = new I18nCheckBox("是否覆盖注释");

    /**
     * 其他设置面板
     */
    private I18nPanel otherPanel;

    /**
     * 主题面板
     */
    private I18nPanel themePanel;

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
        languageConfig(settingLayout, that);
        themeConfig(settingLayout, that);
        otherPanel(settingLayout, that);
    }

    /**
     * 配置语言设置
     */
    private void languageConfig(SettingLayout settingLayout, AbstractSettingsPanel that) {
        SettingsLayoutUtil.addSectionTitle(that, "语言设置");
        languageComboBox.setModel(new DefaultComboBoxModel<>(SystemLanguage.getAllLanguage()));
        languageComboBox.setSelectedItem(JCGSetting.JCG_SETTING_SYSTEM_LANGUAGE.getDescription());
        SettingsLayoutUtil.addLabeledComponent(that, "语言设置", languageComboBox);
    }

    /**
     * 设置面板布局和实例
     */
    private void otherPanel(SettingLayout settingLayout, AbstractSettingsPanel that) {
        if (otherPanel == null) {
            overrideComment.setSelected(JCGSetting.JCG_SETTING_IS_OVERWRITE);
            otherPanel = new I18nPanel(new GridBagLayout());
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
            themePanel = new I18nPanel(new GridBagLayout());
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
        JCGSetting.JCG_SETTING_SYSTEM_LANGUAGE = SystemLanguage.fromDescription(languageComboBox.getSelectedItem().toString());
    }
}
