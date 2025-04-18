package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;

/**
 * 国际化菜单
 */
public class I18nMenu extends JMenu implements LocaleChangeListener {

    /**
     * xxx
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String textI18nKey;

    public I18nMenu() {
    }

    public I18nMenu(String text) {
        setText(text);
    }

    public I18nMenu(Action a) {
        super(a);
    }

    public I18nMenu(String text, boolean b) {
        this(text);
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
     * 语言区域变更回调
     */
    @Override
    public void onLocaleChanged() {
        setText(getTextI18nKey());
    }
}
