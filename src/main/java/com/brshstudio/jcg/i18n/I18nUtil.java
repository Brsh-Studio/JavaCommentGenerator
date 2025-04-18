package com.brshstudio.jcg.i18n;

import com.brshstudio.jcg.ai.ChatAI;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 国际化工具接口
 */
public class I18nUtil {

    /**
     * 基路径
     */
    private static final String BASE_PATH = "/i18n/";

    /**
     * 获取翻译结果
     */
    private static final Map<String, String> translations = new ConcurrentHashMap<>();

    /**
     * 获取当前区域设置
     */
    private static volatile Locale currentLocale = Locale.US;

    /**
     * 获取默认区域设置
     */
    private static volatile Locale defaultLocale = Locale.CHINA;

    /**
     * 映射操作
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * 初始化语言环境
     * @param targetLocale 目标语言环境
     */
    public static void init(Locale targetLocale) {
        try {
            String fileName = "lang_" + targetLocale.toLanguageTag() + ".json";
            loadTranslations(fileName);
            currentLocale = targetLocale;
        } catch (Exception e) {
            translations.clear();
        }
    }

    /**
     * 加载翻译文件
     * @param fileName 文件名（示例：messages_en-US.json）
     */
    private static void loadTranslations(String fileName) {
        String filePath = BASE_PATH + fileName;
        try (InputStream is = I18nUtil.class.getResourceAsStream(filePath)) {
            if (is != null) {
                Map<String, String> temp = mapper.readValue(is, mapper.getTypeFactory().constructMapType(Map.class, String.class, String.class));
                translations.clear();
                translations.putAll(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException("加载语言文件失败: " + filePath, e);
        }
    }

    /**
     * 获取翻译文本（带参数支持）
     * @param key 键名
     * @param args 动态参数
     * @return 格式化后的文本
     */
    public static String get(String key, Object... args) {
        if (key == null)
            return null;
        String pattern = translations.getOrDefault(key, key);
        return MessageFormat.format(pattern, args);
    }

    /**
     * 获取当前语言环境
     */
    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}
