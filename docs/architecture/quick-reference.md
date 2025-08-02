# Niko-Admin 快速开发参考

## 常用开发流程

### 1. 新建CRUD模块完整流程

#### 步骤1: 创建数据库表
```sql
-- 示例：创建商品表
CREATE TABLE sys_product (
    id BIGINT PRIMARY KEY,
    tenant_id VARCHAR(20),
    dept_id BIGINT,
    user_id BIGINT,
    product_name VARCHAR(100) NOT NULL,
    product_code VARCHAR(50),
    price DECIMAL(10,2),
    status CHAR(1) DEFAULT '0',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by BIGINT,
    update_by BIGINT,
    del_flag CHAR(1) DEFAULT '0'
);
```

#### 步骤2: 使用代码生成器
1. 访问 `http://localhost:8080/tool/gen`
2. 选择数据表 `sys_product`
3. 配置生成信息：
   - 表名：sys_product
   - 实体类名：SysProduct
   - 作者：your-name
   - 模块名：system
   - 包路径：org.niko.system
4. 点击生成代码

#### 步骤3: 后端代码结构
生成的后端代码包含：
```
niko-modules/niko-system/src/main/java/org/niko/system/
├── controller/
│   └── SysProductController.java     # 控制器
├── domain/
│   ├── SysProduct.java              # 实体类
│   ├── bo/
│   │   └── SysProductBo.java        # 业务对象
│   └── vo/
│       └── SysProductVo.java        # 视图对象
├── mapper/
│   └── SysProductMapper.java        # 数据访问接口
├── service/
│   ├── ISysProductService.java      # 服务接口
│   └── impl/
│       └── SysProductServiceImpl.java # 服务实现
└── resources/mapper/system/
    └── SysProductMapper.xml         # SQL映射文件
```

#### 步骤4: 前端代码结构
生成的前端代码包含：
```
niko-ui/apps/web-antd/src/views/system/product/
├── index.vue                        # 主页面
├── product-modal.vue               # 弹窗组件
├── data.ts                         # 表格和表单配置
└── api/
    ├── index.ts                    # API接口
    └── model.d.ts                  # 类型定义
```

### 2. 关键注解使用

#### 权限控制
```java
// Controller层权限验证
@SaCheckPermission("system:product:list")   // 查询权限
@SaCheckPermission("system:product:add")    // 新增权限
@SaCheckPermission("system:product:edit")   // 编辑权限
@SaCheckPermission("system:product:remove") // 删除权限
@SaCheckPermission("system:product:export") // 导出权限
```

#### 数据权限
```java
// Mapper层数据权限
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),    // 部门数据权限
    @DataColumn(key = "userName", value = "user_id")     // 用户数据权限
})
```

#### 参数校验
```java
// BO对象校验注解
@NotNull(message = "ID不能为空", groups = {EditGroup.class})
@NotBlank(message = "商品名称不能为空", groups = {AddGroup.class, EditGroup.class})
@Length(min = 2, max = 50, message = "商品名称长度为2-50个字符")
```

#### 业务日志
```java
@Log(title = "商品管理", businessType = BusinessType.INSERT)  // 新增日志
@Log(title = "商品管理", businessType = BusinessType.UPDATE)  // 更新日志
@Log(title = "商品管理", businessType = BusinessType.DELETE)  // 删除日志
@Log(title = "商品管理", businessType = BusinessType.EXPORT)  // 导出日志
```

### 3. 前端开发常用模式

#### API调用
```typescript
// api/index.ts
export function productList(params?: ProductQuery) {
  return requestClient.get<PageResult<ProductVO>>('/system/product/list', { params });
}

export function productAdd(data: ProductForm) {
  return requestClient.postWithMsg<void>('/system/product', data);
}

export function productUpdate(data: ProductForm) {
  return requestClient.putWithMsg<void>('/system/product', data);
}

export function productRemove(id: ID | IDS) {
  return requestClient.deleteWithMsg<void>(`/system/product/${id}`);
}
```

