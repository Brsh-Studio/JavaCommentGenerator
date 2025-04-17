package com.brshstudio.jcg.ai;

import com.brshstudio.jcg.utils.Logger;
import com.brshstudio.jcg.resource.JCGSetting;
import com.brshstudio.jcg.utils.TextSave;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 聊天AI类
 */
public class ChatAI {

    /**
     * 聊天链接
     */
    private String chatUrl;

    /**
     * 模型类型
     */
    private String model;

    /**
     * 设置聊天URL
     */
    public void setChatUrl(String chatUrl) {
        this.chatUrl = chatUrl;
    }

    /**
     * 设置模型
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 客户端标识
     */
    private final HttpClient client;

    /**
     * 对象映射器
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ChatAI() {
        this(JCGSetting.JCG_SETTING_AI_HOST);
    }

    public ChatAI(String chatUrl) {
        this.chatUrl = chatUrl;
        this.model = JCGSetting.JCG_SETTING_AI_MODEL;
        this.client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).version(HttpClient.Version.HTTP_1_1).build();
    }

    /**
     * 发送消息
     */
    public String sendMessage(String prompt) throws Exception {
        String message = sendMessage(prompt, model, false);
        printMessageLog(prompt, message);
        return message;
    }

    /**
     * 打印消息日志
     *
     * @param prompt  提示词
     * @param message 结果
     */
    private void printMessageLog(String prompt, String message) {
        if (JCGSetting.JCG_SETTING_IS_PRINT_AI_OUTPUT) {
            // 打印输出逻辑
            Logger.printLineLogger("提示词：" + prompt + "\n" + "结果：" + message + "\n");
        }
        if (JCGSetting.JCG_SETTING_IS_SAVE_AI_OUTPUT) {
            // 保存输出逻辑
            TextSave.appendToFile(JCGSetting.JCG_SETTING_AI_OUTPUT_PATH, "提示词：" + prompt + "\n" + "结果：" + message + "\n\n");
        }
    }

    /**
     * 发送消息
     */
    public String sendMessage(String prompt, String model, boolean stream) throws Exception {
        String requestBody = buildRequestBody(prompt, model, stream);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(chatUrl)).header("Accept", "application/json").header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new Exception("API request failed with status: " + response.statusCode());
        }
        return parseResponse(response.body());
    }

    /**
     * 构建请求体
     */
    private String buildRequestBody(String prompt, String model, boolean stream) throws Exception {
        RequestOptions options = new RequestOptions((int) (Math.random() * 100000), 0.28f);
        ChatRequest request = new ChatRequest(model, prompt, "json", stream, options);
        return objectMapper.writeValueAsString(request);
    }

    /**
     * 解析响应体
     */
    private String parseResponse(String responseBody) throws Exception {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        if (!rootNode.has("response")) {
            throw new Exception("Invalid API response format");
        }
        return rootNode.get("response").asText();
    }

    // Request DTO classes
    private static class ChatRequest {

        /**
         * 模型类型
         */
        public String model;

        /**
         * 提示信息
         */
        public String prompt;

        /**
         * 数据格式
         */
        public String format;

        /**
         * 流标识
         */
        public boolean stream;

        /**
         * 选项配置
         */
        public RequestOptions options;

        public ChatRequest(String model, String prompt, String format, boolean stream, RequestOptions options) {
            this.model = model;
            this.prompt = prompt;
            this.format = format;
            this.stream = stream;
            this.options = options;
        }
    }

    /**
     * 请求选项配置
     */
    private static class RequestOptions {

        /**
         * 初始化种子值
         */
        public int seed;

        /**
         * 温度值
         */
        public float temperature;

        public RequestOptions(int seed, float temperature) {
            this.seed = seed;
            this.temperature = temperature;
        }
    }
}
