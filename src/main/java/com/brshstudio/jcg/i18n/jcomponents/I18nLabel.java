package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;

/**
 * 国际化标签接口
 */
public class I18nLabel extends javax.swing.JLabel implements LocaleChangeListener {

    /**
     * 获取国际化文本键
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String textI18nKey;

    /**
     * 自动添加冒号
     */
    @Getter(AccessLevel.PRIVATE)
    private boolean autoAppendColon = false;

    public I18nLabel(String text, boolean autoAppendColon) {
        setTextI18nKey(text);
        setAutoAppendColon(autoAppendColon);
    }

    public I18nLabel(String text, Icon icon, int horizontalAlignment) {
        super(I18nUtil.get(text), icon, horizontalAlignment);
        setTextI18nKey(text);
    }

    public I18nLabel(String text, int horizontalAlignment) {
        super(I18nUtil.get(text), horizontalAlignment);
        setTextI18nKey(text);
    }

    public I18nLabel(String text) {
        super(I18nUtil.get(text));
        setTextI18nKey(text);
    }

    public I18nLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public I18nLabel(Icon image) {
        super(image);
    }

    public I18nLabel() {
    }

    /**
     * 设置是否自动添加冒号
     */
    public void setAutoAppendColon(boolean autoAppendColon) {
        this.autoAppendColon = autoAppendColon;
        setText(getTextI18nKey());
    }

    /**
     * 设置文本内容
     */
    @Override
    public void setText(String text) {
        super.setText(I18nUtil.get(text) + (isAutoAppendColon() ? ":" : ""));
        setTextI18nKey(text);
    }

    /**
     * 语言区域更改时调用
     */
    @Override
    public void onLocaleChanged() {
        setText(getTextI18nKey());
    }
}
