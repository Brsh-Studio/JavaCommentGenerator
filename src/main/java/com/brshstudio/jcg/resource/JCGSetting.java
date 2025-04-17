package com.brshstudio.jcg.resource;

import com.brshstudio.jcg.core.JCGCore;
import com.brshstudio.jcg.enums.SystemTheme;
import com.brshstudio.jcg.theme.GlobalDarkTheme;
import com.brshstudio.jcg.theme.GlobalLightTheme;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.swing.*;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置
 */
public class JCGSetting implements Serializable {

    /**
     * 是否保存AI输出
     */
    public static boolean JCG_SETTING_IS_SAVE_AI_OUTPUT = false;

    /**
     * AI输出路径
     */
    public static String JCG_SETTING_AI_OUTPUT_PATH = "logs/ai_output.log";

    /**
     * 是否打印AI输出
     */
    public static boolean JCG_SETTING_IS_PRINT_AI_OUTPUT = false;

    /**
     * 主题颜色
     */
    public static SystemTheme JCG_SETTING_THEME = SystemTheme.SYSTEM_THEME;

    /**
     * 是否覆盖
     */
    public static Boolean JCG_SETTING_IS_OVERWRITE = false;

    /**
     * 是否检查
     */
    public static Boolean JCG_SETTING_IS_CHECK = false;

    /**
     * AI地址
     */
    public static String JCG_SETTING_AI_HOST = "http://localhost:11434/api/generate";

    /**
     * AI模型
     */
    public static String JCG_SETTING_AI_MODEL = "qwen2.5:7b";

    /**
     * AI请求并发数
     */
    public static Integer JCG_SETTING_AI_TASK_NUM = 5;

    /**
     * AI单次请求超时时间(单位秒)
     */
    public static Integer JCG_SETTING_AI_TASK_TIMEOUT = 30;

    /**
     * AI请求超时时间单位
     */
    /**
     * 模块路径列表
     */
    public static Map<String, String> JCG_SETTING_MODULE_PATHS = new HashMap<>();

    /**
     * 默认方法提示
     */
    public static String defaultMethodPrompt = """
        请根据以下方法声明生成对应的中文Javadoc注释，要求简洁明了，每个属性不超过10个字。
        方法声明:
        %s
        返回的结果:
        {
          "comment": "xxx \n@param xxx \n@return xxx \n@throws xxx"
        }
        """;

    /**
     * 方法提示
     */
    public static String JCG_SETTING_METHOD_PROMPT = defaultMethodPrompt;

    /**
     * 默认提示信息
     */
    public static String defaultFieldPrompt = "请根据以下方法声明生成对应的中文Javadoc注释，要求简洁明了，每个属性不超过10个字。请直接输出结果，无需输出其他任何东西。\n方法声明:\n%s\n返回的结果:\n{\n  \"comment\": \"xxx\",\n  \"@param\": \"参数名 参数说明\",\n  \"@return\": \"返回值说明\",\n  \"@throws\": \"异常类型 异常说明\"\n}\n";

    /**
     * 提示信息
     */
    public static String JCG_SETTING_FIELD_PROMPT = defaultFieldPrompt;

    /**
     * 默认类提示语
     */
    public static String defaultClassPrompt = """
        请根据以下类名生成对应的中文注释，要求简洁明了，不超过10个字。
        类或接口声名: %s
        返回的结果:
        {
          "comment": "xxx"
        }
        """;

    /**
     * 类提示语
     */
    public static String JCG_SETTING_CLASS_PROMPT = defaultClassPrompt;

    /**
     * 序列化工具
     */
    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * 配置文件目录
     */
    private static final String CONFIG_FILE = "config.json";

    /**
     * 保存配置文件
     *
     * @throws Exception
     */
    public static synchronized void save() throws Exception {
        // 更新AIChat
        JCGCore.chatAI.setModel(JCG_SETTING_AI_MODEL);
        JCGCore.chatAI.setChatUrl(JCG_SETTING_AI_HOST);
        Map<String, Object> configMap = new HashMap<>();
        // 通过反射获取所有静态字段
        for (Field field : JCGSetting.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                // 排除序列化工具字段
                if (!field.getName().equals("mapper") && field.getName().startsWith("JCG_SETTING")) {
                    field.setAccessible(true);
                    configMap.put(field.getName(), field.get(null));
                }
            }
        }
        mapper.writeValue(new File(CONFIG_FILE), configMap);
        // 保存配置文件后重新加载主题
        reloadTheme();
    }

    /**
     * 从文件加载配置
     *
     * @throws Exception
     */
    public static synchronized void load() throws Exception {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            return;
        }
        Map<String, Object> configMap = mapper.readValue(configFile, HashMap.class);
        for (Field field : JCGSetting.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                String fieldName = field.getName();
                if (configMap.containsKey(fieldName)) {
                    field.setAccessible(true);
                    Object value = null;
                    try {
                        value = convertType(field.getType(), configMap.get(fieldName));
                    } catch (Exception e) {
                        // 提示
                        JOptionPane.showMessageDialog(null, "配置文件解析错误，请检查配置文件格式是否正确。错误配置：" + fieldName + ", 已使用默认配置!", "错误", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    field.set(null, value);
                }
            }
        }
    }

    /**
     * 刷新主题
     */
    public static void reloadTheme() {
        // 如果是系统主题则根据系统主题切换对应的主题
        if (JCG_SETTING_THEME.equals(SystemTheme.SYSTEM_THEME)) {
            // TODO 暂时无法获取系统主题，采用默认白色
            if (UIManager.getBoolean("SystemTheme.isDark")) {
                GlobalDarkTheme.updateTheme();
            } else {
                GlobalLightTheme.updateTheme();
            }
            return;
        }
        // 根据配置文件切换主题
        if (JCG_SETTING_THEME.equals(SystemTheme.LIGHT_THEME)) {
            GlobalLightTheme.updateTheme();
        } else {
            GlobalDarkTheme.updateTheme();
        }
    }

    /**
     * 类型转换处理
     *
     * @param targetType
     * @param value
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Object convertType(Class<?> targetType, Object value) {
        if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value.toString());
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value.toString());
        } else if (targetType.isEnum()) {
            Class<? extends Enum> enumType = (Class<? extends Enum>) targetType;
            String enumName = value.toString();
            return Enum.valueOf(enumType, enumName);
        }
        return value;
    }
}
