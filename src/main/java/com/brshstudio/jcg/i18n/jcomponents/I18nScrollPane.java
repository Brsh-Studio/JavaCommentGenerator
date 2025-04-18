package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.LocaleChangeListener;
import java.awt.*;

/**
 * 国际化滚动面板
 */
public class I18nScrollPane extends javax.swing.JScrollPane implements LocaleChangeListener {

    public I18nScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
    }

    public I18nScrollPane(Component view) {
        super(view);
    }

    public I18nScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
    }

    public I18nScrollPane() {
    }

    /**
     * 响应区域设置更改
     */
    @Override
    public void onLocaleChanged() {
        // JScrollPane无需国际化
    }
}
