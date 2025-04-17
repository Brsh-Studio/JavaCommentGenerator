package com.brshstudio.jcg.ui.layout;

import com.brshstudio.jcg.ui.navigation.NavigationPanel;
import com.brshstudio.jcg.ui.toolbar.QuickToolPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 顶部面板类
 */
public class TopPanel extends JPanel {

    /**
     * 菜单面板标识
     */
    private final NavigationPanel navigationPanel;

    /**
     * 工具面板
     */
    private final QuickToolPanel toolPanel;

    public TopPanel() {
        setLayout(new BorderLayout());
        // 创建子组件
        navigationPanel = new NavigationPanel();
        toolPanel = new QuickToolPanel();
        // 菜单在上方
        add(navigationPanel, BorderLayout.NORTH);
        // 工具面板在中部
        add(toolPanel, BorderLayout.CENTER);
    }

    /**
     * 获取选中的模块
     */
    public String getSelectedModule() {
        return toolPanel.getSelectedModule();
    }

    /**
     * 获取路径
     */
    public String getPath() {
        return toolPanel.getPath();
    }
}
