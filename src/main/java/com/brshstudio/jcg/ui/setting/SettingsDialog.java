package com.brshstudio.jcg.ui.setting;

import com.brshstudio.jcg.i18n.LanguageController;
import com.brshstudio.jcg.ui.setting.layout.SettingLayout;

import javax.swing.*;

/**
 * 设置对话框
 */
public class SettingsDialog extends SettingLayout {

    public SettingsDialog(JFrame parent) {
        super(parent);
        LanguageController.registerContainer(this);
    }

    @Override
    public void dispose() {
        LanguageController.removeContainer(this);
        super.dispose();
    }
}
