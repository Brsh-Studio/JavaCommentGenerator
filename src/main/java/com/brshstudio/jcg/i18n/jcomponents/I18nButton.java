package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.awt.*;

/**
 * 国际化按钮接口
 */
public class I18nButton extends javax.swing.JButton implements LocaleChangeListener {

    /**
     * xxx
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String textI18nKey;

    public I18nButton() {
    }

    public I18nButton(Icon icon) {
        super(icon);
    }

    public I18nButton(String text) {
        super(I18nUtil.get(text));
        setTextI18nKey(text);
    }

    public I18nButton(Action a) {
        super(a);
    }

    public I18nButton(String text, Icon icon) {
        super(I18nUtil.get(text), icon);
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
     * 语言环境变更回调
     */
    @Override
    public void onLocaleChanged() {
        setText(getTextI18nKey());
    }
}
