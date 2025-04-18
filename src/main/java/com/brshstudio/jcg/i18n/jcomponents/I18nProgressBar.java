package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;

/**
 * 国际化进度条接口
 */
public class I18nProgressBar extends JProgressBar implements LocaleChangeListener {

    /**
     * 获取国际化键
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String stringI18nKey;

    /**
     * 获取国际化键
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String valueI18nKey;

    public I18nProgressBar() {
    }

    public I18nProgressBar(int orient) {
        super(orient);
    }

    public I18nProgressBar(int min, int max) {
        super(min, max);
    }

    public I18nProgressBar(int orient, int min, int max) {
        super(orient, min, max);
    }

    public I18nProgressBar(BoundedRangeModel newModel) {
        super(newModel);
    }

    /**
     * 设置字符串值
     */
    public void setString(String string, String value) {
        super.setString(I18nUtil.get(string, value));
        setStringI18nKey(string);
        setValueI18nKey(value);
    }

    /**
     * 设置字符串值
     */
    public void setString(String string) {
        setString(string, null);
    }

    /**
     * 响应语言区域更改事件
     */
    @Override
    public void onLocaleChanged() {
        setString(getStringI18nKey(), getValueI18nKey());
    }
}
