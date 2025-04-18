package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import com.brshstudio.jcg.i18n.patch.I18nItem;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Hashtable;
import java.util.Vector;

/**
 * 国际化树结构
 */
public class I18nTree extends JTree implements LocaleChangeListener {

    /**
     * TreeCell渲染器
     */
    public static final DefaultTreeCellRenderer TREE_CELL_RENDERER = new TreeCellObjectRenderer();

    public I18nTree() {
    }

    public I18nTree(Object[] value) {
        super(value);
    }

    public I18nTree(Vector<?> value) {
        super(value);
    }

    public I18nTree(Hashtable<?, ?> value) {
        super(value);
    }

    public I18nTree(TreeNode root) {
        super(root);
    }

    public I18nTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public I18nTree(TreeModel newModel) {
        super(newModel);
    }

    /**
     * 响应地区设置改变事件
     */
    @Override
    public void onLocaleChanged() {
        // I18nTree 无需国际化
    }

    /**
     * 树形单元渲染器
     */
    private static class TreeCellObjectRenderer extends DefaultTreeCellRenderer {

        /**
         * 渲染树节点组件
         */
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode node) {
                if (node.getUserObject() instanceof I18nItem nav) {
                    setText(I18nUtil.get(nav.getName()));
                }
            }
            return this;
        }
    }
}
