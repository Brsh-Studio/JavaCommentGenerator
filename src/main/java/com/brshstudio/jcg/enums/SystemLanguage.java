package com.brshstudio.jcg.enums;

import lombok.Getter;
import java.util.Arrays;
import java.util.Locale;

public enum SystemLanguage {

    zh_CN(Locale.CHINA, "简体中文"), en_US(Locale.US, "English");

    @Getter
    private Locale locale;

    @Getter
    private String description;

    SystemLanguage(Locale locale, String description) {
        this.locale = locale;
        this.description = description;
    }

    public static String[] getAllLanguage() {
        return Arrays.stream(SystemLanguage.values()).map(SystemLanguage::getDescription).toArray(String[]::new);
    }

    public static SystemLanguage fromDescription(String selectorTheme) {
        for (SystemLanguage systemTheme : SystemLanguage.values()) {
            if (systemTheme.getDescription().equals(selectorTheme)) {
                return systemTheme;
            }
        }
        return zh_CN;
    }

    public static SystemLanguage fromLocale(Locale locale) {
        for (SystemLanguage systemTheme : SystemLanguage.values()) {
            if (systemTheme.getLocale().equals(locale)) {
                return systemTheme;
            }
        }
        return zh_CN;
    }
}
