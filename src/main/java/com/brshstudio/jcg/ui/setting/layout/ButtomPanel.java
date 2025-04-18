package com.brshstudio.jcg.ui.setting.layout;

import com.brshstudio.jcg.i18n.jcomponents.I18nButton;
import com.brshstudio.jcg.i18n.jcomponents.I18nPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;

/**
 * 底部面板
 */
public class ButtomPanel extends I18nPanel {

    /**
     * 申请按钮
     */
    private final I18nButton applyBtn = new I18nButton("应用");

    /**
     * 取消按钮标识
     */
    private final I18nButton cancelBtn = new I18nButton("取消");

    public ButtomPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        setOpaque(false);
        add(applyBtn);
        add(cancelBtn);
    }

    /**
     * 获取应用按钮
     */
    public I18nButton getApplyButton() {
        return applyBtn;
    }

    /**
     * 获取取消按钮。
     */
    public I18nButton getCancelButton() {
        return cancelBtn;
    }
}
