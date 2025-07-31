# Niko-Admin 二次开发指南

## 🎯 开发指南概述

本指南面向希望基于 Niko-Admin 进行二次开发的开发者，涵盖从新手到高级的各种开发场景和最佳实践。

### 适用场景
- ✅ **新功能开发** - 添加业务模块和功能
- ✅ **现有功能扩展** - 增强已有功能
- ✅ **UI界面定制** - 修改界面风格和布局
- ✅ **第三方集成** - 集成外部系统和服务
- ✅ **性能优化** - 提升系统性能
- ✅ **安全增强** - 加强系统安全性

## 🏗️ 开发环境准备

### IDE配置推荐

#### IntelliJ IDEA (后端开发)
```xml
<!-- 推荐插件 -->
- Lombok Plugin
- MyBatis X
- Spring Boot Helper
- Maven Helper
- GitToolBox
- Rainbow Brackets
- Translation

<!-- 代码风格配置 -->
File -> Settings -> Editor -> Code Style -> Java
导入项目中的 .editorconfig 配置
```

#### VS Code (前端开发)
```json
// 推荐插件 (settings.json)
{
  "recommendations": [
    "Vue.volar",
    "Vue.vscode-typescript-vue-plugin", 
    "bradlc.vscode-tailwindcss",
    "esbenp.prettier-vscode",
    "dbaeumer.vscode-eslint",
    "ms-vscode.vscode-typescript-next"
  ]
}
```

### Git工作流配置
```bash
# 安装 husky 和 lint-staged (前端)
cd niko-ui
pnpm install

# Git hooks 会自动配置
# 提交前自动执行 lint 和格式化
```

## 🚀 后端开发实践

### 1. 新建业务模块

#### 步骤1: 创建模块结构
```bash
# 在 niko-modules 下创建新模块
mkdir niko-modules/niko-business
cd niko-modules/niko-business

# 创建标准Maven结构
mkdir -p src/main/java/org/niko/business
mkdir -p src/main/resources
mkdir -p src/test/java
```

#### 步骤2: 配置pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <parent>
        <artifactId>niko-modules</artifactId>
        <groupId>org.niko</groupId>
        <version>${revision}</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>niko-business</artifactId>
    <description>业务管理模块</description>

    <dependencies>
        <!-- 系统模块 -->
        <dependency>
            <groupId>org.niko</groupId>
            <artifactId>niko-system</artifactId>
        </dependency>
    </dependencies>
</project>
```

#### 步骤3: 创建标准目录结构
```java
src/main/java/org/niko/business/
├── controller/          // 控制器层
├── service/            // 服务层
│   └── impl/          // 服务实现
├── domain/            // 实体层
│   ├── bo/           // 业务对象
│   └── vo/           // 视图对象
├── mapper/            // 数据访问层
└── listener/          // 事件监听器
```

### 2. 实体类开发规范

#### 实体类示例 (Entity)
```java
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bus_order")
public class BusOrder extends BaseEntity {

    @TableId(value = "id")
    private Long id;

    /**
     * 订单编号
     */
    @NotBlank(message = "订单编号不能为空")
    @Size(max = 32, message = "订单编号长度不能超过32个字符")
    private String orderNo;

    /**
     * 订单金额
     */
    @NotNull(message = "订单金额不能为空")
    @DecimalMin(value = "0", message = "订单金额不能小于0")
    private BigDecimal amount;

    /**
     * 订单状态 (0待支付 1已支付 2已取消)
     */
    @DictData("order_status") // 字典数据
    private String status;

    /**
     * 客户ID
     */
    @DataPermission(deptAlias = "d") // 数据权限
    private Long customerId;

    /**
     * 备注信息
     */
    @EncryptField // 加密字段
    private String remark;
}
```

#### 业务对象 (BO)
```java
@Data
@EqualsAndHashCode(callSuper = true)
public class BusOrderBo extends BaseEntity {

    @NotNull(message = "订单ID不能为空")
    private Long id;

    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @NotNull(message = "订单金额不能为空")
    private BigDecimal amount;

    private String status;
    private Long customerId;
    private String remark;
}
```

#### 视图对象 (VO)
```java
@Data
@Accessors(chain = true)
public class BusOrderVo {

    @ExcelProperty(value = "订单ID")
    private Long id;

    @ExcelProperty(value = "订单编号")
    private String orderNo;

