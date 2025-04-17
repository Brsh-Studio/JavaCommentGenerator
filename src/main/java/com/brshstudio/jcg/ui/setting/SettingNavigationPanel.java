package com.brshstudio.jcg.ui.setting;

import com.brshstudio.jcg.ui.setting.layout.SettingLayout;
import com.brshstudio.jcg.ui.setting.panels.base.AbstractSettingsPanel;
import com.brshstudio.jcg.resource.JCGNavigation;
import com.brshstudio.jcg.resource.JCGNavigation.SettingNavigation;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.util.List;

/**
 * 设置导航面板
 */
public class SettingNavigationPanel extends JPanel {

    public SettingNavigationPanel(SettingLayout settingLayout) {
        setLayout(new BorderLayout());
        // 创建导航树
        DefaultMutableTreeNode root = buildNavigationTree();
        JTree navigationTree = new JTree(root);
        navigationTree.setRootVisible(false);
        navigationTree.setShowsRootHandles(true);
        navigationTree.setCellRenderer(new SettingNavigationCellRenderer());
        // 调整边框以匹配IDEA样式
        navigationTree.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        navigationTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) navigationTree.getLastSelectedPathComponent();
            if (node != null && node.getUserObject() instanceof SettingNavigation nav) {
                AbstractSettingsPanel panel = settingLayout.getPanel(nav.getName());
                if (panel != null) {
                    settingLayout.getMainPanel().removeAll();
                    panel.removeAll();
                    panel.activated(settingLayout, panel);
                    settingLayout.getMainPanel().add(panel.getMainPanel());
                    settingLayout.getMainPanel().revalidate();
                    settingLayout.getMainPanel().repaint();
                }
            }
        });
        // 自动展开根节点
        navigationTree.expandPath(new TreePath(root.getPath()));
        JScrollPane scrollPane = new JScrollPane(navigationTree);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * 构建导航树节点
     */
    private DefaultMutableTreeNode buildNavigationTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("设置");
        List<SettingNavigation> config = JCGNavigation.getSettingNavigationConfig();
        for (SettingNavigation nav : config) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(nav);
            addChildren(node, nav.getChildren());
            root.add(node);
        }
        return root;
    }

    /**
     * 添加子节点
     */
    private void addChildren(DefaultMutableTreeNode parent, List<SettingNavigation> children) {
        if (children != null) {
            for (SettingNavigation child : children) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(child);
                parent.add(node);
                addChildren(node, child.getChildren());
            }
        }
    }

    // 自定义TreeCellRenderer以正确显示节点文本
    private static class SettingNavigationCellRenderer extends DefaultTreeCellRenderer {

        /**
         * 渲染树节点组件
         */
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                if (node.getUserObject() instanceof SettingNavigation nav) {
                    setText(nav.getName());
                }
            }
            return this;
        }
    }
}
