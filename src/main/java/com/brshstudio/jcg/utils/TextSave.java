package com.brshstudio.jcg.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文本保存类
 */
public class TextSave {

    /**
     * 追加内容到文本文件（自动创建目录和文件）
     *
     * @param filePath 文件路径，如 "logs/data.txt"
     * @param content  要追加的内容
     */
    public static void appendToFile(String filePath, String content) {
        Path path = Paths.get(filePath);
        try {
            // 如果目录不存在则创建
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            // 使用指定编码追加写入
            Files.writeString(path, content + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            Logger.printLineLogger("写入文件失败: " + e.getMessage());
        }
    }
}
