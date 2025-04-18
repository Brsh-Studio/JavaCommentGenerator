package com.brshstudio.jcg.ui.setting.panels;

import com.brshstudio.jcg.i18n.jcomponents.I18nButton;
import com.brshstudio.jcg.i18n.jcomponents.I18nPanel;
import com.brshstudio.jcg.i18n.jcomponents.I18nTitledBorder;
import com.brshstudio.jcg.ui.setting.panels.base.AbstractSettingsPanel;
import com.brshstudio.jcg.resource.JCGSetting;
import com.brshstudio.jcg.ui.setting.layout.SettingLayout;
import com.brshstudio.jcg.utils.SettingsLayoutUtil;
import lombok.Getter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 模型参数设置面板
 */
@SuppressWarnings("unused")
public class ModelArgsSettingsPanel extends AbstractSettingsPanel {

    /**
     * 主面板
     */
    @Getter
    private final JScrollPane mainPanel;

    /**
     * 提示面板
     */
    private I18nPanel promptPanel;

    /**
     * 参数面板
     */
    private I18nPanel argsPanel;

    /**
     * AI主机地址
     */
    private final JTextField aiHost = new JTextField();

    /**
     * AI模型名称
     */
    private final JTextField aiModel = new JTextField();

    /**
     * AI 请求最大并发数
     */
    private final JSpinner aiMaxConcurrent = new JSpinner();

    /**
     * AI 请求超时时间
     */
    private final JSpinner aiTimeout = new JSpinner();

    /**
     * 班级提示语
     */
    private final JTextArea classPrompt = new JTextArea(5, 20);

    /**
     * 方法提示
     */
    private final JTextArea methodPrompt = new JTextArea(5, 20);

    /**
     * 提示信息
     */
    private final JTextArea fieldPrompt = new JTextArea(5, 20);

    public ModelArgsSettingsPanel(SettingLayout settingLayout) {
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
        modelArgsPanel(settingLayout, that);
        promptPanel(settingLayout, that);
    }

    /**
     * 显示设置面板
     */
    public void promptPanel(SettingLayout settingLayout, AbstractSettingsPanel that) {
        if (promptPanel == null) {
            promptPanel = new I18nPanel(new GridBagLayout());
            SettingsLayoutUtil.addSectionTitle(promptPanel, "提示词设置");
            classPrompt.setText(JCGSetting.JCG_SETTING_CLASS_PROMPT);
            SettingsLayoutUtil.addLabeledComponent(promptPanel, "类提示词", setupPromptTextArea(classPrompt, JCGSetting.defaultClassPrompt, "第一个参数为类名"));
            methodPrompt.setText(JCGSetting.JCG_SETTING_METHOD_PROMPT);
            SettingsLayoutUtil.addLabeledComponent(promptPanel, "方法提示词", setupPromptTextArea(methodPrompt, JCGSetting.defaultMethodPrompt, "第一个参数为方法定义名"));
            fieldPrompt.setText(JCGSetting.JCG_SETTING_FIELD_PROMPT);
            SettingsLayoutUtil.addLabeledComponent(promptPanel, "字段提示词", setupPromptTextArea(fieldPrompt, JCGSetting.defaultFieldPrompt, "第一个参数为字段名"));
        }
        SettingsLayoutUtil.addComponentRow(that, promptPanel);
    }

    /**
     * 创建模型参数面板
     */
    public void modelArgsPanel(SettingLayout settingLayout, AbstractSettingsPanel that) {
        if (argsPanel == null) {
            argsPanel = new I18nPanel(new GridBagLayout());
            SettingsLayoutUtil.addSectionTitle(argsPanel, "模型参数设置");
            aiHost.setText(JCGSetting.JCG_SETTING_AI_HOST);
            aiModel.setText(JCGSetting.JCG_SETTING_AI_MODEL);
            aiMaxConcurrent.setValue(JCGSetting.JCG_SETTING_AI_TASK_NUM);
            aiTimeout.setValue(JCGSetting.JCG_SETTING_AI_TASK_TIMEOUT);
            SettingsLayoutUtil.addLabeledComponent(argsPanel, "AI 地址", aiHost);
            SettingsLayoutUtil.addLabeledComponent(argsPanel, "AI 模型", aiModel);
            SettingsLayoutUtil.addLabeledComponent(argsPanel, "AI 最大并发数", aiMaxConcurrent);
            SettingsLayoutUtil.addLabeledComponent(argsPanel, "AI 请求超时时间", aiTimeout);
        }
        SettingsLayoutUtil.addComponentRow(that, argsPanel);
    }

    /**
     * 设置提示文本区域
     */
    private I18nPanel setupPromptTextArea(JTextArea promptComp, String defaultPrompt, String argsTip) {
        I18nPanel panel = new I18nPanel(new BorderLayout(10, 10));
        panel.setBorder(new I18nTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), argsTip));
        promptComp.setEditable(false);
        promptComp.setLineWrap(true);
        promptComp.setWrapStyleWord(true);
        panel.add(new JScrollPane(promptComp), BorderLayout.CENTER);
        I18nPanel buttonPanel = new I18nPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Dimension btnSize = new Dimension(90, 35);
        I18nButton edit = new I18nButton("编辑");
        I18nButton clear = new I18nButton("清空");
        I18nButton def = new I18nButton("恢复默认值");
        for (JButton btn : new I18nButton[] { edit, clear, def }) {
            btn.setMaximumSize(btnSize);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        edit.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                promptComp.setEditable(!promptComp.isEditable());
                edit.setText(promptComp.isEditable() ? "锁定" : "编辑");
            }
        });
        clear.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                promptComp.setText("");
            }
        });
        def.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                promptComp.setText(defaultPrompt);
            }
        });
        buttonPanel.add(edit);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(clear);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(def);
        panel.add(buttonPanel, BorderLayout.EAST);
        return panel;
    }

    /**
     * 应用设置
     */
    @Override
    public void applySettings() {
        // 应用设置逻辑
        JCGSetting.JCG_SETTING_AI_HOST = aiHost.getText();
        JCGSetting.JCG_SETTING_AI_MODEL = aiModel.getText();
        JCGSetting.JCG_SETTING_CLASS_PROMPT = classPrompt.getText();
        JCGSetting.JCG_SETTING_METHOD_PROMPT = methodPrompt.getText();
        JCGSetting.JCG_SETTING_FIELD_PROMPT = fieldPrompt.getText();
        JCGSetting.JCG_SETTING_AI_TASK_NUM = (int) aiMaxConcurrent.getValue();
        JCGSetting.JCG_SETTING_AI_TASK_TIMEOUT = (int) aiTimeout.getValue();
    }
}
