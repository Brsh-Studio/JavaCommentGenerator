package com.brshstudio.jcg.i18n;

import com.brshstudio.jcg.MainApplication;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 语言控制器
 */
public class LanguageController {

    private static final List<Container> containerList = new ArrayList<>();

    /**
     * 切换语言
     */
    public static void switchLanguage(Locale newLocale) {
        I18nUtil.init(newLocale);
        // 通知所有窗口更新文本
        for (Container container : containerList) {
            reloadLocal(container);
        }
    }

    /**
     * 注册容器
     * @param container
     */
    public static void registerContainer(Container container) {
        containerList.add(container);
    }

    /**
     * 移除容器
     * @param container
     */
    public static void removeContainer(Container container) {
        containerList.remove(container);
    }

    /**
     * 重新加载本地容器
     */
    private static void reloadLocal(Container parentContainer) {
        if (parentContainer instanceof LocaleChangeListener changeListener) {
            changeListener.onLocaleChanged();
        }
        for (Component comp : parentContainer.getComponents()) {
            if (comp instanceof LocaleChangeListener changeListener) {
                changeListener.onLocaleChanged();
            }
            if (comp instanceof Container container) {
                reloadLocal(container);
            }
        }
    }
}
