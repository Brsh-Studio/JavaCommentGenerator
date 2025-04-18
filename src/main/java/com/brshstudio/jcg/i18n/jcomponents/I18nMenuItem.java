package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;

/**
 * 国际化菜单项
 */
public class I18nMenuItem extends JMenuItem implements LocaleChangeListener {

    /**
     * 获取国际化文本键
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String textI18nKey;

    public I18nMenuItem() {
    }

    public I18nMenuItem(Icon icon) {
        super(icon);
    }

    public I18nMenuItem(String text) {
        super(I18nUtil.get(text));
        setTextI18nKey(text);
    }

    public I18nMenuItem(Action a) {
        super(a);
    }

    public I18nMenuItem(String text, Icon icon) {
        super(I18nUtil.get(text), icon);
        setTextI18nKey(text);
    }

    public I18nMenuItem(String text, int mnemonic) {
        super(I18nUtil.get(text), mnemonic);
        setTextI18nKey(text);
    }

    /**
     * 设置文本内容
     */
    @Override
    public void setText(String text) {
        super.setText(I18nUtil.get(text));
        setTextI18nKey(text);
    }

    /**
     * 响应语言区域更改
     */
    @Override
    public void onLocaleChanged() {
        setText(getTextI18nKey());
    }
}
