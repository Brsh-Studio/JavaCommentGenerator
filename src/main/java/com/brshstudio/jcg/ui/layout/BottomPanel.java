package com.brshstudio.jcg.ui.layout;

import com.brshstudio.jcg.core.JCGCore;
import com.brshstudio.jcg.utils.Logger;
import com.brshstudio.jcg.MainApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

/**
 * 底部面板类
 */
public class BottomPanel extends JPanel {

    /**
     * 进度条
     */
    private final JProgressBar progressBar;

    /**
     * 启动按钮标识
     */
    private final JButton startBtn;

    /**
     * 停止按钮标识
     */
    private final JButton stopBtn;

    /**
     * 主线程是否正在运行
     */
    boolean isRunning = false;

    /**
     * 启动主线程
     */
    public static Thread main = null;

    public BottomPanel() {
        // 设置布局为边界布局，水平间距为10像素，垂直间距为0像素
        setLayout(new BorderLayout(10, 0));
        // 设置面板的内边距，上下左右各为10像素
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // 进度条样式
        progressBar = new JProgressBar(0, 10000);
        // 开启字符显示
        progressBar.setStringPainted(true);
        // 进度条最小宽度为150像素，最大宽度为Integer.MAX_VALUE像素，实现弹性宽度
        progressBar.setMinimumSize(new Dimension(150, 20));
        progressBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        // 按钮面板使用盒式布局，从左到右排列组件
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        // 按钮面板设置边框和间距，边框为10像素，上下内边距为0像素，左右内边距为10像素
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        btnPanel.setOpaque(false);
        // 绿色按钮，固定尺寸为70x25像素
        startBtn = new JButton("开始");
        startBtn.setPreferredSize(new Dimension(70, 25));
        startBtn.setMaximumSize(new Dimension(70, 25));
        // 红色按钮，固定尺寸为70x25像素
        stopBtn = new JButton("停止");
        stopBtn.setPreferredSize(new Dimension(70, 25));
        stopBtn.setMaximumSize(new Dimension(70, 25));
        stopBtn.setEnabled(false);
        // 添加按钮到按钮面板，并添加间距
        btnPanel.add(startBtn);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(stopBtn);
        // 添加进度条到面板中间
        add(progressBar, BorderLayout.CENTER);
        progressBar.setString("暂无任务");
        // 添加按钮面板到面板右侧
        add(btnPanel, BorderLayout.EAST);
        setupEventHandlers();
    }

    /**
     * 设置进度条内容
     * @param tip
     * @param value
     */
    public void setProgress(String tip, double value) {
        progressBar.setString(tip + " " + String.format("%.2f%%", value));
        progressBar.setValue((int) (value * 100));
    }

    /**
     * 设置事件处理程序
     */
    private void setupEventHandlers() {
        startBtn.addActionListener(this::startProcessing);
        stopBtn.addActionListener(e -> {
            main.interrupt();
            Logger.printLineLogger("处理已停止");
        });
    }

    /**
     * 开始处理
     *
     * @param e
     */
    private void startProcessing(ActionEvent e) {
        if (isRunning) {
            Logger.printLineLogger("已经有一个任务在处理，请等待执行完成后再试！");
            return;
        }
        // TODO: 启动处理线程
        String path = MainApplication.gui.getTopPanel().getPath();
        Logger.printLineLogger("开始处理路径: " + path);
        try {
            List<File> files = JCGCore.collectJavaFiles(new File(path).toPath());
            // 进度条原理，先将100%分配给所有文件。
            // 然后处理文件时，再将该文件所占的百分比分配给他的子任务
            setProgress("处理进度:", 0);
            float singleFileProgress = 100F / files.size();
            main = new Thread() {

                @Override
                public void run() {
                    isRunning = true;
                    for (File file : files) {
                        try {
                            Logger.printLineLogger("正在处理: " + file.getPath());
                            JCGCore.processFile(file, BottomPanel.this, singleFileProgress, singleFileProgress * files.indexOf(file));
                            setProgress("处理进度:", singleFileProgress * (files.indexOf(file) + 1));
                        } catch (Exception ex) {
                            Logger.printLineLogger("处理失败: " + ex.getMessage());
                        }
                    }
                    setProgress("处理进度:", 100);
                    isRunning = false;
                }

                @Override
                public void interrupt() {
                    super.interrupt();
                }
            };
            main.start();
        } catch (Exception ex) {
            Logger.printLineLogger("处理失败: " + ex.getMessage());
        }
    }
}
