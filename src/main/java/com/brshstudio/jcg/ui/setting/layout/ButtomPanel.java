package com.brshstudio.jcg.ui.setting.layout;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 底部面板
 */
public class ButtomPanel extends JPanel {

    /**
     * 申请按钮
     */
    private final JButton applyBtn = new JButton("应用");

    /**
     * 取消按钮标识
     */
    private final JButton cancelBtn = new JButton("取消");

    public ButtomPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        setOpaque(false);
        add(applyBtn);
        add(cancelBtn);
    }

    /**
     * 获取应用按钮
     */
    public JButton getApplyButton() {
        return applyBtn;
    }

    /**
     * 获取取消按钮。
     */
    public JButton getCancelButton() {
        return cancelBtn;
    }
}
