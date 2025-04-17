package com.brshstudio.jcg.ui.setting.panels;

import com.brshstudio.jcg.ui.setting.panels.base.AbstractSettingsPanel;
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
public class LoggerSettingsPanel extends AbstractSettingsPanel {

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
    private final JCheckBox isAISaveOutputComment = new JCheckBox("是否保存输出注释");

    /**
     * 是否AI打印备注
     */
    private final JCheckBox isAIPrintComment = new JCheckBox("是否打印输出注释");

    /**
     * AI输出路径
     */
    private final JTextField aiOutputPath = new JTextField();

    /**
     * 其他设置面板
     */
    private JPanel loggerPanel;

    /**
     * 主题面板
     */
    private JPanel themePanel;

    public LoggerSettingsPanel(SettingLayout settingLayout) {
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
        loggerPanel(settingLayout, that);
    }

    /**
     * 日志面板
     */
    private void loggerPanel(SettingLayout settingLayout, AbstractSettingsPanel that) {
        if (loggerPanel == null) {
            isAISaveOutputComment.setSelected(JCGSetting.JCG_SETTING_IS_SAVE_AI_OUTPUT);
            isAIPrintComment.setSelected(JCGSetting.JCG_SETTING_IS_PRINT_AI_OUTPUT);
            aiOutputPath.setText(JCGSetting.JCG_SETTING_AI_OUTPUT_PATH);
            loggerPanel = new JPanel(new GridBagLayout());
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
            jPanel.add(isAISaveOutputComment);
            jPanel.add(isAIPrintComment);
            SettingsLayoutUtil.addSectionTitle(loggerPanel, "日志配置");
            SettingsLayoutUtil.addComponentRow(loggerPanel, jPanel);
            SettingsLayoutUtil.addLabeledComponent(loggerPanel, "AI 日志输出位置：", aiOutputPath);
        }
        SettingsLayoutUtil.addComponentRow(that, loggerPanel);
    }

    /**
     * 应用设置
     */
    @Override
    public void applySettings() {
        JCGSetting.JCG_SETTING_IS_SAVE_AI_OUTPUT = isAISaveOutputComment.isSelected();
        JCGSetting.JCG_SETTING_IS_PRINT_AI_OUTPUT = isAIPrintComment.isSelected();
        JCGSetting.JCG_SETTING_AI_OUTPUT_PATH = aiOutputPath.getText();
    }
}
