package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.LocaleChangeListener;
import javax.swing.text.Document;
import java.awt.*;

/**
 * 国际化文本字段
 */
public class I18nTextField extends javax.swing.JTextField implements LocaleChangeListener {

    public I18nTextField() {
    }

    public I18nTextField(String text) {
        super(text);
    }

    public I18nTextField(int columns) {
        super(columns);
    }

    public I18nTextField(String text, int columns) {
        super(text, columns);
    }

    public I18nTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    /**
     * 语言区域更改回调
     */
    @Override
    public void onLocaleChanged() {
        // JTextField 无需国际化
    }
}
