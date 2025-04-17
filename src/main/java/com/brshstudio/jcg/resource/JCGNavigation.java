package com.brshstudio.jcg.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 导航栏读取
 */
public class JCGNavigation implements Serializable {

    /**
     * 配置文件路径
     */
    private static final String CONFIG_PATH = "navigation/navigation.json";

    /**
     * 配置文件路径
     */
    private static final String SETTING_CONFIG_PATH = "navigation/setting-navigation.json";

    /**
     * 映射关系定义
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 缓存配置
     */
    private static List<Navigation> cachedConfig;

    /**
     * 缓存配置设置
     */
    private static List<SettingNavigation> settingCachedConfig;

    /**
     * 带缓存的读取方法
     *
     * @return
     */
    public static synchronized List<Navigation> getNavigationConfig() {
        if (cachedConfig == null) {
            try (InputStream is = JCGNavigation.class.getClassLoader().getResourceAsStream(CONFIG_PATH)) {
                if (is == null)
                    throw new ConfigMissingException();
                cachedConfig = mapper.readValue(is, new TypeReference<>() {
                });
            } catch (Exception e) {
                throw new NavigationConfigException("导航配置解析失败", e);
            }
        }
        return cachedConfig;
    }

    /**
     * 带缓存的读取方法
     *
     * @return
     */
    public static synchronized List<SettingNavigation> getSettingNavigationConfig() {
        if (settingCachedConfig == null) {
            try (InputStream is = JCGNavigation.class.getClassLoader().getResourceAsStream(SETTING_CONFIG_PATH)) {
                if (is == null)
                    throw new ConfigMissingException();
                settingCachedConfig = mapper.readValue(is, new TypeReference<>() {
                });
                processPanelPaths(settingCachedConfig, null);
            } catch (Exception e) {
                throw new NavigationConfigException("导航配置解析失败", e);
            }
        }
        return settingCachedConfig;
    }

    /**
     * 递归处理panel路径
     * @param navigationList
     * @param parentPanel
     */
    private static void processPanelPaths(List<SettingNavigation> navigationList, String parentPanel) {
        for (SettingNavigation navigation : navigationList) {
            if (parentPanel != null) {
                navigation.setPanel(parentPanel.toLowerCase() + "." + navigation.getPanel());
            }
            if (!navigation.getChildren().isEmpty()) {
                processPanelPaths(navigation.getChildren(), navigation.getPanel());
            }
        }
    }

    // 自定义异常类
    public static class NavigationConfigException extends RuntimeException {

        public NavigationConfigException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * 配置缺失异常
     */
    public static class ConfigMissingException extends NavigationConfigException {

        public ConfigMissingException() {
            super("导航配置文件未找到: " + CONFIG_PATH, null);
        }
    }

    /**
     * 导航类/接口
     */
    @ToString
    public static class Navigation implements Serializable {

        /**
         * 名称
         * -- GETTER --
         *  获取名称
         */
        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        /**
         * 操作类型
         * -- GETTER --
         *  获取动作
         */
        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String action;

        /**
         * 快捷键
         */
        @Getter
        @Setter
        private String shortKey;

        /**
         * 子项列表
         * -- GETTER --
         *  获取子导航列表
         */
        @Getter
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<Navigation> children = new ArrayList<>();

        // Jackson反序列化需要默认构造器
        public Navigation() {
        }

        /**
         * 设置子导航列表
         */
        public void setChildren(List<Navigation> children) {
            this.children = children != null ? children : new ArrayList<>();
        }
    }

    /**
     * 设置导航配置
     */
    @ToString
    public static class SettingNavigation implements Serializable {

        /**
         * 名称
         */
        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String name;

        /**
         * 面板标识
         */
        @Getter
        @Setter
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String panel;

        /**
         * 子项列表
         */
        @Getter
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        private List<SettingNavigation> children = new ArrayList<>();

        // Jackson反序列化需要默认构造器
        public SettingNavigation() {
        }

        /**
         * 设置子导航项
         */
        public void setChildren(List<SettingNavigation> children) {
            this.children = children != null ? children : new ArrayList<>();
        }
    }
}
