# **Java注释生成器**

## 项目介绍
**Java注释生成器** 是一款基于图形化界面的 Java 代码注释生成工具，通过类声明，方法声明，字段声明自动生成JavaDoc。

## 功能特点
✅ AI 提示词自定义
✅ 支持任意版本Java代码
✅ 智能参数识别（自动解析方法参数

## 软件架构
**技术栈组成**：
- Java 版本：Java 17
- 界面框架：Swing + FlatLaf 现代皮肤
- 构建工具：Maven 支持

**架构优势**：
- 模块化设计（核心引擎与界面分离）
- 低资源占用（内存消耗 < 50MB）
- 跨平台支持（Windows/macOS/Linux）

## 安装教程

### 环境要求
1. JDK 环境：
   ```bash
   # 验证Java环境
   java -version  # 要求17+，低版本自测，至少11+
   javac -version
   ```

2. 构建工具：
   ```bash
   mvn -v        # Maven 3.5+
   ```

### 完整安装步骤
#### 方式一：开发者模式（推荐）
```bash
# 克隆仓库
git clone https://gitee.com/brshstudio/java-comment-generator.git

# 进入项目目录
cd java-comment-generator

# 构建项目（Maven）
mvn clean package -DskipTests

# 运行应用程序
java -jar target/java-comment-generator-1.0.1.1.jar
```

#### 方式二：直接下载exe文件
1. 访问 [Releases 页面](https://gitee.com/brshstudio/java-comment-generator/releases) 下载最新版

## 贡献指南
欢迎通过 Gitee 提交 PR，请遵循：
1. 在新建分支开发新功能
3. 保持测试覆盖率 ≥ 80%
