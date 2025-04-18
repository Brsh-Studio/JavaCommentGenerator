package com.brshstudio.jcg.i18n.jcomponents;

import com.brshstudio.jcg.i18n.I18nUtil;
import com.brshstudio.jcg.i18n.LocaleChangeListener;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.awt.*;

/**
 * 国际化框架接口
 */
public class I18nFrame extends JFrame implements LocaleChangeListener {

    /**
     * xxx
     */
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private String titleI18nKey;

    public I18nFrame() throws HeadlessException {
    }

    public I18nFrame(GraphicsConfiguration gc) {
        super(gc);
    }

    public I18nFrame(String title) throws HeadlessException {
        super(I18nUtil.get(title));
        setTitleI18nKey(title);
    }

    public I18nFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
        setTitleI18nKey(title);
    }

    /**
     * 设置标题
     */
    @Override
    public void setTitle(String title) {
        super.setTitle(I18nUtil.get(title));
        setTitleI18nKey(title);
    }

    /**
     * 语言环境改变时调用
     */
    @Override
    public void onLocaleChanged() {
        setTitle(getTitleI18nKey());
    }
}
