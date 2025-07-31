# 设计文档

## 概述

本设计文档描述了如何从Niko-Admin项目中完全清理snail-job相关的所有组件。清理工作将分为五个主要领域：Maven依赖管理、Java源代码、Spring配置、前端UI组件和基础设施配置。

## 架构

### 清理范围分析

基于代码扫描结果，需要清理的组件包括：

1. **Maven依赖层**
   - 根POM文件中的snail-job版本属性
   - 依赖管理中的snail-job相关依赖（已注释但需完全移除）
   - niko-common-job模块中的snail-job依赖

2. **Java代码层**
   - `niko-modules/niko-job/src/main/java/org/niko/job/snailjob/` 整个包
   - `niko-common/niko-common-job/src/main/java/org/niko/common/job/config/SnailJobConfig.java`
   - Spring Boot自动配置导入文件

3. **配置层**
   - Spring Boot自动配置文件中的SnailJobConfig引用
   - 可能存在的application.yml中的snail-job配置

4. **前端UI层**
   - Vue组件中的snail-job iframe页面
   - 图标定义中的snail-job图标
   - 菜单配置中的snail-job相关项

5. **基础设施层**
   - nginx配置中的snail-job代理设置
   - SQL脚本中的snail-job数据库结构

## 组件和接口

### 1. Maven依赖清理组件

**目标文件：**
- `pom.xml` (根目录)
- `niko-common/niko-common-job/pom.xml`

**清理内容：**
- 移除 `<snailjob.version>1.5.0</snailjob.version>` 属性
- 移除已注释的snail-job依赖块
- 移除niko-common-job中的snail-job依赖

### 2. Java源代码清理组件

**目标目录和文件：**
- `niko-modules/niko-job/src/main/java/org/niko/job/snailjob/` (整个目录)
- `niko-common/niko-common-job/src/main/java/org/niko/common/job/config/SnailJobConfig.java`
- `niko-common/niko-common-job/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`

**清理策略：**
- 删除整个snailjob包及其所有文件
- 删除SnailJobConfig配置类
- 从自动配置导入文件中移除SnailJobConfig引用

### 3. 前端UI清理组件

**目标文件：**
- `niko-ui/apps/web-antd/src/views/monitor/snailjob/index.vue`
- `niko-ui/packages/icons/src/svg/index.ts`
- `niko-ui/scripts/菜单图标替换sql/update_icon.sql`

**清理策略：**
- 删除snail-job监控页面组件
- 从图标导出中移除SvgSnailJobIcon
- 更新SQL脚本中的图标引用

### 4. 基础设施配置清理组件

**目标文件：**
- `script/niko-docker/nginx/conf/nginx.conf`
- `script/sql/job.sql`

**清理策略：**
- 移除nginx中的snailjob-server upstream配置
- 移除nginx中的/snail-job/路径代理配置
- 删除或重命名job.sql文件（如果完全是snail-job相关）

## 数据模型

### 清理操作数据模型

```
CleanupOperation {
  - type: string (DELETE_FILE, MODIFY_FILE, DELETE_DIRECTORY)
  - targetPath: string
  - backupRequired: boolean
  - dependencies: string[] (依赖的其他清理操作)
}
```

### 文件修改数据模型

```
FileModification {
  - filePath: string
  - modificationType: string (REMOVE_LINES, REMOVE_BLOCKS, REPLACE_CONTENT)
  - targetContent: string (要移除或替换的内容)
  - lineNumbers: number[] (可选，特定行号)
}
```

## 错误处理

### 1. 文件不存在错误
- **场景：** 尝试删除已经不存在的文件
- **处理：** 记录警告日志，继续执行其他清理操作

### 2. 文件权限错误
- **场景：** 没有权限删除或修改某些文件
- **处理：** 记录错误日志，提示用户手动处理

### 3. 依赖引用错误
- **场景：** 清理后可能导致的编译错误
- **处理：** 提供编译验证步骤，确保清理后项目可正常构建

### 4. 配置不一致错误
- **场景：** 部分清理完成但遗留了相关配置
- **处理：** 提供验证清单，确保所有相关配置都被清理

## 测试策略

### 1. 编译测试
- 清理完成后执行 `mvn clean compile` 验证Java代码编译正常
- 执行 `mvn clean package -DskipTests` 验证打包正常

### 2. 启动测试
- 启动应用程序验证没有snail-job相关的初始化错误
- 检查日志确保没有snail-job相关的错误信息

### 3. 前端构建测试
- 执行 `pnpm build:antd` 验证前端构建正常
- 检查构建输出确保没有snail-job相关的引用错误

### 4. 配置验证测试
- 检查所有配置文件确保没有遗留的snail-job配置
- 验证nginx配置语法正确性

## 实施顺序

1. **第一阶段：** Java源代码清理（避免编译错误）
2. **第二阶段：** Maven依赖清理（清理构建配置）
3. **第三阶段：** Spring配置清理（清理运行时配置）
4. **第四阶段：** 前端UI清理（清理用户界面）
5. **第五阶段：** 基础设施配置清理（清理部署配置）
6. **第六阶段：** 验证和测试（确保清理完整性）

## 回滚策略

- 在开始清理前创建Git分支或提交点
- 对于重要配置文件，在修改前创建备份
- 提供清理操作的逆向脚本（如果需要恢复）
- 记录所有清理操作的详细日志，便于问题排查