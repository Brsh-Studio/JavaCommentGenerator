package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

public class I18nCheckBox extends JCheckBox implements LocaleChangeListener {

    /**
     * xxx
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String textI18nKey;

    public I18nCheckBox() {
    }

    public I18nCheckBox(Icon icon) {
        super(icon);
    }

    public I18nCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    public I18nCheckBox(String text) {
        super(I18nUtil.get(text));
        setTextI18nKey(text);
    }

    public I18nCheckBox(Action a) {
        super(a);
    }

    public I18nCheckBox(String text, boolean selected) {
        super(I18nUtil.get(text), selected);
        setTextI18nKey(text);
    }

    public I18nCheckBox(String text, Icon icon) {
        super(I18nUtil.get(text), icon);
        setTextI18nKey(text);
    }

    public I18nCheckBox(String text, Icon icon, boolean selected) {
        super(I18nUtil.get(text), icon, selected);
        setTextI18nKey(text);
    }

    @Override
    public void setText(String text) {
        super.setText(I18nUtil.get(text));
        setTextI18nKey(text);
    }

    @Override
    public void onLocaleChanged() {
        setText(getTextI18nKey());
    }
}
