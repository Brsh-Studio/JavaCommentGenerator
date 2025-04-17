package com.brshstudio.jcg.ui.toolbar;

import com.brshstudio.jcg.MainApplication;
import com.brshstudio.jcg.resource.JCGSetting;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * 快速工具面板
 */
public class QuickToolPanel extends JPanel {

    /**
     * 模块选择框
     */
    private final JComboBox<String> moduleComboBox;

    /**
     * 路径标识
     */
    private final JTextField pathField;

    public QuickToolPanel() {
        // 测试行布局换行
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel moduleLabel = new JLabel("模块:");
        moduleComboBox = new JComboBox<>(getModuleNames());
        JLabel pathLabel = new JLabel("路径:");
        pathField = new JTextField();
        JButton browseBtn = new JButton("浏览");
        add(moduleLabel);
        add(moduleComboBox);
        add(pathLabel);
        add(pathField);
        add(browseBtn);
        // 事件绑定
        browseBtn.addActionListener(this::handleBrowse);
    }

    /**
     * 获取模块名称列表
     */
    public String[] getModuleNames() {
        return JCGSetting.JCG_SETTING_MODULE_PATHS.keySet().toArray(new String[0]);
    }

    /**
     * 获取模块路径
     */
    public String getModulePath(String module) {
        return JCGSetting.JCG_SETTING_MODULE_PATHS.get(module.toLowerCase());
    }

    // 访问组件属性方法
    public String getSelectedModule() {
        return (String) moduleComboBox.getSelectedItem();
    }

    /**
     * 获取路径
     */
    public String getPath() {
        return pathField.getText().trim();
    }

    /**
     * 设置文件路径
     */
    public void setPath(String path) {
        pathField.setText(path);
    }

    /**
     * 处理浏览事件
     */
    private void handleBrowse(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("选择Java文件");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java files", "java");
        chooser.setFileFilter(filter);
        if (chooser.showOpenDialog(MainApplication.gui) == JFileChooser.APPROVE_OPTION) {
            File selected = chooser.getSelectedFile();
            if (selected.isFile() && !selected.getName().toLowerCase().endsWith(".java")) {
                JOptionPane.showMessageDialog(this, "请选择Java文件或目录", "格式错误", JOptionPane.ERROR_MESSAGE);
                setPath("");
            } else {
                setPath(selected.getAbsolutePath());
            }
        }
    }
}
