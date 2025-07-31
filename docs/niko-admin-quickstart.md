# Niko-Admin 快速上手指南

## 🎯 新手快速开始

### 适用人群
- ✅ 有 Java 基础的开发者
- ✅ 有 Vue.js 基础的开发者  
- ✅ 对 Spring Boot 有一定了解
- ✅ 希望快速搭建管理系统的开发者

### 预估学习时间
- **快速上手**: 2-4小时 (基础运行)
- **熟悉功能**: 1-2天 (了解各模块功能)
- **深度开发**: 1-2周 (掌握二次开发)

## 🛠️ 环境要求

### 必需环境
| 环境 | 版本要求 | 验证命令 | 下载地址 |
|------|----------|----------|----------|
| **Java** | 17+ | `java -version` | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) |
| **Maven** | 3.6+ | `mvn -version` | [Apache Maven](https://maven.apache.org/download.cgi) |
| **Node.js** | 20.10.0+ | `node -v` | [Node.js](https://nodejs.org/) |
| **pnpm** | 9.12.0+ | `pnpm -v` | `npm install -g pnpm` |
| **PostgreSQL** | 14.2+ | `psql --version` | [PostgreSQL](https://www.postgresql.org/download/) |
| **Redis** | 7.2.8+ | `redis-server --version` | [Redis](https://redis.io/download/) |

### 可选环境 (推荐)
| 环境 | 作用 | 安装方式 |
|------|------|----------|
| **Docker** | 简化环境搭建 | [Docker Desktop](https://www.docker.com/products/docker-desktop/) |
| **MinIO** | 对象存储服务 | Docker方式安装 |

### 开发工具推荐
| 工具 | 用途 | 推荐理由 |
|------|------|----------|
| **IntelliJ IDEA** | Java开发 | 优秀的Java IDE |
| **VS Code** | 前端开发 | 轻量级且功能强大 |
| **Navicat/DBeaver** | 数据库管理 | 可视化数据库操作 |
| **Postman** | API测试 | 接口调试必备 |

## 🚀 快速启动步骤

### 方式一: Docker 一键启动 (推荐新手)

#### 1. 启动基础服务
```bash
# 进入 Docker 配置目录
cd script/niko-docker

# 启动 PostgreSQL + Redis + MinIO
docker-compose up -d

# 验证服务启动
docker-compose ps
```

#### 2. 验证服务状态
```bash
# PostgreSQL (端口 15432)
# 用户名: root, 密码: Nibuzhid@0

# Redis (端口 16379)  
# 默认无密码

# MinIO (端口 19000/19001)
# 用户名: nitthiko, 密码: Nibuzhid@0
```

#### 3. 初始化数据库
```bash
# 连接数据库
psql -h localhost -p 15432 -U root -d postgres

# 执行初始化脚本
\i script/sql/init.sql
\i script/sql/job.sql  
\i script/sql/workflow.sql
```

#### 4. 启动后端服务
```bash
# 编译项目
mvn clean compile -DskipTests

# 启动应用 (dev环境)
mvn clean spring-boot:run

# 或指定环境启动
mvn clean spring-boot:run -Dspring-boot.run.profiles=local
```

#### 5. 启动前端服务
```bash
# 进入前端目录
cd niko-ui

# 安装依赖
pnpm install

# 启动开发服务器
pnpm dev:antd
```

#### 6. 访问系统
- 🌐 **前端地址**: http://localhost:5173
- 🔗 **后端API**: http://localhost:8080
- 📚 **API文档**: http://localhost:8080/doc.html
- 💾 **MinIO控制台**: http://localhost:19001

---

### 方式二: 手动环境搭建

#### 1. PostgreSQL 安装配置
```sql
-- 创建数据库
CREATE DATABASE niko_admin;

-- 创建用户
CREATE USER niko_user WITH PASSWORD 'your_password';

-- 授权
GRANT ALL PRIVILEGES ON DATABASE niko_admin TO niko_user;
```

#### 2. Redis 安装配置
```bash
# 启动 Redis
redis-server

# 测试连接
redis-cli ping
```

#### 3. 修改配置文件
```yaml
# niko-admin/src/main/resources/application-dev.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/niko_admin
    username: niko_user
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379
```

#### 4. 按照上述步骤 4-6 启动服务

## 🔐 默认账号信息

### 系统管理员
```
账号: admin
密码: admin123
角色: 超级管理员
权限: 所有权限
```

### 普通用户
```
账号: test
密码: admin123  
角色: 普通用户
权限: 部分功能权限
```

### 租户账号
```
租户编号: 000000 (默认租户)
租户名称: 翎客科技
状态: 正常
```

## 📋 启动验证清单

### ✅ 后端服务验证
```bash
# 1. 检查应用启动日志
# 看到 "Niko-Admin启动成功" 表示启动完成

# 2. 检查端口占用
netstat -an | grep 8080

# 3. 访问健康检查
curl http://localhost:8080/actuator/health

# 4. 访问API文档
# 浏览器打开: http://localhost:8080/doc.html
```

### ✅ 前端服务验证
```bash
# 1. 检查前端启动日志
# 看到 "Local: http://localhost:5173" 表示启动完成

# 2. 访问登录页面
# 浏览器打开: http://localhost:5173
```

### ✅ 数据库连接验证
```bash
# 1. 查看数据库表
# 确认 sys_user, sys_role 等表已创建

# 2. 查看初始数据
SELECT * FROM sys_user LIMIT 5;
```

### ✅ 缓存服务验证
```bash
# Redis 连接测试
redis-cli
> ping
PONG
> keys *
```

## 🎨 登录系统体验

### 1. 访问登录页面
打开浏览器访问: http://localhost:5173

### 2. 选择登录方式
- 📱 **账号密码登录** (默认)
- 📞 **手机验证码登录**
- 📧 **邮箱登录**  
- 📲 **扫码登录**
- 🔗 **第三方登录**

### 3. 系统功能体验路径

#### 新手推荐体验顺序
```
1. 🏠 首页仪表板 -> 了解系统概况
2. 👥 系统管理 -> 用户管理 -> 查看用户列表
3. 👤 系统管理 -> 角色管理 -> 了解权限体系
4. 📋 系统管理 -> 菜单管理 -> 查看菜单结构
5. 📊 系统监控 -> 缓存监控 -> 查看Redis状态
6. 🔧 系统工具 -> 代码生成 -> 体验代码生成功能
7. 🌊 工作流管理 -> 流程设计 -> 体验流程设计器
```

## ⚠️ 常见问题解决

### 后端启动问题

#### 问题1: 端口占用
```bash
# 现象: 启动时提示端口8080被占用
# 解决方法1: 查找并关闭占用进程
lsof -i:8080
kill -9 <PID>

# 解决方法2: 修改端口
# 在 application.yml 中修改
server:
  port: 8081
```

#### 问题2: 数据库连接失败
```bash
# 现象: 启动时报数据库连接错误
# 解决步骤:
1. 检查PostgreSQL服务是否启动
2. 验证数据库用户名密码
3. 确认数据库已创建
4. 检查网络连接
```

#### 问题3: Redis连接失败
```bash
# 现象: 启动时报Redis连接错误
# 解决步骤:
1. 检查Redis服务是否启动
2. 验证Redis端口配置
3. 检查Redis密码设置
```

### 前端启动问题

#### 问题1: 依赖安装失败
```bash
# 现象: pnpm install 报错
# 解决方法:
1. 清理缓存: pnpm store prune
2. 删除node_modules: rm -rf node_modules
3. 重新安装: pnpm install
4. 尝试使用npm: npm install
```

#### 问题2: 页面空白
```bash
# 现象: 访问页面显示空白
# 解决步骤:
1. 检查控制台错误信息
2. 确认后端API是否正常
3. 检查代理配置
4. 清理浏览器缓存
```

#### 问题3: API请求失败
```bash
# 现象: 登录或其他API请求失败
# 解决步骤:
1. 检查后端服务是否启动
2. 确认API地址配置正确
3. 查看网络请求状态
4. 检查跨域配置
```

### Docker相关问题

#### 问题1: Docker服务启动失败
```bash
# 检查Docker服务状态
docker-compose logs postgres
docker-compose logs redis
docker-compose logs minio

# 重启服务
docker-compose restart
```

#### 问题2: 数据持久化问题
```bash
# 确认数据目录挂载
docker-compose exec postgres ls -la /var/lib/postgresql/data
```

## 🎯 性能优化建议

### 开发环境优化
```yaml
# application-dev.yml 优化配置
logging:
  level:
    org.niko: debug
    org.springframework: warn
    
# JVM参数优化
-Xms512m -Xmx1024m -XX:+UseG1GC
```

### 前端开发优化
```javascript
// vite.config.mts 优化配置
export default defineConfig({
  server: {
    hmr: true,      // 热更新
    open: true,     // 自动打开浏览器
  },
  build: {
    sourcemap: true // 开发环境源码映射
  }
})
```

## 📚 学习资源推荐

### 官方文档
- 📖 **Spring Boot官方文档**: https://spring.io/projects/spring-boot
- 📘 **Vue 3官方文档**: https://vuejs.org/
- 📗 **Ant Design Vue**: https://antdv.com/
- 📙 **Sa-Token文档**: http://sa-token.dev33.cn/

### 视频教程
- 🎥 **Spring Boot入门**: B站搜索"Spring Boot教程"
- 🎬 **Vue 3实战**: B站搜索"Vue3教程"
- 📹 **前后端分离项目**: B站搜索"前后端分离"

### 社区资源
- 💬 **Gitee仓库**: https://gitee.com/dromara/RuoYi-Plus
- 🗣️ **QQ交流群**: 搜索"RuoYi"相关群组
- 📱 **微信群**: 关注相关公众号获取群二维码

## 🔄 下一步学习建议

### 初学者 (1-2周目标)
1. **熟悉项目结构** - 理解各模块作用
2. **掌握基本操作** - 用户、角色、菜单管理
3. **了解权限体系** - RBAC权限模型
4. **练习API调用** - 使用Postman测试接口

### 进阶开发者 (2-4周目标)
1. **深入源码** - 理解核心组件实现
2. **自定义组件** - 开发业务组件
3. **扩展功能** - 添加新的业务模块
4. **性能优化** - SQL优化、缓存策略

### 高级开发者 (1-2月目标)
1. **架构设计** - 微服务拆分设计
2. **中间件集成** - 消息队列、搜索引擎
3. **部署运维** - Docker、K8s部署
4. **二次开发** - 基于框架开发完整项目

## 🎉 成功启动标志

当你看到以下界面时，恭喜你已经成功启动了Niko-Admin系统！

### 后端成功标志
```bash
  _   _ _ _   _   _     _ _          
 | \ | (_) | | | |   (_) |         
 |  \| |_| |_| |_| | _| | _____   
 | . ` | | __| __| |/ / |/ / _ \  
 | |\  | | |_| |_|   <|   < (_) | 
 |_| \_|_|\__|\__|_|\_\_|\_\___/  
                                  
Niko-Admin启动成功，欢迎使用！
接口文档: http://localhost:8080/doc.html
```

### 前端成功标志
- ✅ 能够正常访问登录页面
- ✅ 能够使用默认账号登录
- ✅ 能够看到系统主界面
- ✅ 各个菜单功能正常

---

> 📝 **说明**: 这是 Niko-Admin 的快速上手指南，帮助你在最短时间内启动并体验系统。
> 📖 **下一步**: 查看 `niko-admin-development-guide.md` 了解如何进行二次开发。