package com.brshstudio.jcg.utils;

import lombok.SneakyThrows;
import java.awt.*;
import java.util.concurrent.*;

/**
 * 工具类接口
 */
public class Tools {

    /**
     * 固定并发数执行器
     *
     * @param queue            任务队列
     * @param concurrencyLevel 并发线程数
     * @param overallTimeout   整体超时时间
     * @param overallTimeUnit  整体超时时间单位
     * @param taskTimeout    单个任务超时时间
     * @param taskTimeUnit   单个任务超时时间单位
     */
    @SneakyThrows
    public static void executeWithFixedConcurrency(BlockingQueue<Runnable> queue, int concurrencyLevel, int overallTimeout, TimeUnit overallTimeUnit, int taskTimeout, TimeUnit taskTimeUnit) {
        // 1. 使用 SynchronousQueue 严格避免任务堆积
        ExecutorService executor = new // 2. 设置拒绝策略为调用者运行，保证任务不丢失
        ThreadPoolExecutor(concurrencyLevel, concurrencyLevel, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            // 3. 直接提交任务，依赖线程池自身并发控制
            while (!queue.isEmpty()) {
                Runnable task = queue.poll();
                if (task != null) {
                    Future<?> future = executor.submit(() -> {
                        try {
                            task.run();
                        } catch (Exception e) {
                            // 4. 明确处理任务级异常
                            Logger.printLineLogger("任务执行失败: " + e.getMessage());
                        }
                    });
                    try {
                        future.get(taskTimeout, taskTimeUnit);
                    } catch (TimeoutException e) {
                        Logger.printLineLogger("任务执行超时: " + e.getMessage());
                        future.cancel(true);
                    }
                }
            }
        } finally {
            // 5. 确保无论如何都触发关闭
            executor.shutdown();
            try {
                if (!executor.awaitTermination(overallTimeout, overallTimeUnit)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * 递归打印组件树结构（带缩进格式）
     * @param component 起始组件（通常是顶层容器）
     * @param depth     当前递归深度（初始传入0）
     */
    public static void printComponentTree(Component component, int depth) {
        // 生成层级缩进符号（每层两个空格）
        String indent = "  ".repeat(depth);
        // 输出当前组件信息
        String name = (component.getName() != null) ? component.getName() : "Unnamed";
        System.out.printf("%s|- %s [%s] Size:%s %n", indent, name, component.getClass().getSimpleName(), component.getSize());
        // 如果当前组件是容器，递归处理子组件
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                printComponentTree(child, depth + 1);
            }
        }
    }
}