    @ExcelProperty(value = "订单金额")
    private BigDecimal amount;

    @ExcelProperty(value = "订单状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "order_status")
    private String status;

    @ExcelProperty(value = "创建时间")
    private Date createTime;
}
```

### 3. 数据访问层 (Mapper)

#### Mapper接口
```java
@Mapper
public interface BusOrderMapper extends BaseMapperPlus<BusOrder, BusOrderVo> {
    
    /**
     * 查询订单列表
     */
    @DataPermission(deptAlias = "d")
    Page<BusOrderVo> selectOrderList(@Param("page") Page<BusOrder> page, 
                                    @Param("order") BusOrderBo order);
    
    /**
     * 根据条件查询订单
     */
    List<BusOrderVo> selectOrderByCondition(BusOrderBo order);
}
```

#### Mapper XML
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.niko.business.mapper.BusOrderMapper">

    <resultMap type="BusOrderVo" id="BusOrderResult">
        <result property="id" column="id"/>
        <result property="orderNo" column="order_no"/>
        <result property="amount" column="amount"/>
        <result property="status" column="status"/>
        <result property="createTime" column="create_time"/>
    </resultMap>

    <sql id="selectOrderVo">
        select id, order_no, amount, status, create_time
        from bus_order o
        left join sys_dept d on o.create_dept = d.dept_id
    </sql>

    <select id="selectOrderList" resultMap="BusOrderResult">
        <include refid="selectOrderVo"/>
        <where>
            o.del_flag = '0'
            <if test="order.orderNo != null and order.orderNo != ''">
                and o.order_no like concat('%', #{order.orderNo}, '%')
            </if>
            <if test="order.status != null and order.status != ''">
                and o.status = #{order.status}
            </if>
            ${params.dataScope}
        </where>
        order by o.create_time desc
    </select>
</mapper>
```

### 4. 服务层开发

#### 服务接口
```java
public interface IBusOrderService {
    
    /**
     * 查询订单分页列表
     */
    TableDataInfo<BusOrderVo> selectOrderList(BusOrderBo order, PageQuery pageQuery);
    
    /**
     * 查询订单详情
     */
    BusOrderVo selectOrderById(Long id);
    
    /**
     * 新增订单
     */
    Boolean insertOrder(BusOrderBo order);
    
    /**
     * 修改订单
     */
    Boolean updateOrder(BusOrderBo order);
    
    /**
     * 删除订单
     */
    Boolean deleteOrderByIds(Collection<Long> ids);
    
    /**
     * 导出订单数据
     */
    void export(BusOrderBo order, HttpServletResponse response);
}
```

#### 服务实现
```java
@Slf4j
@RequiredArgsConstructor
@Service
public class BusOrderServiceImpl implements IBusOrderService {

    private final BusOrderMapper baseMapper;

    @Override
    public TableDataInfo<BusOrderVo> selectOrderList(BusOrderBo order, PageQuery pageQuery) {
        Page<BusOrder> page = PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<BusOrderVo> list = baseMapper.selectOrderList(page, order);
        return TableDataInfo.build(list);
    }

    @Override
    public BusOrderVo selectOrderById(Long id) {
        return baseMapper.selectVoById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertOrder(BusOrderBo order) {
        BusOrder entity = MapstructUtils.convert(order, BusOrder.class);
        // 生成订单号
        entity.setOrderNo(generateOrderNo());
        validEntityBeforeSave(entity);
        boolean flag = baseMapper.insert(entity) > 0;
        if (flag) {
            order.setId(entity.getId());
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) 
    public Boolean updateOrder(BusOrderBo order) {
        BusOrder entity = MapstructUtils.convert(order, BusOrder.class);
        validEntityBeforeSave(entity);
        return baseMapper.updateById(entity) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOrderByIds(Collection<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void export(BusOrderBo order, HttpServletResponse response) {
        List<BusOrderVo> list = baseMapper.selectOrderByCondition(order);
        ExcelUtil.exportExcel(list, "订单数据", BusOrderVo.class, response);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(BusOrder entity) {
        // TODO 做一些数据校验,如唯一约束
    }
    
    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis();
    }
}
```

### 5. 控制器层开发

```java
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/business/order")
public class BusOrderController extends BaseController {

    private final IBusOrderService orderService;

    /**
     * 查询订单列表
     */
    @SaCheckPermission("business:order:list")
    @GetMapping("/list")
    public TableDataInfo<BusOrderVo> list(BusOrderBo order, PageQuery pageQuery) {
        return orderService.selectOrderList(order, pageQuery);
    }

    /**
     * 获取订单详细信息
     */
    @SaCheckPermission("business:order:query")
    @GetMapping("/{id}")
    public R<BusOrderVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable Long id) {
        return R.ok(orderService.selectOrderById(id));
    }

    /**
     * 新增订单
     */
    @SaCheckPermission("business:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @RepeatSubmit // 防重复提交
    @PostMapping
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BusOrderBo order) {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @SaCheckPermission("business:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BusOrderBo order) {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @SaCheckPermission("business:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] ids) {
        return toAjax(orderService.deleteOrderByIds(Arrays.asList(ids)));
    }

    /**
     * 导出订单列表
     */
    @SaCheckPermission("business:order:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BusOrderBo order, HttpServletResponse response) {
        orderService.export(order, response);
    }
}
```

## 🎨 前端开发实践

### 1. 新建页面模块

#### 步骤1: 创建页面目录
```bash
# 在 src/views 下创建业务模块
mkdir -p src/views/business/order
cd src/views/business/order
```

#### 步骤2: 创建页面文件
```typescript
// index.vue - 主页面
// order-drawer.vue - 编辑抽屉
// data.tsx - 表格配置
// api/index.ts - API接口
// model.d.ts - 类型定义
```

### 2. API接口开发

#### API接口定义
```typescript
// src/api/business/order/index.ts
import { request } from '#/api/request';
import type { OrderQuery, OrderForm, OrderInfo } from './model';

enum Api {
  OrderList = '/business/order/list',
  OrderInfo = '/business/order',
  OrderAdd = '/business/order',
  OrderEdit = '/business/order',
  OrderDelete = '/business/order',
  OrderExport = '/business/order/export',
}

/**
 * 查询订单列表
 */
export function getOrderList(params: OrderQuery) {
  return request.get<PageResult<OrderInfo>>({
    url: Api.OrderList,
    params,
  });
}

/**
 * 获取订单详情
 */
export function getOrderInfo(id: number) {
  return request.get<OrderInfo>({
    url: `${Api.OrderInfo}/${id}`,
  });
}

/**
 * 新增订单
 */
export function addOrder(data: OrderForm) {
  return request.post({
    url: Api.OrderAdd,
    data,
  });
}

/**
 * 修改订单
 */
export function updateOrder(data: OrderForm) {
  return request.put({
    url: Api.OrderEdit,
    data,
  });
}

/**
 * 删除订单
 */
export function deleteOrder(ids: number[]) {
  return request.delete({
    url: `${Api.OrderDelete}/${ids.join(',')}`,
  });
}

/**
 * 导出订单
 */
export function exportOrder(params: OrderQuery) {
  return request.post({
    url: Api.OrderExport,
    data: params,
    responseType: 'blob',
  });
}
```

#### 类型定义
```typescript
// src/api/business/order/model.d.ts
export interface OrderQuery extends PageQuery {
  orderNo?: string;
  status?: string;
  customerId?: number;
}

export interface OrderForm {
  id?: number;
  orderNo?: string;
  amount?: number;
  status?: string;
  customerId?: number;
  remark?: string;
}

export interface OrderInfo extends OrderForm {
  createTime?: string;
  updateTime?: string;
}
```

### 3. 页面组件开发

#### 列表页面
```vue
<!-- src/views/business/order/index.vue -->
<template>
  <div>
    <!-- 搜索区域 -->
    <div class="search-container">
      <a-form :model="queryParams" layout="inline">
        <a-form-item label="订单编号">
          <a-input v-model:value="queryParams.orderNo" placeholder="请输入订单编号" />
        </a-form-item>
        <a-form-item label="订单状态">
          <dict-select v-model:value="queryParams.status" dict-type="order_status" placeholder="请选择订单状态" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="handleQuery">搜索</a-button>
          <a-button @click="resetQuery">重置</a-button>
        </a-form-item>
      </a-form>
    </div>

    <!-- 操作按钮 -->
    <div class="action-container">
      <a-button type="primary" @click="handleAdd" v-auth="'business:order:add'">
        <plus-outlined />
        新增
      </a-button>
      <a-button type="danger" :disabled="!selectedRowKeys.length" @click="handleDelete" v-auth="'business:order:remove'">
        <delete-outlined />
        删除
      </a-button>
      <a-button @click="handleExport" v-auth="'business:order:export'">
        <download-outlined />
        导出
      </a-button>
    </div>

    <!-- 数据表格 -->
    <a-table
      :columns="columns"
      :data-source="dataList"
      :loading="loading"
      :pagination="pagination"
      :row-selection="rowSelection"
      @change="handleTableChange"
    >
      <template #status="{ record }">
        <dict-tag :value="record.status" dict-type="order_status" />
      </template>
      <template #amount="{ record }">
        <span>￥{{ record.amount }}</span>
      </template>
      <template #action="{ record }">
        <a-button type="link" @click="handleEdit(record)" v-auth="'business:order:edit'">编辑</a-button>
        <a-button type="link" danger @click="handleDelete([record.id])" v-auth="'business:order:remove'">删除</a-button>
      </template>
    </a-table>

    <!-- 编辑抽屉 -->
    <order-drawer
      v-model:visible="drawerVisible"
      :order-data="currentOrder"
      @success="handleSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue';
import { message, Modal } from 'ant-design-vue';
import { PlusOutlined, DeleteOutlined, DownloadOutlined } from '@ant-design/icons-vue';
import { getOrderList, deleteOrder, exportOrder } from '#/api/business/order';
import OrderDrawer from './order-drawer.vue';
import { columns } from './data';
import type { OrderQuery, OrderInfo } from '#/api/business/order/model';

// 响应式数据
const loading = ref(false);
const dataList = ref<OrderInfo[]>([]);
const selectedRowKeys = ref<number[]>([]);
const drawerVisible = ref(false);
const currentOrder = ref<OrderInfo | null>(null);

// 查询参数
const queryParams = reactive<OrderQuery>({
  pageNum: 1,
  pageSize: 10,
  orderNo: '',
  status: '',
});

// 分页配置
const pagination = reactive({
  current: 1,
  pageSize: 10,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
});

// 行选择配置
const rowSelection = {
  selectedRowKeys,
  onChange: (keys: number[]) => {
    selectedRowKeys.value = keys;
  },
};

// 获取列表数据
const getList = async () => {
  loading.value = true;
  try {
    const { data } = await getOrderList(queryParams);
    dataList.value = data.rows;
    pagination.total = data.total;
  } catch (error) {
    console.error('获取订单列表失败:', error);
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleQuery = () => {
  pagination.current = 1;
  queryParams.pageNum = 1;
  getList();
};

// 重置
const resetQuery = () => {
  Object.assign(queryParams, {
    pageNum: 1,
    pageSize: 10,
    orderNo: '',
    status: '',
  });
  handleQuery();
};

// 新增
const handleAdd = () => {
  currentOrder.value = null;
  drawerVisible.value = true;
};

// 编辑
const handleEdit = (record: OrderInfo) => {
  currentOrder.value = record;
  drawerVisible.value = true;
};

// 删除
const handleDelete = (ids: number[]) => {
  Modal.confirm({
    title: '确认删除',
    content: `确定要删除选中的${ids.length}条记录吗？`,
    async onOk() {
      try {
        await deleteOrder(ids);
        message.success('删除成功');
        selectedRowKeys.value = [];
        getList();
      } catch (error) {
        message.error('删除失败');
      }
    },
  });
};

// 导出
const handleExport = async () => {
  try {
    await exportOrder(queryParams);
    message.success('导出成功');
  } catch (error) {
    message.error('导出失败');
  }
};

// 表格变化
const handleTableChange = (pag: any) => {
  pagination.current = pag.current;
  pagination.pageSize = pag.pageSize;
  queryParams.pageNum = pag.current;
  queryParams.pageSize = pag.pageSize;
  getList();
};

// 操作成功回调
const handleSuccess = () => {
  drawerVisible.value = false;
  getList();
};

// 组件挂载
onMounted(() => {
  getList();
});
</script>
```

#### 编辑抽屉组件
```vue
<!-- src/views/business/order/order-drawer.vue -->
<template>
  <a-drawer
    :visible="visible"
    :title="isEdit ? '编辑订单' : '新增订单'"
    width="600"
    @close="handleClose"
  >
    <a-form
      ref="formRef"
      :model="formData"
      :rules="rules"
      layout="vertical"
    >
      <a-form-item label="订单编号" name="orderNo">
        <a-input v-model:value="formData.orderNo" placeholder="请输入订单编号" />
      </a-form-item>
      
      <a-form-item label="订单金额" name="amount">
        <a-input-number
          v-model:value="formData.amount"
          :min="0"
          :precision="2"
          style="width: 100%"
          placeholder="请输入订单金额"
        />
      </a-form-item>
      
      <a-form-item label="订单状态" name="status">
        <dict-select v-model:value="formData.status" dict-type="order_status" />
      </a-form-item>
      
      <a-form-item label="备注信息" name="remark">
        <a-textarea v-model:value="formData.remark" :rows="4" placeholder="请输入备注信息" />
      </a-form-item>
    </a-form>

    <template #footer>
      <a-button @click="handleClose">取消</a-button>
      <a-button type="primary" :loading="submitting" @click="handleSubmit">确定</a-button>
    </template>
  </a-drawer>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue';
import { message } from 'ant-design-vue';
import { addOrder, updateOrder } from '#/api/business/order';
import type { OrderForm, OrderInfo } from '#/api/business/order/model';

// Props
interface Props {
  visible: boolean;
  orderData?: OrderInfo | null;
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  orderData: null,
});

// Emits
const emit = defineEmits<{
  'update:visible': [value: boolean];
  success: [];
}>();

// 响应式数据
const formRef = ref();
const submitting = ref(false);

// 表单数据
const formData = reactive<OrderForm>({
  id: undefined,
  orderNo: '',
  amount: undefined,
  status: '',
  remark: '',
});

// 表单验证规则
const rules = {
  orderNo: [
    { required: true, message: '请输入订单编号', trigger: 'blur' },
    { max: 32, message: '订单编号不能超过32个字符', trigger: 'blur' },
  ],
  amount: [
    { required: true, message: '请输入订单金额', trigger: 'blur' },
    { type: 'number', min: 0, message: '订单金额不能小于0', trigger: 'blur' },
  ],
  status: [
    { required: true, message: '请选择订单状态', trigger: 'change' },
  ],
};

// 计算属性
const isEdit = computed(() => !!props.orderData?.id);

// 监听 orderData 变化
watch(
  () => props.orderData,
  (newData) => {
    if (newData) {
      Object.assign(formData, newData);
    } else {
      resetForm();
    }
  },
  { immediate: true }
);

// 重置表单
const resetForm = () => {
  Object.assign(formData, {
    id: undefined,
    orderNo: '',
    amount: undefined,
    status: '',
    remark: '',
  });
  formRef.value?.resetFields();
};

// 关闭抽屉
const handleClose = () => {
  emit('update:visible', false);
  resetForm();
};

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate();
    submitting.value = true;

    if (isEdit.value) {
      await updateOrder(formData);
      message.success('修改成功');
    } else {
      await addOrder(formData);
      message.success('新增成功');
    }

    emit('success');
  } catch (error) {
    console.error('提交失败:', error);
  } finally {
    submitting.value = false;
  }
};
</script>
```

### 4. 表格配置文件
```typescript
// src/views/business/order/data.tsx
import type { TableColumnProps } from 'ant-design-vue';
import { Tag } from 'ant-design-vue';

export const columns: TableColumnProps[] = [
  {
    title: '订单编号',
    dataIndex: 'orderNo',
    key: 'orderNo',
    width: 150,
  },
  {
    title: '订单金额',
    dataIndex: 'amount',
    key: 'amount',
    width: 120,
    slots: { customRender: 'amount' },
  },
  {
    title: '订单状态',
    dataIndex: 'status',
    key: 'status',
    width: 100,
    slots: { customRender: 'status' },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime',
    width: 160,
  },
  {
    title: '操作',
    key: 'action',
    width: 120,
    fixed: 'right',
    slots: { customRender: 'action' },
  },
];
```

## 🔧 开发工具和插件

### 推荐开发工具

#### 代码生成器使用
```bash
# 1. 访问代码生成器页面
http://localhost:5173/tool/gen

# 2. 导入数据库表
点击"导入"按钮，选择要生成代码的表

# 3. 配置生成参数
- 基本信息: 包名、作者、表注释等
- 字段信息: 字段类型、字典类型、校验规则等
- 生成信息: 生成模板、生成路径等

# 4. 预览和下载代码
点击"预览"查看生成的代码
点击"生成代码"下载代码包
```

#### Maven Archetype (自定义)
```bash
# 创建自定义Maven原型
mvn archetype:create-from-project

# 使用原型创建新模块
mvn archetype:generate \
  -DarchetypeGroupId=org.niko \
  -DarchetypeArtifactId=niko-module-archetype \
  -DgroupId=org.niko \
  -DartifactId=niko-new-module
```

### VS Code 扩展配置
```json
// .vscode/settings.json
{
  // Vue 开发配置
  "vetur.validation.template": false,
  "vetur.validation.script": false,
  "vetur.validation.style": false,
  
  // TypeScript 配置
  "typescript.preferences.quoteStyle": "single",
  "typescript.preferences.includePackageJsonAutoImports": "auto",
  
  // 格式化配置
  "editor.formatOnSave": true,
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": true
  },
  
  // 文件关联
  "files.associations": {
    "*.vue": "vue"
  }
}
```

## 🔐 权限和安全

### 权限控制实现

#### 后端权限注解
```java
// 权限验证
@SaCheckPermission("business:order:list")    // 检查权限
@SaCheckRole("admin")                         // 检查角色
@SaCheckLogin                                 // 检查登录

// 数据权限
@DataPermission(deptAlias = "d")             // 部门数据权限
@TenantIgnore                                // 忽略租户隔离

// 操作日志
@Log(title = "订单管理", businessType = BusinessType.INSERT)

// 防重复提交
@RepeatSubmit(interval = 5000)               // 5秒内不允许重复提交

// 限流控制
@RateLimiter(count = 100, time = 60)         // 1分钟最多100次请求
```

#### 前端权限控制
```typescript
// 权限指令
v-auth="'business:order:add'"               // 权限指令
v-role="'admin'"                            // 角色指令

// 权限函数
import { usePermission } from '@/hooks/usePermission';

const { hasPermission, hasRole } = usePermission();

if (hasPermission('business:order:add')) {
  // 有权限的操作
}
```

### 数据安全配置

#### 字段加密
```java
@EncryptField(algorithm = EncryptAlgorithm.AES)
private String sensitiveData;  // 自动加密存储

@SensitiveField(SensitiveStrategy.PHONE)
private String phone;  // 自动脱敏显示
```

#### API加密
```yaml
# application.yml
api-decrypt:
  enabled: true                    # 开启API加密
  header-flag: encrypt-key         # 加密标识头
  public-key: MFwwDQYJKoZI...      # RSA公钥
  private-key: MIIBVAIBADANBg... # RSA私钥
```

## 📊 性能优化实践

### 数据库优化

#### 1. 索引优化
```sql
-- 为经常查询的字段添加索引
CREATE INDEX idx_order_no ON bus_order(order_no);
CREATE INDEX idx_create_time ON bus_order(create_time);
CREATE INDEX idx_status_customer ON bus_order(status, customer_id);
```

#### 2. 查询优化
```java
// 分页查询优化
@Override
public TableDataInfo<BusOrderVo> selectOrderList(BusOrderBo order, PageQuery pageQuery) {
    // 使用 MyBatis-Plus 分页插件
    Page<BusOrder> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
    
    // 构建查询条件
    LambdaQueryWrapper<BusOrder> wrapper = Wrappers.lambdaQueryWrapper(BusOrder.class)
        .eq(StringUtils.isNotBlank(order.getOrderNo()), BusOrder::getOrderNo, order.getOrderNo())
        .eq(StringUtils.isNotBlank(order.getStatus()), BusOrder::getStatus, order.getStatus())
        .orderByDesc(BusOrder::getCreateTime);
    
    // 执行查询
    Page<BusOrderVo> voPage = baseMapper.selectVoPage(page, wrapper);
    return TableDataInfo.build(voPage);
}
```

### 缓存优化

#### Redis 缓存使用
```java
@Service
public class BusOrderServiceImpl implements IBusOrderService {

    @Autowired
    private RedisUtils redisUtils;

    @Override  
    public BusOrderVo selectOrderById(Long id) {
        String cacheKey = "order:" + id;
        
        // 先从缓存获取
        BusOrderVo cached = redisUtils.getCacheObject(cacheKey);
        if (cached != null) {
            return cached;
        }
        
        // 从数据库查询
        BusOrderVo order = baseMapper.selectVoById(id);
        if (order != null) {
            // 缓存结果，有效期1小时
            redisUtils.setCacheObject(cacheKey, order, Duration.ofHours(1));
        }
        
        return order;
    }
}
```

### 前端性能优化

#### 1. 组件懒加载
```typescript
// 路由懒加载
const routes = [
  {
    path: '/business/order',
    component: () => import('@/views/business/order/index.vue'),
  },
];

// 组件懒加载
const OrderDrawer = defineAsyncComponent(() => import('./order-drawer.vue'));
```

#### 2. 列表虚拟化
```vue
<template>
  <a-table
    :scroll="{ y: 400 }"
    :virtual="true"
    :data-source="largeDataList"
    :columns="columns"
  />
</template>
```

## 🚀 部署和运维

### Docker 部署

#### Dockerfile (后端)
```dockerfile
# niko-admin/Dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/niko-admin.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Dockerfile (前端)  
```dockerfile
# niko-ui/Dockerfile
FROM node:20-alpine as builder

WORKDIR /app
COPY . .
RUN pnpm install && pnpm build:antd

FROM nginx:alpine
COPY --from=builder /app/apps/web-antd/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
```

#### Docker Compose 部署
```yaml
# docker-compose.prod.yml
version: '3.8'

services:
  niko-admin:
    build: ./niko-admin
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
    depends_on:
      - postgres
      - redis

