# Nikko Admin

基于 Spring Boot 3 + Vue 3 + Ant Design Vue 的企业级管理系统

## 🚀 技术栈

### 后端
- **Java 17** + **Spring Boot 3.4.7**
- **Gradle** 构建工具
- **PostgreSQL** 数据库
- **Redis** 缓存
- **MyBatis Plus** ORM框架
- **Sa-Token** 权限认证

### 前端
- **Vue 3** + **TypeScript**
- **Ant Design Vue 4**
- **Vite** 构建工具
- **Pinia** 状态管理

## 📁 项目结构

```
nikko-admin/
├── niko-admin/              # 主应用
├── niko-common/             # 公共模块
│   ├── niko-common-core/    # 核心工具
│   ├── niko-common-mybatis/ # MyBatis配置
│   ├── niko-common-redis/   # Redis配置
│   ├── niko-common-security/# 安全配置
│   └── ...                  # 其他公共模块
├── niko-modules/            # 业务模块
│   ├── niko-system/         # 系统管理
│   ├── niko-workflow/       # 工作流
│   ├── niko-generator/      # 代码生成
│   └── niko-job/            # 定时任务
├── niko-ui/                 # 前端项目
│   ├── apps/web-antd/       # 主应用
│   └── packages/            # 公共包
└── script/                  # 脚本文件
```

## ⚡ 快速开始

### 环境要求
- JDK 17+
- Node.js 20.10.0+
- PostgreSQL 12+
- Redis 6.0+
- Gradle 8.x

### 后端启动
```bash
# 配置数据库连接
# 修改 niko-admin/src/main/resources/application-dev.yml

# 启动应用
./gradlew bootRun
```

### 前端启动
```bash
cd niko-ui
pnpm install
pnpm run dev:antd
```

## 🔧 构建

### 后端
```bash
./gradlew build
```

### 前端
```bash
cd niko-ui
pnpm run build:antd
```

## 📝 开发规范

- 提交信息使用 [Conventional Commits](https://www.conventionalcommits.org/)
- 代码风格遵循项目配置的 ESLint + Prettier 规则

## 📄 许可证

[MIT License](LICENSE)
