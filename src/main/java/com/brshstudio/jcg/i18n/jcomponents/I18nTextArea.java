package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.LocaleChangeListener;
import javax.swing.text.Document;
import java.awt.*;

/**
 * 国际化文本区域
 */
public class I18nTextArea extends javax.swing.JTextArea implements LocaleChangeListener {

    public I18nTextArea() {
    }

    public I18nTextArea(String text) {
        super(text);
    }

    public I18nTextArea(int rows, int columns) {
        super(rows, columns);
    }

    public I18nTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
    }

    public I18nTextArea(Document doc) {
        super(doc);
    }

    public I18nTextArea(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
    }

    /**
     * 语言区域变更回调
     */
    @Override
    public void onLocaleChanged() {
        // 无需格式化
    }
}