  niko-ui:
    build: ./niko-ui
    ports:
      - "80:80"
    depends_on:
      - niko-admin

  postgres:
    image: postgres:14.2
    environment:
      POSTGRES_DB: niko_admin
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7.2.8
    command: redis-server --appendonly yes
    volumes:
      - redis_data:/data

volumes:
  postgres_data:
  redis_data:
```

## 🔍 调试和测试

### 单元测试示例

#### 后端单元测试
```java
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class BusOrderServiceTest {

    @Autowired
    private IBusOrderService orderService;

    @Test
    @Order(1)
    void testInsertOrder() {
        BusOrderBo order = new BusOrderBo();
        order.setOrderNo("TEST001");
        order.setAmount(new BigDecimal("100.00"));
        order.setStatus("0");
        
        Boolean result = orderService.insertOrder(order);
        assertTrue(result);
        assertNotNull(order.getId());
    }

    @Test
    @Order(2)
    void testSelectOrderById() {
        BusOrderVo order = orderService.selectOrderById(1L);
        assertNotNull(order);
        assertEquals("TEST001", order.getOrderNo());
    }
}
```

#### 前端单元测试
```typescript
// tests/order.test.ts
import { describe, it, expect, vi } from 'vitest';
import { mount } from '@vue/test-utils';
import OrderDrawer from '@/views/business/order/order-drawer.vue';

describe('OrderDrawer', () => {
  it('should render correctly', () => {
    const wrapper = mount(OrderDrawer, {
      props: {
        visible: true,
        orderData: null,
      },
    });

    expect(wrapper.find('.ant-drawer-title').text()).toBe('新增订单');
  });

  it('should validate form', async () => {
    const wrapper = mount(OrderDrawer, {
      props: {
        visible: true,
        orderData: null,
      },
    });

    // 模拟表单提交
    await wrapper.find('button[type="primary"]').trigger('click');
    
    // 验证错误信息
    expect(wrapper.find('.ant-form-item-explain-error').exists()).toBe(true);
  });
});
```

### API 接口测试

#### Postman 测试集合
```json
{
  "info": {
    "name": "Niko-Admin API Tests",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "登录获取Token",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Content-Type",
            "value": "application/json"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"username\": \"admin\",\n  \"password\": \"admin123\"\n}"
        },
        "url": {
          "raw": "{{base_url}}/auth/login",
          "host": ["{{base_url}}"],
          "path": ["auth", "login"]
        }
      }
    },
    {
      "name": "订单列表查询",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "{{token}}"
          }
        ],
        "url": {
          "raw": "{{base_url}}/business/order/list?pageNum=1&pageSize=10",
          "host": ["{{base_url}}"],
          "path": ["business", "order", "list"],
          "query": [
            {
              "key": "pageNum",
              "value": "1"
            },
            {
              "key": "pageSize", 
              "value": "10"
            }
          ]
        }
      }
    }
  ]
}
```

---

> 📝 **说明**: 这是 Niko-Admin 的详细二次开发指南，涵盖了从后端到前端的完整开发流程。
> 📖 **下一步**: 查看 `niko-admin-learning-path.md` 了解系统性的学习路径建议。