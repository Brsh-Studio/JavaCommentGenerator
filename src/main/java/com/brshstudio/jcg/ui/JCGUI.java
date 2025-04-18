package com.brshstudio.jcg.ui;

import com.brshstudio.jcg.i18n.LanguageController;
import com.brshstudio.jcg.ui.layout.Layout;

/**
 * 笔记生成器GUI
 */
public class JCGUI extends Layout {

    public JCGUI() {
        super();
        LanguageController.registerContainer(this);
    }

    @Override
    public void dispose() {
        LanguageController.removeContainer(this);
        super.dispose();
    }
}
