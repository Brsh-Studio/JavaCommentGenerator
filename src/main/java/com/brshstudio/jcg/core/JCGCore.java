package com.brshstudio.jcg.core;

import com.brshstudio.jcg.utils.Logger;
import com.brshstudio.jcg.utils.Tools;
import com.brshstudio.jcg.ai.ChatAI;
import com.brshstudio.jcg.resource.JCGSetting;
import com.brshstudio.jcg.ui.layout.BottomPanel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 笔记生成核心类
 */
public class JCGCore {

    /**
     * 创建ChatAI
     */
    public static final ChatAI chatAI = new ChatAI();

    /**
     * 对象映射器
     */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Java解析器
     */
    private static final JavaParser javaParser;

    static {
        try {
            ParserConfiguration configuration = new ParserConfiguration();
            configuration.setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17).setSymbolResolver(new JavaSymbolSolver(new ReflectionTypeSolver()));
            javaParser = new JavaParser(configuration);
        } catch (Exception e) {
            Logger.printLineLogger("解析器初始化失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 递归收集Java文件
     */
    public static List<File> collectJavaFiles(Path startPath) {
        try {
            return Files.walk(startPath).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".java")).map(Path::toFile).collect(Collectors.toList());
        } catch (Exception e) {
            Logger.printLineLogger("文件扫描失败：" + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * 处理文件
     */
    public static void processFile(File file, BottomPanel bottomPanel, float singleFileProgress, float sum) throws Exception {
        // 原文件处理逻辑
        CompilationUnit cu = javaParser.parse(file).getResult().get();
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        for (ClassOrInterfaceDeclaration declaration : cu.findAll(ClassOrInterfaceDeclaration.class)) {
            taskQueue.add(() -> {
                try {
                    Optional<Comment> classComment = declaration.getComment();
                    if (classComment.isEmpty()) {
                        String string = chatAI.sendMessage(JCGSetting.JCG_SETTING_CLASS_PROMPT.formatted(declaration.getNameAsString()));
                        JsonNode jsonNode = objectMapper.readTree(string);
                        if (jsonNode.has("comment")) {
                            String commentAi = jsonNode.get("comment").asText();
                            declaration.setComment(new JavadocComment(commentAi));
                        }
                    }
                } catch (Exception e) {
                    Logger.printLineLogger(e.getMessage());
                }
            });
        }
        Map<String, List<Object>> fileMessage = analyzeSourceCode(cu);
        float single = singleFileProgress / fileMessage.size();
        // 原子化数值类型
        AtomicInteger i = new AtomicInteger(0);
        for (Map.Entry<String, List<Object>> entry : fileMessage.entrySet()) {
            String key = entry.getKey();
            List<Object> values = entry.getValue();
            for (Object value : values) {
                taskQueue.add(() -> {
                    Logger.printLineLogger("处理标识：" + key);
                    process(key, value);
                    bottomPanel.setProgress("处理进度", sum + (single * i.incrementAndGet()));
                });
            }
        }
        Tools.executeWithFixedConcurrency(taskQueue, JCGSetting.JCG_SETTING_AI_TASK_NUM, 30, TimeUnit.SECONDS, (fileMessage.size() + 1) * 30, TimeUnit.SECONDS);
        BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
        writer.write(cu.toString());
        writer.close();
    }

    /**
     * 处理键值对
     */
    private static void process(String key, Object value) {
        try {
            if (value instanceof FieldDeclaration) {
                // 是字段
                Optional<Comment> comment = ((FieldDeclaration) value).getComment();
                boolean commentPresent = comment.isPresent();
                if (commentPresent && comment.get().getContent().contains("region")) {
                    // 移动到父级游离注释列表
                    ((FieldDeclaration) value).getParentNode().ifPresent(parent -> {
                        // 添加到类或编译单元
                        parent.addOrphanComment(comment.get());
                    });
                    // 移除方法关联
                    ((FieldDeclaration) value).setComment(null);
                    commentPresent = false;
                }
                if (!commentPresent || JCGSetting.JCG_SETTING_IS_OVERWRITE) {
                    String string = chatAI.sendMessage(JCGSetting.JCG_SETTING_FIELD_PROMPT.formatted(key));
                    JsonNode jsonNode = objectMapper.readTree(string);
                    if (jsonNode.has("comment")) {
                        String commentAi = jsonNode.get("comment").asText();
                        ((FieldDeclaration) value).setComment(new JavadocComment(commentAi));
                    }
                }
            } else if (value instanceof MethodDeclaration) {
                // 是字段
                Optional<Comment> comment = ((MethodDeclaration) value).getComment();
                boolean commentPresent = comment.isPresent();
                if (commentPresent && comment.get().getContent().contains("region")) {
                    // 移动到父级游离注释列表
                    ((MethodDeclaration) value).getParentNode().ifPresent(parent -> {
                        // 添加到类或编译单元
                        parent.addOrphanComment(comment.get());
                    });
                    // 移除方法关联
                    ((MethodDeclaration) value).setComment(null);
                    commentPresent = false;
                }
                if (!commentPresent || JCGSetting.JCG_SETTING_IS_OVERWRITE) {
                    String string = chatAI.sendMessage(JCGSetting.JCG_SETTING_METHOD_PROMPT.formatted(key));
                    JsonNode jsonNode = objectMapper.readTree(string);
                    if (jsonNode.has("comment")) {
                        String commentAi = jsonNode.get("comment").asText().replaceAll("@", "\n").replaceAll("\n\n", "\n");
                        ((MethodDeclaration) value).setComment(new JavadocComment(commentAi));
                    }
                }
            }
        } catch (Exception e) {
            Logger.printLineLogger(e.getMessage());
        }
    }

    /**
     * 分析Java文件
     *
     * @param cu 编译单位
     * @return java类描述信息
     * @throws IOException IO异常
     */
    public static Map<String, List<Object>> analyzeSourceCode(CompilationUnit cu) throws IOException {
        Map<String, List<Object>> result = new HashMap<>();
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(cls -> {
            // 处理字段
            cls.getFields().forEach(field -> field.getVariables().forEach(var -> result.computeIfAbsent(var.getNameAsString(), k -> new ArrayList<>()).add(field)));
            // 处理方法
            cls.getMethods().forEach(method -> result.computeIfAbsent(method.getDeclarationAsString(), k -> new ArrayList<>()).add(method));
        });
        return result;
    }
}
