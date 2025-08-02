# Niko-Admin 技术文档

本目录包含了Niko-Admin系统的详细技术文档，帮助开发者深入理解系统架构和开发流程。

## 文档结构

```
docs/
├── README.md                                    # 文档说明
├── architecture/                                # 架构设计文档
│   ├── frontend-backend-flow.md                 # 前后端数据流转完整分析
│   └── quick-reference.md                       # 快速开发参考
└── frontend/                                    # 前端技术文档
    ├── icon-system.md                           # 图标系统完整分析
    └── offline-icons-guide.md                   # 内网离线图标部署指南
```

## 文档内容

### 架构设计 (architecture/)

- **[前后端数据流转完整分析](./architecture/frontend-backend-flow.md)**
  - 详细分析从前端Vue3页面到后端Spring Boot接口的完整处理流程
  - 包含代码示例、时序图和架构分析
  - 涵盖前端API调用、后端分层处理、数据权限控制等关键内容

- **[快速开发参考](./architecture/quick-reference.md)**
  - 新建CRUD模块的完整开发流程
  - 常用注解和配置的使用方法
  - 前后端开发的最佳实践
  - 常见问题的解决方案和性能优化建议

### 前端技术 (frontend/)

- **[图标系统完整分析](./frontend/icon-system.md)**
  - 详细分析Iconify图标系统的架构和工作原理
  - 离线图标与在线图标的混合加载机制
  - 图标组件的使用方式和最佳实践
  - 性能优化策略和问题诊断方法

- **[内网离线图标部署指南](./frontend/offline-icons-guide.md)**
  - 三种内网离线图标部署方案对比
  - 完全离线模式的详细实施步骤
  - 内网图标服务器的搭建和配置
  - 自动化部署脚本和验证工具

## 如何使用文档

1. **新手开发者**: 建议从[前后端数据流转分析](./architecture/frontend-backend-flow.md)开始，了解整体架构
2. **前端开发者**: 重点关注前端页面层和API调用层的实现，以及[图标系统分析](./frontend/icon-system.md)
3. **后端开发者**: 重点关注Controller、Service、Mapper各层的职责和实现
4. **架构师**: 可以参考整体架构设计和最佳实践建议
5. **运维工程师**: 内网部署时参考[离线图标部署指南](./frontend/offline-icons-guide.md)

## 文档贡献

欢迎为文档贡献内容，请遵循以下规范：

1. 使用Markdown格式编写
2. 包含足够的代码示例和说明
3. 保持文档结构清晰，便于阅读
4. 及时更新文档内容，确保与代码同步

## 相关链接

- [项目主配置](../CLAUDE.md)
- [前端项目](../niko-ui/)
- [后端项目](../niko-modules/)
- [代码生成器](../niko-modules/niko-generator/)