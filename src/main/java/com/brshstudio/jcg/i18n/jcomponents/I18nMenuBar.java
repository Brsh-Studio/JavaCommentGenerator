package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.LocaleChangeListener;
import javax.swing.*;

/**
 * 国际化菜单栏接口
 */
public class I18nMenuBar extends JMenuBar implements LocaleChangeListener {

    public I18nMenuBar() {
    }

    /**
     * 语言区域更改时调用
     */
    @Override
    public void onLocaleChanged() {
        // JMenuBar 无需国际化
    }
}
