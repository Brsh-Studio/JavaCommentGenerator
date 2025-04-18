package com.brshstudio.jcg.ui.navigation;

import com.brshstudio.jcg.MainApplication;
import com.brshstudio.jcg.i18n.jcomponents.I18nMenu;
import com.brshstudio.jcg.i18n.jcomponents.I18nMenuBar;
import com.brshstudio.jcg.i18n.jcomponents.I18nMenuItem;
import com.brshstudio.jcg.i18n.jcomponents.I18nPanel;
import com.brshstudio.jcg.resource.JCGNavigation;
import com.brshstudio.jcg.resource.JCGNavigation.Navigation;
import com.brshstudio.jcg.ui.setting.SettingsDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 支持配置化的导航栏
 */
public class NavigationPanel extends I18nPanel {

    public NavigationPanel() {
        initMenuBar();
    }

    /**
     * 初始化菜单栏
     */
    private void initMenuBar() {
        setLayout(new BorderLayout());
        I18nMenuBar menuBar = new I18nMenuBar();
        menuBar.setBackground(new Color(0, 0, 0, 0));
        try {
            List<Navigation> navConfig = JCGNavigation.getNavigationConfig();
            buildMenuFromConfig(menuBar, navConfig);
        } catch (JCGNavigation.NavigationConfigException e) {
            showConfigErrorDialog(e);
            return;
        }
        add(menuBar, BorderLayout.NORTH);
    }

    /**
     * 递归构建菜单结构
     */
    private void buildMenuFromConfig(Container parent, List<Navigation> config) {
        for (Navigation nav : config) {
            if (nav.getChildren().isEmpty()) {
                parent.add(createMenuItem(nav));
            } else {
                I18nMenu menu = createMenu(nav);
                parent.add(menu);
                buildMenuFromConfig(menu, nav.getChildren());
            }
        }
    }

    /**
     * 创建带图标的菜单项
     */
    private I18nMenuItem createMenuItem(Navigation nav) {
        I18nMenuItem item = new I18nMenuItem(nav.getName());
        // 动态绑定 Action
        if (nav.getAction() != null && !nav.getAction().isEmpty()) {
            item.addActionListener(e -> invokeActionMethod(nav.getAction(), e));
        }
        // 设置快捷键
        if (nav.getShortKey() != null && !nav.getShortKey().isEmpty()) {
            KeyStroke keyStroke = parseShortKey(nav.getShortKey());
            if (keyStroke != null) {
                item.setAccelerator(keyStroke);
            }
        }
        // 可扩展图标加载逻辑
        // item.setIcon(loadIcon(nav.getIconPath()));
        return item;
    }

    /**
     * 创建带子菜单的父级菜单
     */
    private I18nMenu createMenu(Navigation nav) {
        I18nMenu menu = new I18nMenu(nav.getName());
        // 可显示调试信息
        menu.setToolTipText(nav.getAction());
        return menu;
    }

    /**
     * 反射调用处理方法
     */
    private void invokeActionMethod(String actionName, ActionEvent e) {
        try {
            Method method = this.getClass().getDeclaredMethod(actionName, ActionEvent.class);
            method.invoke(this, e);
        } catch (NoSuchMethodException ex) {
            showActionErrorDialog(actionName);
        } catch (Exception ex) {
            throw new RuntimeException("执行菜单动作失败: " + actionName, ex);
        }
    }

    /**
     * 解析快捷键字符串为 KeyStroke 对象
     */
    private KeyStroke parseShortKey(String shortKey) {
        // 快捷键格式示例：{Ctrl}{Alt}{I}
        if (!shortKey.matches("^\\{[^}]+\\}(\\{[^}]+\\})*$")) {
            JOptionPane.showMessageDialog(this, "快捷键格式错误: " + shortKey, "快捷键错误", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int modifiers = 0;
        int keyCode = 0;
        String[] keys = shortKey.split("\\}\\{");
        for (String key : keys) {
            key = key.replace("{", "").replace("}", "");
            switch(key) {
                case "Ctrl":
                    modifiers |= KeyEvent.CTRL_MASK;
                    break;
                case "Alt":
                    modifiers |= KeyEvent.ALT_MASK;
                    break;
                case "Shift":
                    modifiers |= KeyEvent.SHIFT_MASK;
                    break;
                default:
                    keyCode = KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
            }
        }
        return KeyStroke.getKeyStroke(keyCode, modifiers);
    }

    // 错误处理对话框
    private void showConfigErrorDialog(Exception e) {
        JOptionPane.showMessageDialog(this, "菜单配置加载失败: \n" + e.getMessage(), "配置错误", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 显示操作错误对话框
     */
    private void showActionErrorDialog(String actionName) {
        JOptionPane.showMessageDialog(this, "未找到对应的菜单处理方法: " + actionName, "动作错误", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * 显示设置窗口
     */
    @SuppressWarnings("unused")
    private void showSettings(ActionEvent e) {
        new SettingsDialog(MainApplication.gui).setVisible(true);
    }

    /**
     * 显示预设路径设置
     */
    @SuppressWarnings("unused")
    private void showPresetPath(ActionEvent e) {
        JOptionPane.showMessageDialog(MainApplication.gui, "预设路径功能正在开发中...", "预设路径", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 显示帮助文档
     */
    @SuppressWarnings("unused")
    private void showHelp(ActionEvent e) {
        JOptionPane.showMessageDialog(MainApplication.gui, "访问官网查看完整帮助文档：https://ng.brshworkstudio.com", "帮助", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 执行更新检查
     */
    @SuppressWarnings("unused")
    private void checkUpdate(ActionEvent e) {
        JOptionPane.showMessageDialog(MainApplication.gui, "已是最新版本：v1.0.1.1", "检查更新", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 显示关于信息
     */
    @SuppressWarnings("unused")
    private void showAbout(ActionEvent e) {
        JOptionPane.showMessageDialog(MainApplication.gui, "Notes Generator\nVersion 1.0.1.1\nCopyright © 2025 不染伤痕工作室", "关于", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 退出
     */
    @SuppressWarnings("unused")
    private void exit(ActionEvent e) {
        MainApplication.gui.dispose();
    }
}
