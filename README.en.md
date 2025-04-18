# **Java Comment Generator**

## Project Introduction
**Java Comment Generator** It is a Java code comment generation tool based on a graphical interface, which automatically generates JavaDoc through class declarations, method declarations, and field declarations。

## Features
✅ AI prompt word customization
✅ Any version of Java code is supported
✅ Intelligent Parameter Recognition (Automatic Parsing of Method Parameters.)

## Software Architecture
**Technology Stack Composition**:
- Java version: Java 17
- Interface Framework: Swing FlatLaf modern skin
- Build tools: Maven support

**Architectural Advantages**:
- Modular design (separation of core engine from interface)
- Low resource footprint (memory consumption < 50MB)
- Cross-platform support (Windows/macOS/Linux)

## Installation Tutorial

### Environmental requirements
1. JDK Environment:
```bash
    # Verify the Java environment
    java -version # requires 17, self-test for lower versions, at least 11 
    javac -version
```

2. Build Tools:
```bash
  mvn -v # Maven 3.5 
```
### Full installation steps
#### Method 1: Developer Mode (Recommended)
```bash
    # Clone the repository
    git clone https://gitee.com/brshstudio/java-comment-generator.git
    
    # Go to the project directory
    cd java-comment-generator
    
    # Building a Project (Maven)
    mvn clean package -DskipTests
    
    # Run the application
    java -jar target/java-comment-generator-1.0.1.1.jar
```

#### Method 2: Download the exe file directly
1. Visit the Releases page (https://gitee.com/brshstudio/java-comment-generator/releases) to download the latest version

## Contribution Guidelines
Submissions via Gitee are welcome to follow the following steps:
1. Develop new features in the new branch
3. Maintain Test Coverage ≥ 80%
