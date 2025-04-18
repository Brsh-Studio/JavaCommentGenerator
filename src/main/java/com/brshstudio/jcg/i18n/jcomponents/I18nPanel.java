package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.LocaleChangeListener;
import javax.swing.*;
import java.awt.*;

/**
 * 国际化面板
 */
public class I18nPanel extends JPanel implements LocaleChangeListener {

    public I18nPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public I18nPanel(LayoutManager layout) {
        super(layout);
    }

    public I18nPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public I18nPanel() {
    }

    /**
     * 响应语言区域变化
     */
    @Override
    public void onLocaleChanged() {
        // JPanel 无需国际化
    }
}
