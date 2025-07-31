# 删除三方登录功能实施计划

- [-] 1. 准备工作和备份


  - 创建当前代码分支备份，确保可以快速回滚
  - 备份sys_social表数据，防止数据丢失
  - 记录当前系统中所有justauth相关配置项
  - _Requirements: 4.1, 4.2, 4.3, 4.4_

- [ ] 2. 删除前端三方登录UI组件
  - [ ] 2.1 移除登录页面中的三方登录组件引用
    - 修改`niko-ui/apps/web-antd/src/views/_core/authentication/login.vue`文件
    - 删除OAuthLogin组件的导入和使用
    - 移除third-party-login模板插槽内容
    - _Requirements: 1.1_

  - [ ] 2.2 删除OAuth登录相关组件文件
    - 删除`niko-ui/apps/web-antd/src/views/_core/authentication/oauth-login.vue`文件
    - 删除`niko-ui/apps/web-antd/src/views/_core/oauth-common.ts`文件
    - 删除`niko-ui/apps/web-antd/src/views/_core/social-callback/index.vue`文件
    - _Requirements: 1.2_

  - [ ] 2.3 删除通用UI库中的第三方登录组件
    - 删除`niko-ui/packages/effects/common-ui/src/ui/authentication/third-party-login.vue`文件
    - 修改`niko-ui/packages/effects/common-ui/src/ui/authentication/types.ts`，移除'social'类型定义
    - _Requirements: 1.1, 2.3_

- [ ] 3. 删除前端个人中心账号绑定功能
  - [ ] 3.1 修改个人中心账号绑定组件
    - 修改`niko-ui/apps/web-antd/src/views/_core/profile/components/account-bind.vue`文件
    - 移除所有三方账号绑定相关的UI和逻辑
    - 或者完全删除该组件文件（如果只用于三方账号绑定）
    - _Requirements: 1.3_

  - [ ] 3.2 删除前端Social API接口文件
    - 删除`niko-ui/apps/web-antd/src/api/system/social/`整个目录
    - 检查并移除其他文件中对social API的引用
    - _Requirements: 2.3_

- [ ] 4. 清理前端图标和资源
  - [ ] 4.1 移除三方登录相关图标
    - 修改`niko-ui/packages/icons/src/iconify-offline/index.ts`文件
    - 移除GithubOAuthIcon和其他三方平台图标的导出
    - 删除相关的图标导入语句
    - _Requirements: 2.3_

- [ ] 5. 删除后端niko-common-social模块
  - [ ] 5.1 从父模块中移除social模块引用
    - 修改`niko-common/pom.xml`文件
    - 从modules列表中移除`<module>niko-common-social</module>`
    - _Requirements: 2.1_

  - [ ] 5.2 删除niko-common-social模块目录
    - 删除`niko-common/niko-common-social/`整个目录
    - 确保删除所有相关的Java类、配置文件和资源文件
    - _Requirements: 2.1_

- [ ] 6. 删除系统模块中的Social相关组件
  - [ ] 6.1 删除Social实体和数据访问层
    - 删除`niko-modules/niko-system/src/main/java/org/niko/system/domain/SysSocial.java`
    - 删除`niko-modules/niko-system/src/main/java/org/niko/system/domain/bo/SysSocialBo.java`
    - 删除`niko-modules/niko-system/src/main/java/org/niko/system/domain/vo/SysSocialVo.java`
    - 删除`niko-modules/niko-system/src/main/java/org/niko/system/mapper/SysSocialMapper.java`
    - _Requirements: 2.2_

  - [ ] 6.2 删除Social服务层
    - 删除`niko-modules/niko-system/src/main/java/org/niko/system/service/ISysSocialService.java`
    - 删除`niko-modules/niko-system/src/main/java/org/niko/system/service/impl/SysSocialServiceImpl.java`
    - _Requirements: 2.2_

  - [ ] 6.3 检查并移除Social相关的控制器
    - 搜索并删除任何Social相关的Controller类
    - 移除/system/social/*相关的API端点
    - _Requirements: 2.3_

- [ ] 7. 清理Maven依赖和配置
  - [ ] 7.1 移除JustAuth相关依赖
    - 检查根目录`pom.xml`文件，移除JustAuth相关的依赖管理
    - 检查其他模块的pom.xml文件，确保没有JustAuth依赖引用
    - _Requirements: 4.2_

  - [ ] 7.2 清理自动配置注册
    - 检查META-INF/spring.factories文件，移除SocialAutoConfiguration相关注册
    - 清理任何与social相关的自动配置类引用
    - _Requirements: 4.2_

- [ ] 8. 删除数据库表结构
  - [ ] 8.1 检查数据库依赖关系
    - 查询sys_social表是否被其他表引用（外键约束）
    - 检查是否有视图或存储过程依赖该表
    - _Requirements: 2.4_

  - [ ] 8.2 删除sys_social表
    - 执行DROP TABLE sys_social语句删除表结构
    - 更新数据库初始化脚本，移除sys_social表的创建语句
    - _Requirements: 2.4_

- [ ] 9. 清理配置文件
  - [ ] 9.1 移除justauth配置项
    - 检查application.yml、application.properties等配置文件
    - 移除所有justauth.*前缀的配置项
    - 清理任何三方登录相关的配置参数
    - _Requirements: 4.1, 4.4_

- [ ] 10. 编译和基础测试
  - [ ] 10.1 后端编译测试
    - 执行Maven编译命令，确保后端代码编译通过
    - 解决任何编译错误和依赖问题
    - 运行后端单元测试，确保核心功能正常
    - _Requirements: 3.1, 3.2, 3.3_

  - [ ] 10.2 前端编译测试
    - 执行前端构建命令，确保前端代码编译通过
    - 解决任何TypeScript类型错误和组件引用错误
    - 运行前端单元测试，确保组件功能正常
    - _Requirements: 3.1, 3.2, 3.3_

- [ ] 11. 功能验证测试
  - [ ] 11.1 登录功能测试
    - 测试用户名密码登录功能是否正常工作
    - 测试短信验证码登录功能是否正常工作
    - 测试邮箱登录功能是否正常工作
    - 验证登录页面不显示任何三方登录选项
    - _Requirements: 1.1, 3.1, 3.2, 3.3_

  - [ ] 11.2 个人中心功能测试
    - 访问个人中心页面，确保页面正常显示
    - 验证不显示任何三方账号绑定选项
    - 测试其他个人中心功能是否正常工作
    - _Requirements: 1.3_

  - [ ] 11.3 系统启动和运行测试
    - 启动后端服务，检查启动日志无三方登录相关错误
    - 启动前端应用，确保页面正常加载
    - 验证系统整体功能稳定性
    - _Requirements: 1.4, 3.4_

- [ ] 12. 清理和文档更新
  - [ ] 12.1 清理生成的文件和缓存
    - 清理Maven target目录中的编译缓存
    - 清理前端node_modules和dist目录
    - 删除任何临时生成的Social相关文件
    - _Requirements: 2.1, 2.2, 2.3_

  - [ ] 12.2 更新项目文档
    - 更新README文件，移除三方登录相关的说明
    - 更新API文档，移除/system/social相关接口文档
    - 更新部署文档，移除三方登录配置说明
    - _Requirements: 4.4_