#### 表格配置
```typescript
// data.ts
export const columns: VxeGridProps['columns'] = [
  { type: 'checkbox', width: 60 },
  { title: 'ID', field: 'id', width: 80 },
  { title: '商品名称', field: 'productName', minWidth: 120 },
  { title: '商品编码', field: 'productCode', width: 120 },
  { title: '价格', field: 'price', width: 100 },
  { title: '状态', field: 'status', width: 80, slots: { default: 'status' } },
  {
    field: 'action',
    title: '操作',
    fixed: 'right',
    width: 160,
    slots: { default: 'action' }
  }
];
```

#### 表单配置
```typescript
export const modalSchema: FormSchemaGetter = () => [
  {
    fieldName: 'id',
    component: 'Input',
    dependencies: { show: () => false } // 隐藏ID字段
  },
  {
    fieldName: 'productName',
    label: '商品名称',
    component: 'Input',
    rules: 'required'
  },
  {
    fieldName: 'productCode',
    label: '商品编码',
    component: 'Input',
    rules: 'required'
  },
  {
    fieldName: 'price',
    label: '价格',
    component: 'InputNumber',
    componentProps: { precision: 2, min: 0 }
  }
];
```

### 4. 数据库操作常用方法

#### Service层常用方法
```java
// 查询单个
ProductVo vo = baseMapper.selectVoById(id);

// 分页查询
LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery();
wrapper.like(StringUtils.isNotBlank(bo.getProductName()), Product::getProductName, bo.getProductName());
Page<ProductVo> result = baseMapper.selectVoPage(pageQuery.build(), wrapper);

// 新增
Product entity = MapstructUtils.convert(bo, Product.class);
baseMapper.insert(entity);

// 更新
Product entity = MapstructUtils.convert(bo, Product.class);
baseMapper.updateById(entity);

// 批量删除
baseMapper.deleteBatchIds(Arrays.asList(ids));
```

### 5. 常见问题解决

#### 权限配置
```java
// 1. 确保Controller有权限注解
@SaCheckPermission("system:product:list")

// 2. 前端页面使用权限指令
v-access:code="['system:product:add']"

// 3. 在菜单管理中配置对应权限标识
```

#### 数据权限不生效
```java
// 确保Mapper方法有数据权限注解
@DataPermission({
    @DataColumn(key = "deptName", value = "dept_id")
})

// 确保实体类有dept_id字段
private Long deptId;
```

#### 多租户数据隔离
```java
// 1. 实体类继承TenantEntity
public class Product extends TenantEntity {
    // ...
}

// 2. 数据库表有tenant_id字段
ALTER TABLE sys_product ADD COLUMN tenant_id VARCHAR(20);

// 3. 配置文件开启多租户
tenant:
  enable: true
```

### 6. 开发调试技巧

#### 查看生成的SQL
```yaml
# application-dev.yml
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```

#### 前端API调试
```typescript
// 在浏览器控制台查看请求详情
// Network选项卡查看请求响应
// Console查看错误信息
```

#### 后端异常处理
```java
// 使用ServiceException抛出业务异常
if (existsProduct) {
    throw new ServiceException("商品编码已存在");
}

// 使用ValidationUtils进行参数校验
ValidatorUtils.validate(bo, AddGroup.class);
```

### 7. 性能优化建议

#### 数据库优化
```sql
-- 添加索引
CREATE INDEX idx_product_code ON sys_product(product_code);
CREATE INDEX idx_product_name ON sys_product(product_name);
CREATE INDEX idx_tenant_dept ON sys_product(tenant_id, dept_id);
```

#### 查询优化
```java
// 只查询需要的字段
LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery();
wrapper.select(Product::getId, Product::getProductName, Product::getPrice);

// 使用分页查询
PageQuery pageQuery = new PageQuery();
pageQuery.setPageSize(20); // 控制每页数量
```

#### 缓存使用
```java
// 字典数据缓存
@Cacheable(value = "dict", key = "#dictType")
public List<DictData> getDictData(String dictType) {
    // ...
}
```

这个快速参考文档涵盖了日常开发中最常用的流程和技巧，可以帮助开发者快速上手和解决常见问题。