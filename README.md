# Nikko Admin - 企业级管理系统

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.x-4FC08D.svg)](https://vuejs.org/)
[![Ant Design Vue](https://img.shields.io/badge/Ant%20Design%20Vue-4.x-1890FF.svg)](https://antdv.com/)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A.svg)](https://gradle.org/)

## 📖 项目简介

Nikko Admin 是一个基于 Spring Boot 3 + Vue 3 + Ant Design Vue 的现代化企业级管理系统，采用前后端分离架构，提供完整的用户管理、权限控制、工作流等核心功能。

## ✨ 主要特性

### 🚀 技术架构
- **后端**: Spring Boot 3 + Gradle + MyBatis Plus
- **前端**: Vue 3 + TypeScript + Ant Design Vue 4
- **数据库**: MySQL 8.0+
- **缓存**: Redis
- **权限**: Sa-Token
- **工作流**: 内置工作流引擎

### 🔧 核心功能
- 👥 **用户管理**: 用户注册、登录、权限分配
- 🔐 **权限控制**: 基于角色的访问控制 (RBAC)
- 📋 **工作流**: 审批流程、任务管理
- 🗂️ **系统管理**: 菜单、角色、部门管理
- 📊 **数据管理**: 数据导入导出、表格操作
- 🎨 **主题定制**: 支持明暗主题切换

## 🏗️ 项目结构

```
nikko-admin/
├── niko-admin/                 # 主应用模块
│   ├── src/main/java/         # Java 源代码
│   └── src/main/resources/    # 配置文件
├── niko-common/               # 公共模块
│   ├── niko-common-core/     # 核心工具
│   ├── niko-common-mybatis/  # MyBatis 配置
│   ├── niko-common-redis/    # Redis 配置
│   ├── niko-common-security/ # 安全配置
│   └── ...                   # 其他公共模块
├── niko-modules/              # 业务模块
│   ├── niko-system/          # 系统管理
│   ├── niko-workflow/        # 工作流
│   └── niko-generator/       # 代码生成器
├── niko-ui/                   # 前端项目
│   ├── apps/web-antd/        # Ant Design Vue 应用
│   └── packages/              # 前端包
└── script/                    # 脚本和 SQL
```

## 🚀 快速开始

### 环境要求
- **JDK**: 17+
- **Node.js**: 20.10.0+
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Gradle**: 8.x

### 后端启动
```bash
# 克隆项目
git clone https://github.com/xueaan/nikko-admin.git
cd nikko-admin

# 配置数据库
# 修改 niko-admin/src/main/resources/application-dev.yml

# 启动应用
./gradlew bootRun
```

### 前端启动
```bash
cd niko-ui

# 安装依赖
pnpm install

# 启动开发服务器
pnpm run dev:antd
```

## 📁 模块说明

### 后端模块
- **niko-admin**: 主应用入口，包含启动类和配置
- **niko-common**: 公共工具模块，提供各种基础功能
- **niko-modules**: 业务功能模块，包含具体的业务逻辑

### 前端模块
- **web-antd**: 基于 Ant Design Vue 的管理界面
- **packages**: 可复用的前端组件和工具

## 🔧 构建部署

### 后端构建
```bash
# 构建 JAR 包
./gradlew build

# 构建 Docker 镜像
./gradlew dockerBuild
```

### 前端构建
```bash
cd niko-ui

# 生产环境构建
pnpm run build:antd

# 构建产物在 apps/web-antd/dist/ 目录
```

## 📝 开发规范

### 代码风格
- 后端遵循阿里巴巴 Java 开发手册
- 前端使用 ESLint + Prettier 规范
- 提交信息使用 Conventional Commits 规范

### 分支管理
- `master`: 主分支，用于生产环境
- `develop`: 开发分支
- `feature/*`: 功能分支
- `hotfix/*`: 热修复分支

## 🤝 贡献指南

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 📄 许可证

本项目采用 [MIT License](LICENSE) 许可证。

## 📞 联系方式

- **项目地址**: [https://github.com/xueaan/nikko-admin](https://github.com/xueaan/nikko-admin)
- **问题反馈**: [Issues](https://github.com/xueaan/nikko-admin/issues)

## 🙏 致谢

感谢以下开源项目的支持：
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Vue.js](https://vuejs.org/)
- [Ant Design Vue](https://antdv.com/)
- [MyBatis Plus](https://baomidou.com/)

---

⭐ 如果这个项目对您有帮助，请给我们一个 Star！
