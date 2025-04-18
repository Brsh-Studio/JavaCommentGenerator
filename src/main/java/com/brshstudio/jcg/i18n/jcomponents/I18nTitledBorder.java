package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicHTML;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;

public class I18nTitledBorder extends TitledBorder implements LocaleChangeListener {

    /**
     * xxx
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String titleI18nKey;

    private final JLabel label;

    public I18nTitledBorder(String title) {
        this(null, title, LEADING, DEFAULT_POSITION, null, null);
    }

    public I18nTitledBorder(Border border) {
        this(border, "", LEADING, DEFAULT_POSITION, null, null);
    }

    public I18nTitledBorder(Border border, String title) {
        this(border, title, LEADING, DEFAULT_POSITION, null, null);
    }

    public I18nTitledBorder(Border border,
                        String title,
                        int titleJustification,
                        int titlePosition) {
        this(border, title, titleJustification,
                titlePosition, null, null);
    }

    public I18nTitledBorder(Border border,
                        String title,
                        int titleJustification,
                        int titlePosition,
                        Font titleFont) {
        this(border, title, titleJustification,
                titlePosition, titleFont, null);
    }
    public I18nTitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont, Color titleColor) {
        super(title);
        this.title = title;
        this.border = border;
        this.titleFont = titleFont;
        this.titleColor = titleColor;

        setTitleJustification(titleJustification);
        setTitlePosition(titlePosition);

        this.label = new JLabel();
        this.label.setOpaque(false);
        this.label.putClientProperty(BasicHTML.propertyKey, null);
        setTitleI18nKey(title);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(I18nUtil.get(title));
        setTitleI18nKey(title);
    }

    @Override
    public void onLocaleChanged() {
        setTitle(getTitleI18nKey());
    }
}
