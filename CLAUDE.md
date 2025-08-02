# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

Niko-Admin是一个Java后端 + Vue前端的全栈多租户管理系统 (版本: 5.4.1)，基于Spring Boot 3.4.7和Vue 3构建。

## 开发环境要求

- **后端**: Java 17, Maven 3.6+
- **前端**: Node.js 20.10.0+, pnpm 9.12.0+
- **数据库**: PostgreSQL 14.2+
- **缓存**: Redis 7.2.8+
- **对象存储**: MinIO

## 常用开发命令

### 后端 (Java)

```bash
# 编译项目 (跳过测试)
mvn clean compile -DskipTests

# 打包项目
mvn clean package -DskipTests

# 运行应用 (默认dev环境)
mvn clean spring-boot:run

# 运行特定环境
mvn clean spring-boot:run -Dspring-boot.run.profiles=local
mvn clean spring-boot:run -Dspring-boot.run.profiles=prod

# 运行测试
mvn test
```

### 前端 (Vue3 + Vite)

```bash
# 进入前端目录
cd niko-ui

# 安装依赖
pnpm install

# 开发模式运行 (Ant Design版本)
pnpm dev:antd

# 构建生产版本
pnpm build:antd
pnpm build:antd:test  # 测试环境构建

# 类型检查
pnpm check:type

# 代码检查和格式化
pnpm lint
pnpm format

# 运行单元测试
pnpm test:unit

# 运行端到端测试
pnpm test:e2e

# 预览生产构建
pnpm preview

# 清理项目
pnpm clean
```

### Docker 开发环境

```bash
# 启动开发依赖服务 (PostgreSQL, Redis, MinIO)
cd script/niko-docker
docker-compose up -d

# 停止服务
docker-compose down
```

## 项目架构

### 后端架构 (模块化设计)

- **niko-admin**: 主应用模块，包含启动类和主要配置
- **niko-common**: 通用模块集合
  - `niko-common-core`: 核心工具类、常量、异常处理
  - `niko-common-web`: Web相关配置和处理器
  - `niko-common-mybatis`: MyBatis Plus配置和数据权限
  - `niko-common-redis`: Redis配置和缓存管理
  - `niko-common-satoken`: Sa-Token权限认证配置
  - `niko-common-security`: 安全配置
  - `niko-common-log`: 日志记录和切面
  - `niko-common-excel`: Excel导入导出
  - `niko-common-oss`: 对象存储服务
  - `niko-common-sms`: 短信服务
  - `niko-common-mail`: 邮件服务
  - `niko-common-encrypt`: 数据加密和API加密
  - `niko-common-tenant`: 多租户支持
  - `niko-common-sse`: 服务端推送
  - `niko-common-websocket`: WebSocket支持
- **niko-modules**: 业务模块
  - `niko-system`: 系统管理模块
  - `niko-generator`: 代码生成器
  - `niko-job`: 定时任务模块
  - `niko-workflow`: 工作流模块

### 前端架构 (Monorepo)

- **apps/web-antd**: Ant Design Vue版本的主应用
- **apps/backend-mock**: Mock后端服务
- **packages**: 共享包和组件库
- **internal**: 内部工具和配置

### 关键技术栈

**后端:**
- Spring Boot 3.4.7 (Java 17)
- MyBatis Plus 3.5.12 (数据库ORM)
- Sa-Token 1.44.0 (权限认证)
- Redisson 3.50.0 (分布式锁)
- Warm-Flow 1.7.4 (工作流引擎)
- SpringDoc 2.8.8 (API文档)
- Hutool 5.8.38 (工具库)

**前端:**
- Vue 3 + TypeScript
- Ant Design Vue (UI组件库)
- Vite (构建工具)
- Pinia (状态管理)
- Vue Router (路由管理)

## 重要配置文件

- `pom.xml`: Maven主配置文件，定义项目版本和依赖
- `niko-admin/src/main/resources/application.yml`: 主应用配置
- `niko-admin/src/main/resources/application-dev.yml`: 开发环境配置
- `niko-ui/package.json`: 前端主配置
- `script/niko-docker/docker-compose.yml`: Docker开发环境配置

## 数据库和缓存

- 使用PostgreSQL作为主数据库 (端口: 15432)
- Redis作为缓存和会话存储 (端口: 16379)
- 支持多租户数据隔离
- 使用MyBatis Plus进行ORM映射

## 特性功能

- 多租户支持 (可通过tenant.enable配置开关)
- 数据权限控制 (基于注解的数据权限)
- API接口加密 (支持RSA/SM2等非对称加密)
- 数据脱敏 (基于注解的敏感数据处理)
- 防重复提交 (基于注解的幂等性控制)
- 限流控制 (基于注解的接口限流)
- 操作日志记录 (基于切面的日志记录)
- 工作流引擎 (Warm-Flow集成)
- 代码生成器 (支持多种模板，包括Java/Vue模板)

## 代码生成器使用

访问 http://localhost:8080/tool/gen 进行代码生成，配置文件在 `generator.yml`。

## 开发注意事项

- 后端默认运行在8080端口
- 前端默认运行在5173端口
- 验证码默认开启 (可通过captcha.enable配置)
- 多租户功能默认开启 (可通过tenant.enable配置)
- 支持虚拟线程 (JDK21+)
- API文档访问: http://localhost:8080/doc.html

## 部署相关

- 支持Docker部署
- 提供完整的Docker Compose配置
- 支持多环境配置 (local/dev/prod)
- 前端支持构建产物分析 (pnpm build:analyze)