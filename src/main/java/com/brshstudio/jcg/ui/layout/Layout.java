package com.brshstudio.jcg.ui.layout;

import lombok.Getter;
import javax.swing.*;
import java.awt.*;

/**
 * 注释生成器GUI 布局
 */
public class Layout extends JFrame {

    /**
     * 顶部面板标识
     */
    @Getter
    private TopPanel topPanel;

    /**
     * 底部面板
     */
    @Getter
    private BottomPanel bottomPanel;

    /**
     * 日志区域
     */
    @Getter
    private final JTextArea logArea = new JTextArea(15, 50);

    public Layout() {
        super("不染伤痕注释生成器 v1.0.0.1");
        initializeUI();
    }

    /**
     * 初始化用户界面
     */
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        // 顶部面板
        topPanel = new TopPanel();
        add(topPanel, BorderLayout.NORTH);
        // 日志区域
        JScrollPane logScroll = new JScrollPane(logArea);
        logArea.setEditable(false);
        add(logScroll, BorderLayout.CENTER);
        // 底部面板
        bottomPanel = new BottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = (int) (screenSize.width * 0.8);
        int windowHeight = (int) (screenSize.height * 0.8);
        setSize(windowWidth, windowHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
