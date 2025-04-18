package com.brshstudio.jcg.ui.setting.layout;

import com.brshstudio.jcg.i18n.jcomponents.I18nPanel;
import com.brshstudio.jcg.ui.setting.panels.GeneralSettingsPanel;
import com.brshstudio.jcg.ui.setting.panels.base.AbstractSettingsPanel;
import com.brshstudio.jcg.resource.JCGNavigation;
import com.brshstudio.jcg.resource.JCGSetting;
import com.brshstudio.jcg.ui.setting.SettingNavigationPanel;
import lombok.Getter;
import lombok.SneakyThrows;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 设置对话框
 */
public class SettingLayout extends JDialog {

    /**
     * 按钮面板
     */
    @Getter
    private final ButtomPanel buttonPanel;

    /**
     * 主面板
     */
    @Getter
    public final I18nPanel mainPanel;

    /**
     * 面板缓存
     */
    @Getter
    private final Map<String, AbstractSettingsPanel> panelCache = new ConcurrentHashMap<>();

    /**
     * 面板供应商列表
     */
    @Getter
    private final Map<String, Supplier<AbstractSettingsPanel>> panelSuppliers;

    /**
     * 名称与面板映射
     */
    private final Map<String, Class<? extends AbstractSettingsPanel>> nameToPanelMap;

    public SettingLayout(JFrame parent) {
        super(parent, "设置", true);
        List<JCGNavigation.SettingNavigation> navigations = JCGNavigation.getSettingNavigationConfig();
        this.mainPanel = new I18nPanel(new BorderLayout());
        this.buttonPanel = new ButtomPanel();
        this.nameToPanelMap = createNameToPanelMap(navigations);
        this.panelSuppliers = getSettingChildrenPanels(navigations);
        initializeUI();
    }

    /**
     * 按需加载 Panel 的核心逻辑
     */
    private Map<String, Supplier<AbstractSettingsPanel>> getSettingChildrenPanels(List<JCGNavigation.SettingNavigation> navigations) {
        return // 关键改造：返回 Supplier
        // 关键改造：返回 Supplier
        // 关键改造：返回 Supplier
        // 关键改造：返回 Supplier
        navigations.stream().flatMap(this::flattenNavigation).collect(Collectors.toMap(JCGNavigation.SettingNavigation::getName, this::createLazyPanelSupplier));
    }

    /**
     * 获取设置面板
     */
    public AbstractSettingsPanel getPanel(String name) {
        return this.panelCache.computeIfAbsent(name, k -> this.panelSuppliers.get(k).get());
    }

    /**
     * 获取指定类型的面板
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractSettingsPanel> T getPanel(Class<T> clazz) {
        // 缓存未命中时遍历供应商
        Map.Entry<String, Class<? extends AbstractSettingsPanel>> classEntry = nameToPanelMap.entrySet().stream().filter(entry -> entry.getValue().equals(clazz)).findFirst().orElse(null);
        if (classEntry != null) {
            return (T) getPanel(classEntry.getKey());
        }
        throw new UnsupportedOperationException("不支持该类！");
    }

    /**
     * 创建延迟加载的 Supplier
     */
    private Supplier<AbstractSettingsPanel> createLazyPanelSupplier(JCGNavigation.SettingNavigation n) {
        return () -> {
            try {
                // 实际创建 Panel 的代码（仅在第一次调用时执行）
                Class<?> panelClass = Class.forName("com.brshstudio.jcg.ui.setting.panels." + n.getPanel() + "SettingsPanel");
                AbstractSettingsPanel panel = (AbstractSettingsPanel) panelClass.getDeclaredConstructor(SettingLayout.class).newInstance(SettingLayout.this);
                panel.setName(n.getName());
                return panel;
            } catch (Exception e) {
                throw new RuntimeException("Failed to create panel: " + n.getName(), e);
            }
        };
    }

    /**
     * 创建导航名称与面板类名的映射
     */
    private Map<String, Class<? extends AbstractSettingsPanel>> createNameToPanelMap(List<JCGNavigation.SettingNavigation> navigations) {
        return navigations.stream().flatMap(this::flattenNavigation).collect(Collectors.toMap(JCGNavigation.SettingNavigation::getName, n -> {
            try {
                return (Class<? extends AbstractSettingsPanel>) Class.forName("com.brshstudio.jcg.ui.setting.panels." + n.getPanel() + "SettingsPanel");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to create panel: " + n.getName(), e);
            }
        }));
    }

    /**
     * 递归展开所有层级的SettingNavigation节点
     * @param navigation
     * @return
     */
    private Stream<JCGNavigation.SettingNavigation> flattenNavigation(JCGNavigation.SettingNavigation navigation) {
        return Stream.concat(Stream.of(navigation), navigation.getChildren().stream().flatMap(this::flattenNavigation));
    }

    /**
     * 初始化用户界面
     */
    private void initializeUI() {
        // 主面板
        I18nPanel panel = new I18nPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(0, 20, 15, 20));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        // 左侧导航面板
        I18nPanel leftPanel = new SettingNavigationPanel(this);
        // 右侧配置面板
        // 默认第一个面板
        mainPanel.add(getPanel(GeneralSettingsPanel.class).getMainPanel());
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(mainPanel);
        // 设置分割条初始位置
        splitPane.setDividerLocation(200);
        leftPanel.setMinimumSize(new Dimension(200, 0));
        // 底部按钮面板
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(panel);
        Rectangle bounds = getParent().getBounds();
        setSize((int) (bounds.width * 0.8), (int) (bounds.height * 0.8));
        setLocationRelativeTo(getParent());
        setResizable(false);
        buttonPanel.getApplyButton().addActionListener(e -> applySettings());
        buttonPanel.getCancelButton().addActionListener(e -> dispose());
    }

    /**
     * 应用设置
     */
    @SneakyThrows
    private void applySettings() {
        for (AbstractSettingsPanel panel : panelCache.values()) {
            panel.applySettings();
        }
        JCGSetting.save();
//        dispose();
    }
}
