package com.brshstudio.jcg.enums;

import lombok.Getter;
import java.util.Arrays;

public enum SystemTheme {

    SYSTEM_THEME("系统主题"), LIGHT_THEME("亮色主题"), DARK_THEME("暗色主题");

    @Getter
    private String description;

    SystemTheme(String description) {
        this.description = description;
    }

    public static String[] getAllThemeModel() {
        return Arrays.stream(SystemTheme.values()).map(SystemTheme::getDescription).toArray(String[]::new);
    }

    public static SystemTheme fromDescription(String selectorTheme) {
        for (SystemTheme systemTheme : SystemTheme.values()) {
            if (systemTheme.getDescription().equals(selectorTheme)) {
                return systemTheme;
            }
        }
        return SYSTEM_THEME;
    }
}
