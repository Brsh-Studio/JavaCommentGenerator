package com.brshstudio.jcg.utils;

import com.brshstudio.jcg.i18n.jcomponents.I18nLabel;
import com.brshstudio.jcg.i18n.jcomponents.I18nPanel;
import com.brshstudio.jcg.i18n.jcomponents.I18nScrollPane;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 设置布局工具
 */
public class SettingsLayoutUtil {

    public enum FieldDirection {

        NORTH, CENTER
    }

    /**
     * 内容填充
     */
    private static final int CONTENT_PADDING = 10;

    /**
     * 节间距设置
     */
    private static final int SECTION_SPACING = 15;

    /**
     * 章节标题字体
     */
    private static final Font SECTION_TITLE_FONT = new Font("Dialog", Font.BOLD, 13);

    // 新增尺寸控制常量
    private static final int LABEL_WIDTH = 120;

    /**
     * 最大输入宽度
     */
    private static final int MAX_INPUT_WIDTH = 400;

    /**
     * 初始化内容面板（改进版）
     * @param contentPanel 需要初始化的面板
     * @return 包含滚动条的面板容器
     */
    public static I18nScrollPane initContentPanel(I18nPanel contentPanel) {
        // 使用GridBagLayout代替BoxLayout
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(CONTENT_PADDING, CONTENT_PADDING, CONTENT_PADDING, CONTENT_PADDING));
        // 创建中间容器保持顶部对齐
        I18nPanel wrapper = new I18nPanel(new BorderLayout());
        wrapper.setName("wrapper");
        wrapper.add(contentPanel, BorderLayout.NORTH);
        // 创建滚动面板
        I18nScrollPane scrollPane = new I18nScrollPane(wrapper);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        // 设置视口特性
        scrollPane.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
        scrollPane.getViewport().setBackground(contentPanel.getBackground());
        return scrollPane;
    }

    /**
     * 添加带标题的分隔区域
     * @param parent 父容器
     * @param title 区域标题
     */
    public static void addSectionTitle(I18nPanel parent, String title) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);
        // 添加间距（如果已有组件）
        if (parent.getComponentCount() > 0) {
            gbc.insets.top = SECTION_SPACING;
        }
        // 标题容器
        I18nPanel container = new I18nPanel(new BorderLayout());
        container.setOpaque(false);
        // 标题组件
        I18nLabel titleLabel = new I18nLabel(title);
        titleLabel.setFont(SECTION_TITLE_FONT);
        titleLabel.setBorder(new EmptyBorder(0, 0, 4, 0));
        // 分隔线
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        container.add(titleLabel, BorderLayout.NORTH);
        container.add(separator, BorderLayout.SOUTH);
        parent.add(container, gbc);
    }

    /**
     * 添加常规组件行（改进版）
     * @param parent 父容器（使用GridBagLayout的面板）
     * @param component 要添加的组件
     */
    public static void addComponentRow(I18nPanel parent, JComponent component) {
        // 自动设置组件尺寸限制
        if (!(component instanceof I18nScrollPane)) {
            component.setMaximumSize(new Dimension(component.getMaximumSize().width, component.getPreferredSize().height));
        }
        parent.add(component, createGbc());
    }

    /**
     * 添加带有标签的组件行
     * @param parent 父容器
     * @param labelText 标签文字
     * @param component 要添加的组件
     */
    public static void addLabeledComponent(I18nPanel parent, String labelText, JComponent component, FieldDirection direction, boolean isNonColon) {
        I18nPanel rowPanel = new I18nPanel(new BorderLayout(10, 0));
        rowPanel.setOpaque(false);
        I18nLabel label = new I18nLabel(labelText);
        if (!isNonColon) {
            label.setAutoAppendColon(true);
        }
        label.setLabelFor(component);
        label.setPreferredSize(new Dimension(LABEL_WIDTH, label.getPreferredSize().height));
        component.setMaximumSize(new Dimension(MAX_INPUT_WIDTH, component.getPreferredSize().height));
        if (direction.equals(FieldDirection.NORTH)) {
            I18nPanel i18nPanel = new I18nPanel(new BorderLayout());
            i18nPanel.add(label, BorderLayout.NORTH);
            rowPanel.add(i18nPanel, BorderLayout.WEST);
        } else {
            rowPanel.add(label, BorderLayout.WEST);
        }
        rowPanel.add(component, BorderLayout.CENTER);
        parent.add(rowPanel, createGbc());
    }

    /**
     * 添加带有标签的组件行 (不包含冒号)
     * @param parent 父容器
     * @param labelText 标签文字
     * @param component 要添加的组件
     */
    public static void addLabeledComponent(I18nPanel parent, String labelText, JComponent component) {
        addLabeledComponent(parent, labelText, component, FieldDirection.NORTH, false);
    }

    /**
     * 添加带有标签的组件行 (包含冒号)
     * @param parent 父容器
     * @param labelText 标签文字
     * @param component 要添加的组件
     */
    public static void addLabeledAndNonColonComponent(I18nPanel parent, String labelText, JComponent component) {
        addLabeledComponent(parent, labelText, component, FieldDirection.NORTH, true);
    }

    /**
     * 创建GridBagConstraints布局约束
     */
    private static GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        // 布局约束配置
        // 占满整行
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        // 左上对齐
        gbc.anchor = GridBagConstraints.NORTHWEST;
        // 仅水平填充
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // 水平使用剩余空间
        gbc.weightx = 1.0;
        // 垂直不扩展
        gbc.weighty = 0.0;
        // 底部间距6像素
        gbc.insets = new Insets(0, 0, 6, 0);
        return gbc;
    }
}
