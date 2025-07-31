# 实施计划

- [x] 1. 清理Java源代码文件




  - 删除整个snailjob包目录及其所有Java文件
  - 删除SnailJobConfig配置类文件
  - 更新Spring Boot自动配置导入文件
  - _需求: 2.1, 2.2, 2.3, 3.1, 3.2, 3.3_

- [x] 1.1 删除snailjob包目录


  - 删除 `niko-modules/niko-job/src/main/java/org/niko/job/snailjob/` 整个目录
  - 验证目录下的所有Java文件都被删除
  - _需求: 2.1, 2.3_

- [x] 1.2 删除SnailJobConfig配置类







  - 删除 `niko-common/niko-common-job/src/main/java/org/niko/common/job/config/SnailJobConfig.java` 文件
  - 验证配置类文件已被完全移除
  - _需求: 3.1, 3.3_

- [x] 1.3 更新Spring Boot自动配置导入


  - 修改 `niko-common/niko-common-job/src/main/resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports` 文件
  - 移除 `org.niko.common.job.config.SnailJobConfig` 导入行
  - _需求: 3.1, 3.2_

- [x] 2. 清理Maven依赖配置





  - 从根POM文件中移除snail-job版本属性
  - 从根POM文件中移除已注释的snail-job依赖
  - 从niko-common-job模块中移除snail-job依赖
  - _需求: 1.1, 1.2, 1.3_

- [x] 2.1 清理根POM文件中的snail-job配置


  - 从 `pom.xml` 中移除 `<snailjob.version>1.5.0</snailjob.version>` 属性
  - 从 `pom.xml` 中移除已注释的snail-job依赖块（包括注释）
  - _需求: 1.1, 1.3_

- [x] 2.2 清理niko-common-job模块的依赖


  - 从 `niko-common/niko-common-job/pom.xml` 中移除已注释的snail-job依赖
  - 移除相关的注释说明
  - _需求: 1.1, 1.2_

- [ ] 3. 清理前端UI组件和引用
  - 删除snail-job监控页面组件
  - 从图标定义中移除snail-job图标
  - 更新菜单图标SQL脚本
  - _需求: 4.1, 4.2, 4.3_

- [ ] 3.1 删除snail-job监控页面
  - 删除 `niko-ui/apps/web-antd/src/views/monitor/snailjob/index.vue` 文件
  - 如果snailjob目录为空，则删除整个目录
  - _需求: 4.1, 4.2_

- [ ] 3.2 清理图标定义
  - 从 `niko-ui/packages/icons/src/svg/index.ts` 中移除SvgSnailJobIcon相关代码
  - 移除图标的创建、导出和引用
  - _需求: 4.3_

- [ ] 3.3 更新菜单图标SQL脚本
  - 修改 `niko-ui/scripts/菜单图标替换sql/update_icon.sql` 文件
  - 移除或注释掉snail-job图标的更新语句
  - _需求: 4.1_

- [ ] 4. 清理基础设施配置
  - 从nginx配置中移除snail-job代理设置
  - 处理snail-job相关的SQL脚本
  - _需求: 5.1, 5.2, 5.3_

- [ ] 4.1 清理nginx配置
  - 从 `script/niko-docker/nginx/conf/nginx.conf` 中移除snailjob-server upstream配置
  - 移除/snail-job/路径的代理配置块
  - _需求: 5.2_

- [ ] 4.2 处理snail-job SQL脚本
  - 检查 `script/sql/job.sql` 文件内容
  - 如果完全是snail-job相关内容则删除文件，否则移除snail-job相关部分
  - _需求: 5.3_

- [ ] 5. 验证清理完整性
  - 执行编译测试验证Java代码正常
  - 执行应用启动测试验证配置正常
  - 执行前端构建测试验证UI正常
  - _需求: 1.2, 2.2, 3.1, 4.2_

- [ ] 5.1 执行Java编译验证
  - 运行 `mvn clean compile -DskipTests` 验证编译无错误
  - 检查编译输出确保没有snail-job相关的缺失依赖错误
  - _需求: 1.2, 2.2_

- [ ] 5.2 执行应用启动验证
  - 启动应用程序并检查启动日志
  - 确认没有snail-job相关的初始化错误或警告
  - _需求: 3.1_

- [ ] 5.3 执行前端构建验证
  - 运行 `pnpm build:antd` 验证前端构建正常
  - 检查构建输出确保没有snail-job相关的引用错误
  - _需求: 4.2_