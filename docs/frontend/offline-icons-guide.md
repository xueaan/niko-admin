# Niko-Admin 内网离线图标部署完整指南

## 概述

本指南提供了Niko-Admin在内网环境下的图标系统离线部署解决方案，确保在无外网连接的情况下，图标系统能够正常工作。

## 问题分析

### 现有系统的内网部署问题

1. **图标选择器无法工作**
   - 依赖 `https://api.iconify.design` API
   - 无法获取图标集列表和图标数据
   - 图标搜索功能失效

2. **动态图标加载失败**
   - 运行时动态加载的图标无法获取
   - 只能使用预置的离线图标

3. **开发依赖冗余**
   - 30+个 `@iconify/icons-*` 包作为devDependencies
   - 安装时间长，占用存储空间大

## 解决方案对比

| 方案 | 优势 | 劣势 | 适用场景 |
|------|------|------|----------|
| 完全离线模式 | 无网络依赖，启动快 | 图标数量受限，扩展性差 | 小型项目，图标需求固定 |
| 内网图标服务器 | 功能完整，易迁移 | 需要额外服务器资源 | 大型企业，有运维能力 |
| 混合部署模式 | 灵活性强，渐进升级 | 配置复杂，维护成本高 | 中型项目，需求变化大 |

## 方案一：完全离线模式（推荐）

### 1.1 实施步骤

#### 步骤1：分析图标使用情况

```bash
# 扫描项目中使用的图标
cd niko-ui
grep -r "icon=" . --include="*.vue" --include="*.ts" --include="*.js" | grep -o 'icon="[^"]*"' | sort | uniq > used-icons.txt

# 分析图标集使用频率
cat used-icons.txt | cut -d':' -f1 | sed 's/icon="//g' | sort | uniq -c | sort -nr
```

#### 步骤2：扩展离线图标库

创建自定义离线图标注册文件：

**文件位置：** `packages/icons/src/iconify-offline/custom-offline-icons.ts`

```typescript
import { addIcon } from '@iconify/vue';
import { defineComponent, h } from 'vue';
import type { IconifyIcon } from '@iconify/types';

// 导入常用图标数据
import homeIcon from '@iconify/icons-ant-design/home-outlined';
import userIcon from '@iconify/icons-ant-design/user-outlined';
import settingIcon from '@iconify/icons-ant-design/setting-outlined';
import menuIcon from '@iconify/icons-ant-design/menu-outlined';
import searchIcon from '@iconify/icons-ant-design/search-outlined';
import editIcon from '@iconify/icons-ant-design/edit-outlined';
import deleteIcon from '@iconify/icons-ant-design/delete-outlined';
import plusIcon from '@iconify/icons-ant-design/plus-outlined';
import minusIcon from '@iconify/icons-ant-design/minus-outlined';
import closeIcon from '@iconify/icons-ant-design/close-outlined';

// MDI常用图标
import mdiAccount from '@iconify/icons-mdi/account';
import mdiAccountGroup from '@iconify/icons-mdi/account-group';
import mdiAccountSettings from '@iconify/icons-mdi/account-settings';
import mdiDatabase from '@iconify/icons-mdi/database';
import mdiServer from '@iconify/icons-mdi/server';
import mdiCloud from '@iconify/icons-mdi/cloud';
import mdiMonitor from '@iconify/icons-mdi/monitor';
import mdiChart from '@iconify/icons-mdi/chart-line';
import mdiTable from '@iconify/icons-mdi/table';
import mdiFile from '@iconify/icons-mdi/file';

// Heroicons常用图标
import heroHome from '@iconify/icons-heroicons/home';
import heroUser from '@iconify/icons-heroicons/user';
import heroCog from '@iconify/icons-heroicons/cog-6-tooth';
import heroDocument from '@iconify/icons-heroicons/document';
import heroFolder from '@iconify/icons-heroicons/folder';

// 批量图标注册配置
const OFFLINE_ICON_MAP = {
  // Ant Design Icons
  'ant-design:home-outlined': homeIcon,
  'ant-design:user-outlined': userIcon,
  'ant-design:setting-outlined': settingIcon,
  'ant-design:menu-outlined': menuIcon,
  'ant-design:search-outlined': searchIcon,
  'ant-design:edit-outlined': editIcon,
  'ant-design:delete-outlined': deleteIcon,
  'ant-design:plus-outlined': plusIcon,
  'ant-design:minus-outlined': minusIcon,
  'ant-design:close-outlined': closeIcon,
  
  // MDI Icons
  'mdi:account': mdiAccount,
  'mdi:account-group': mdiAccountGroup,
  'mdi:account-settings': mdiAccountSettings,
  'mdi:database': mdiDatabase,
  'mdi:server': mdiServer,
  'mdi:cloud': mdiCloud,
  'mdi:monitor': mdiMonitor,
  'mdi:chart-line': mdiChart,
  'mdi:table': mdiTable,
  'mdi:file': mdiFile,
  
  // Heroicons
  'heroicons:home': heroHome,
  'heroicons:user': heroUser,
  'heroicons:cog-6-tooth': heroCog,
  'heroicons:document': heroDocument,
  'heroicons:folder': heroFolder,
} as const;

// 批量注册离线图标
export function registerOfflineIcons() {
  Object.entries(OFFLINE_ICON_MAP).forEach(([name, iconData]) => {
    addIcon(name, iconData);
  });
  
  console.log(`已注册 ${Object.keys(OFFLINE_ICON_MAP).length} 个离线图标`);
}

// 获取所有离线图标名称
export function getOfflineIconNames(): string[] {
  return Object.keys(OFFLINE_ICON_MAP);
}

// 检查图标是否为离线图标
export function isOfflineIcon(iconName: string): boolean {
  return iconName in OFFLINE_ICON_MAP;
}

// 创建离线图标组件工厂
function createOfflineIconComponent(iconName: string, iconData: IconifyIcon) {
  return defineComponent({
    name: `OfflineIcon-${iconName.replace(':', '-')}`,
    setup(props, { attrs }) {
      addIcon(iconName, iconData);
      return () => h('span', {
        class: 'offline-icon',
        innerHTML: `<svg viewBox="0 0 ${iconData.width || 24} ${iconData.height || 24}">${iconData.body}</svg>`,
        ...attrs
      });
    },
  });
}

// 导出预定义的离线图标组件
export const OfflineIcons = Object.entries(OFFLINE_ICON_MAP).reduce((acc, [name, data]) => {
  const componentName = name.replace(/[:-]/g, '').replace(/^\w/, c => c.toUpperCase());
  acc[componentName] = createOfflineIconComponent(name, data);
  return acc;
}, {} as Record<string, any>);
```

#### 步骤3：替换图标选择器为离线版本

**文件位置：** `packages/effects/common-ui/src/components/icon-picker/offline-icon-picker.vue`

```vue
<template>
  <div class="offline-icon-picker">
    <!-- 图标集选择 -->
    <Select v-model:value="selectedCollection" @change="handleCollectionChange">
      <SelectOption v-for="collection in availableCollections" :key="collection.prefix" :value="collection.prefix">
        {{ collection.name }} ({{ collection.count }})
      </SelectOption>
    </Select>
    
    <!-- 搜索框 -->
    <Input 
      v-model:value="searchTerm" 
      :placeholder="$t('common.search')" 
      @input="handleSearch"
      :suffix-icon="SearchOutlined"
    />
    
    <!-- 图标网格 -->
    <div class="icon-grid">
      <div 
        v-for="icon in filteredIcons" 
        :key="icon"
        :class="['icon-item', { active: selectedIcon === icon }]"
        @click="handleIconSelect(icon)"
        :title="icon"
      >
        <Icon :icon="icon" class="icon-preview" />
        <span class="icon-name">{{ getIconDisplayName(icon) }}</span>
      </div>
    </div>
    
    <!-- 分页 -->
    <Pagination 
      v-model:current="currentPage"
      :total="totalIcons"
      :page-size="pageSize"
      :show-size-changer="false"
      :show-quick-jumper="false"
      size="small"
    />
    
    <!-- 统计信息 -->
    <div class="icon-stats">
      共 {{ totalIcons }} 个图标 (离线模式)
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watch, onMounted } from 'vue';
import { Select, SelectOption, Input, Pagination } from 'ant-design-vue';
import { SearchOutlined } from '@ant-design/icons-vue';
import { Icon } from '@vben/icons';
import { getOfflineIconNames } from '../../iconify-offline/custom-offline-icons';

interface Props {
  modelValue?: string;
}

interface Emits {
  (e: 'update:modelValue', value: string): void;
  (e: 'select', value: string): void;
}

const props = defineProps<Props>();
const emit = defineEmits<Emits>();

// 响应式数据
const searchTerm = ref('');
const selectedCollection = ref('all');
const currentPage = ref(1);
const pageSize = 24;
const selectedIcon = ref(props.modelValue || '');

// 获取所有离线图标
const allOfflineIcons = ref<string[]>([]);

// 图标集配置
const availableCollections = computed(() => {
  const collections = new Map<string, { name: string; count: number }>();
  
  allOfflineIcons.value.forEach(icon => {
    const [prefix] = icon.split(':');
    if (!collections.has(prefix)) {
      collections.set(prefix, { name: getCollectionName(prefix), count: 0 });
    }
    collections.get(prefix)!.count++;
  });
  
  return [
    { prefix: 'all', name: '全部', count: allOfflineIcons.value.length },
    ...Array.from(collections.entries()).map(([prefix, info]) => ({
      prefix,
      ...info
    }))
  ];
});

// 过滤后的图标列表
const filteredIcons = computed(() => {
  let icons = allOfflineIcons.value;
  
  // 按图标集过滤
  if (selectedCollection.value !== 'all') {
    icons = icons.filter(icon => icon.startsWith(`${selectedCollection.value}:`));
  }
  
  // 按搜索词过滤
  if (searchTerm.value) {
    const term = searchTerm.value.toLowerCase();
    icons = icons.filter(icon => 
      icon.toLowerCase().includes(term) || 
      getIconDisplayName(icon).toLowerCase().includes(term)
    );
  }
  
  // 分页
  const start = (currentPage.value - 1) * pageSize;
  const end = start + pageSize;
  return icons.slice(start, end);
});

// 总图标数
const totalIcons = computed(() => {
  let icons = allOfflineIcons.value;
  
  if (selectedCollection.value !== 'all') {
    icons = icons.filter(icon => icon.startsWith(`${selectedCollection.value}:`));
  }
  
  if (searchTerm.value) {
    const term = searchTerm.value.toLowerCase();
    icons = icons.filter(icon => 
      icon.toLowerCase().includes(term) || 
      getIconDisplayName(icon).toLowerCase().includes(term)
    );
  }
  
  return icons.length;
});

// 获取图标集名称
function getCollectionName(prefix: string): string {
  const names: Record<string, string> = {
    'ant-design': 'Ant Design',
    'mdi': 'Material Design Icons',
    'heroicons': 'Heroicons',
    'bi': 'Bootstrap Icons',
    'fa': 'Font Awesome',
    'tabler': 'Tabler Icons',
    'carbon': 'Carbon Icons',
    'feather': 'Feather Icons'
  };
  return names[prefix] || prefix.toUpperCase();
}

// 获取图标显示名称
function getIconDisplayName(icon: string): string {
  const [, name] = icon.split(':');
  return name.replace(/-/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
}

// 处理图标集切换
function handleCollectionChange() {
  currentPage.value = 1;
  searchTerm.value = '';
}

// 处理搜索
function handleSearch() {
  currentPage.value = 1;
}

// 处理图标选择
function handleIconSelect(icon: string) {
  selectedIcon.value = icon;
  emit('update:modelValue', icon);
  emit('select', icon);
}

// 监听外部值变化
watch(() => props.modelValue, (newValue) => {
  selectedIcon.value = newValue || '';
});

// 组件挂载时加载离线图标
onMounted(() => {
  allOfflineIcons.value = getOfflineIconNames();
});
</script>

<style scoped>
.offline-icon-picker {
  @apply space-y-4;
}

.icon-grid {
  @apply grid grid-cols-6 gap-2 max-h-96 overflow-y-auto;
}

.icon-item {
  @apply flex flex-col items-center p-2 rounded cursor-pointer hover:bg-gray-100 transition-colors;
  @apply border border-transparent;
}

.icon-item.active {
  @apply border-blue-500 bg-blue-50;
}

.icon-preview {
  @apply w-6 h-6 mb-1;
}

.icon-name {
  @apply text-xs text-gray-600 text-center truncate w-full;
}

.icon-stats {
  @apply text-sm text-gray-500 text-center;
}
</style>
```

#### 步骤4：修改图标组件支持离线模式

**文件位置：** `packages/@core/ui-kit/shadcn-ui/src/components/icon/offline-icon.vue`

```vue
<template>
  <!-- 离线图标优先显示 -->
  <component :is="icon as Component" v-if="isComponent" v-bind="$attrs" />
  <span v-else-if="isOfflineIcon && offlineIconData" 
        class="offline-icon" 
        :innerHTML="renderOfflineIcon" 
        v-bind="$attrs" />
  <img v-else-if="isRemoteIcon" :src="icon as string" v-bind="$attrs" />
  <IconifyIcon v-else-if="icon" v-bind="$attrs" :icon="icon as string" />
  <IconDefault v-else-if="fallback" v-bind="$attrs" />
  <div v-else class="icon-placeholder" v-bind="$attrs">
    <span class="icon-missing">?</span>
    <span class="icon-tooltip">{{ icon }}</span>
  </div>
</template>

<script setup lang="ts">
import type { Component } from 'vue';
import { computed } from 'vue';
import { Icon as IconifyIcon } from '@iconify/vue';
import { isOfflineIcon as checkOfflineIcon, getOfflineIconNames } from '@vben/icons';

interface Props {
  icon?: Component | string;
  fallback?: boolean;
}

const props = withDefaults(defineProps<Props>(), {
  fallback: false,
});

// 判断是否为Vue组件
const isComponent = computed(() => {
  return typeof props.icon === 'object' && props.icon !== null;
});

// 判断是否为远程图片
const isRemoteIcon = computed(() => {
  return typeof props.icon === 'string' && /^https?:\/\//.test(props.icon);
});

// 判断是否为离线图标
const isOfflineIcon = computed(() => {
  return typeof props.icon === 'string' && checkOfflineIcon(props.icon);
});

// 获取离线图标数据
const offlineIconData = computed(() => {
  if (!isOfflineIcon.value || typeof props.icon !== 'string') return null;
  
  // 从Iconify缓存中获取图标数据
  try {
    const iconData = window.Iconify?.getIcon?.(props.icon);
    return iconData;
  } catch (error) {
    console.warn(`Failed to get offline icon data for: ${props.icon}`, error);
    return null;
  }
});

// 渲染离线图标SVG
const renderOfflineIcon = computed(() => {
  if (!offlineIconData.value) return '';
  
  const { body, width = 24, height = 24 } = offlineIconData.value;
  return `<svg viewBox="0 0 ${width} ${height}" fill="currentColor">${body}</svg>`;
});
</script>

<style scoped>
.offline-icon {
  @apply inline-flex items-center justify-center;
}

.offline-icon svg {
  @apply w-full h-full;
}

.icon-placeholder {
  @apply relative inline-flex items-center justify-center w-6 h-6 bg-gray-200 rounded;
}

.icon-missing {
  @apply text-gray-500 text-sm font-bold;
}

.icon-tooltip {
  @apply absolute bottom-full left-1/2 transform -translate-x-1/2 bg-black text-white text-xs px-2 py-1 rounded opacity-0 pointer-events-none;
  @apply group-hover:opacity-100 transition-opacity;
}
</style>
```

#### 步骤5：创建图标管理工具

**文件位置：** `scripts/icon-management/offline-icon-tool.js`

```javascript
const fs = require('fs');
const path = require('path');
const { execSync } = require('child_process');

class OfflineIconTool {
  constructor() {
    this.projectRoot = path.resolve(__dirname, '../..');
    this.iconsDir = path.join(this.projectRoot, 'packages/icons/src/iconify-offline');
    this.usedIconsFile = path.join(__dirname, 'used-icons.json');
    this.configFile = path.join(__dirname, 'icon-config.json');
  }

  // 扫描项目中使用的图标
  scanUsedIcons() {
    console.log('扫描项目中使用的图标...');
    
    const usedIcons = new Set();
    const searchPaths = [
      'packages/**/*.vue',
      'packages/**/*.ts', 
      'packages/**/*.js',
      'apps/**/*.vue',
      'apps/**/*.ts',
      'apps/**/*.js'
    ];
    
    searchPaths.forEach(pattern => {
      try {
        const files = execSync(`find ${this.projectRoot} -path "*/node_modules" -prune -o -name "${pattern.split('/').pop()}" -print`)
          .toString().split('\n').filter(Boolean);
        
        files.forEach(file => {
          const content = fs.readFileSync(file, 'utf8');
          
          // 匹配 icon="xxx:xxx" 格式
          const iconMatches = content.match(/icon=["']([^"']+:+[^"']+)["']/g);
          if (iconMatches) {
            iconMatches.forEach(match => {
              const icon = match.match(/icon=["']([^"']+)["']/)?.[1];
              if (icon && icon.includes(':')) {
                usedIcons.add(icon);
              }
            });
          }
          
          // 匹配 'xxx:xxx' 字符串格式
          const stringMatches = content.match(/['"]([a-z0-9-]+:[a-z0-9-]+)['\"]/g);
          if (stringMatches) {
            stringMatches.forEach(match => {
              const icon = match.match(/['"]([a-z0-9-]+:[a-z0-9-]+)['\"]/)?.[1];
              if (icon) {
                usedIcons.add(icon);
              }
            });
          }
        });
      } catch (error) {
        console.warn(`扫描失败: ${pattern}`, error.message);
      }
    });
    
    const usedIconsList = Array.from(usedIcons).sort();
    fs.writeFileSync(this.usedIconsFile, JSON.stringify(usedIconsList, null, 2));
    
    console.log(`发现 ${usedIconsList.length} 个使用的图标`);
    return usedIconsList;
  }

  // 分析图标集使用情况
  analyzeIconUsage(usedIcons = null) {
    if (!usedIcons) {
      if (fs.existsSync(this.usedIconsFile)) {
        usedIcons = JSON.parse(fs.readFileSync(this.usedIconsFile, 'utf8'));
      } else {
        usedIcons = this.scanUsedIcons();
      }
    }
    
    const prefixStats = {};
    usedIcons.forEach(icon => {
      const [prefix] = icon.split(':');
      prefixStats[prefix] = (prefixStats[prefix] || 0) + 1;
    });
    
    console.log('\n图标集使用统计:');
    Object.entries(prefixStats)
      .sort(([,a], [,b]) => b - a)
      .forEach(([prefix, count]) => {
        console.log(`  ${prefix}: ${count} 个图标`);
      });
    
    return prefixStats;
  }

  // 生成离线图标代码
  generateOfflineIcons(usedIcons = null) {
    if (!usedIcons) {
      if (fs.existsSync(this.usedIconsFile)) {
        usedIcons = JSON.parse(fs.readFileSync(this.usedIconsFile, 'utf8'));
      } else {
        usedIcons = this.scanUsedIcons();
      }
    }
    
    console.log('生成离线图标代码...');
    
    // 按图标集分组
    const iconsByPrefix = {};
    usedIcons.forEach(icon => {
      const [prefix, name] = icon.split(':');
      if (!iconsByPrefix[prefix]) {
        iconsByPrefix[prefix] = [];
      }
      iconsByPrefix[prefix].push({ icon, name });
    });
    
    // 生成导入语句
    const imports = [];
    const iconMap = [];
    let componentIndex = 0;
    
    Object.entries(iconsByPrefix).forEach(([prefix, icons]) => {
      icons.forEach(({ icon, name }) => {
        const componentName = `icon${componentIndex++}`;
        const packageName = `@iconify/icons-${prefix}`;
        const iconFileName = name.replace(/([A-Z])/g, '-$1').toLowerCase();
        
        imports.push(`import ${componentName} from '${packageName}/${iconFileName}';`);
        iconMap.push(`  '${icon}': ${componentName},`);
      });
    });
    
    // 生成完整代码
    const code = `import { addIcon } from '@iconify/vue';
import { defineComponent, h } from 'vue';
import type { IconifyIcon } from '@iconify/types';

// 自动生成的离线图标导入
${imports.join('\n')}

// 离线图标映射表
const OFFLINE_ICON_MAP = {
${iconMap.join('\n')}
} as const;

// 批量注册离线图标
export function registerOfflineIcons() {
  Object.entries(OFFLINE_ICON_MAP).forEach(([name, iconData]) => {
    addIcon(name, iconData);
  });
  
  console.log(\`已注册 \${Object.keys(OFFLINE_ICON_MAP).length} 个离线图标\`);
}

// 获取所有离线图标名称
export function getOfflineIconNames(): string[] {
  return Object.keys(OFFLINE_ICON_MAP);
}

// 检查图标是否为离线图标
export function isOfflineIcon(iconName: string): boolean {
  return iconName in OFFLINE_ICON_MAP;
}

// 获取离线图标数据
export function getOfflineIconData(iconName: string): IconifyIcon | null {
  return OFFLINE_ICON_MAP[iconName as keyof typeof OFFLINE_ICON_MAP] || null;
}
`;
    
    const outputFile = path.join(this.iconsDir, 'auto-generated-offline-icons.ts');
    fs.writeFileSync(outputFile, code);
    
    console.log(`生成离线图标文件: ${outputFile}`);
    console.log(`包含 ${usedIcons.length} 个图标`);
    
    return outputFile;
  }

  // 清理未使用的图标依赖
  cleanUnusedDependencies() {
    console.log('分析并清理未使用的图标依赖...');
    
    const packageJsonPath = path.join(this.projectRoot, 'package.json');
    const packageJson = JSON.parse(fs.readFileSync(packageJsonPath, 'utf8'));
    
    const usedIcons = JSON.parse(fs.readFileSync(this.usedIconsFile, 'utf8'));
    const usedPrefixes = new Set(usedIcons.map(icon => icon.split(':')[0]));
    
    const iconDeps = Object.keys(packageJson.devDependencies || {})
      .filter(dep => dep.startsWith('@iconify/icons-'));
    
    const unusedDeps = iconDeps.filter(dep => {
      const prefix = dep.replace('@iconify/icons-', '');
      return !usedPrefixes.has(prefix);
    });
    
    if (unusedDeps.length > 0) {
      console.log(`发现 ${unusedDeps.length} 个未使用的图标依赖:`);
      unusedDeps.forEach(dep => console.log(`  - ${dep}`));
      
      // 生成清理脚本
      const cleanScript = `#!/bin/bash
# 清理未使用的图标依赖
${unusedDeps.map(dep => `npm uninstall ${dep}`).join('\n')}
`;
      
      fs.writeFileSync(path.join(__dirname, 'clean-deps.sh'), cleanScript);
      console.log('生成清理脚本: scripts/icon-management/clean-deps.sh');
    } else {
      console.log('未发现未使用的图标依赖');
    }
  }

  // 生成配置报告
  generateReport() {
    const usedIcons = JSON.parse(fs.readFileSync(this.usedIconsFile, 'utf8'));
    const prefixStats = this.analyzeIconUsage(usedIcons);
    
    const report = {
      timestamp: new Date().toISOString(),
      totalIcons: usedIcons.length,
      iconSets: Object.keys(prefixStats).length,
      prefixStats,
      topIcons: usedIcons.slice(0, 20),
      recommendations: {
        shouldUseOfflineMode: usedIcons.length < 200,
        suggestedCacheSize: Math.ceil(usedIcons.length * 1.5),
        criticalIconSets: Object.entries(prefixStats)
          .filter(([, count]) => count >= 5)
          .map(([prefix]) => prefix)
      }
    };
    
    fs.writeFileSync(
      path.join(__dirname, 'icon-analysis-report.json'), 
      JSON.stringify(report, null, 2)
    );
    
    console.log('\n=== 图标分析报告 ===');
    console.log(`总图标数: ${report.totalIcons}`);
    console.log(`图标集数: ${report.iconSets}`);
    console.log(`建议离线模式: ${report.recommendations.shouldUseOfflineMode ? '是' : '否'}`);
    console.log(`关键图标集: ${report.recommendations.criticalIconSets.join(', ')}`);
    
    return report;
  }
}

// CLI 接口
if (require.main === module) {
  const tool = new OfflineIconTool();
  const command = process.argv[2];
  
  switch (command) {
    case 'scan':
      tool.scanUsedIcons();
      break;
    case 'analyze':
      tool.analyzeIconUsage();
      break;
    case 'generate':
      tool.generateOfflineIcons();
      break;
    case 'clean':
      tool.cleanUnusedDependencies();
      break;
    case 'report':
      tool.generateReport();
      break;
    case 'all':
      tool.scanUsedIcons();
      tool.generateOfflineIcons();
      tool.cleanUnusedDependencies();
      tool.generateReport();
      break;
    default:
      console.log(`
图标管理工具使用说明:

  node offline-icon-tool.js scan      # 扫描项目中使用的图标
  node offline-icon-tool.js analyze   # 分析图标使用情况
  node offline-icon-tool.js generate  # 生成离线图标代码
  node offline-icon-tool.js clean     # 清理未使用的依赖
  node offline-icon-tool.js report    # 生成分析报告
  node offline-icon-tool.js all       # 执行所有步骤
`);
  }
}

module.exports = OfflineIconTool;
```

### 1.2 配置修改

#### 修改Vite配置支持离线模式

**文件位置：** `internal/vite-config/src/config/application.ts`

```typescript
// 添加离线图标构建配置
export function createApplicationConfig(
  userOptions: ApplicationPluginOptions = {},
): ApplicationConfig {
  const { isBuild, env } = userOptions;
  
  return {
    // ... 其他配置
    
    define: {
      // 定义离线模式标志
      __OFFLINE_ICONS_MODE__: JSON.stringify(env.VITE_OFFLINE_ICONS_MODE === 'true'),
      __ICONIFY_API_URL__: JSON.stringify(env.VITE_ICONIFY_API_URL || 'https://api.iconify.design'),
    },
    
    build: {
      rollupOptions: {
        external: isBuild && env.VITE_OFFLINE_ICONS_MODE === 'true' 
          ? ['@iconify/json'] // 在离线模式下排除大型JSON文件
          : [],
      },
    },
    
    // ... 其他配置
  };
}
```

#### 修改环境变量配置

**文件位置：** `.env.development`

```bash
# 开发环境 - 混合模式
VITE_OFFLINE_ICONS_MODE=false
VITE_ICONIFY_API_URL=https://api.iconify.design

# 启用图标调试
VITE_ICON_DEBUG=true
```

**文件位置：** `.env.production`

```bash
# 生产环境 - 离线模式
VITE_OFFLINE_ICONS_MODE=true

# 如果有内网图标服务器，可以配置
# VITE_ICONIFY_API_URL=http://internal-iconify-server:3000
```

## 方案二：内网图标服务器

### 2.1 Docker部署方案

#### 创建图标服务器

**文件位置：** `docker/iconify-server/Dockerfile`

```dockerfile
FROM node:18-alpine

WORKDIR /app

# 安装依赖
COPY package*.json ./
RUN npm ci --only=production

# 复制应用代码
COPY . .

# 暴露端口
EXPOSE 3000

# 启动服务
CMD ["node", "server.js"]
```

**文件位置：** `docker/iconify-server/server.js`

```javascript
const express = require('express');
const cors = require('cors');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;

// 启用CORS
app.use(cors());

// 静态文件服务
app.use('/icons', express.static(path.join(__dirname, 'data/icons')));

// 图标集列表API
app.get('/collections', (req, res) => {
  try {
    const collectionsPath = path.join(__dirname, 'data/collections.json');
    const collections = JSON.parse(fs.readFileSync(collectionsPath, 'utf8'));
    res.json(collections);
  } catch (error) {
    res.status(500).json({ error: 'Failed to load collections' });
  }
});

// 图标集详情API
app.get('/collection', (req, res) => {
  const { prefix } = req.query;
  
  if (!prefix) {
    return res.status(400).json({ error: 'Missing prefix parameter' });
  }
  
  try {
    const collectionPath = path.join(__dirname, `data/collections/${prefix}.json`);
    if (!fs.existsSync(collectionPath)) {
      return res.status(404).json({ error: 'Collection not found' });
    }
    
    const collection = JSON.parse(fs.readFileSync(collectionPath, 'utf8'));
    res.json(collection);
  } catch (error) {
    res.status(500).json({ error: 'Failed to load collection' });
  }
});

// 单个图标API
app.get('/:prefix/:icon.svg', (req, res) => {
  const { prefix, icon } = req.params;
  
  try {
    const iconPath = path.join(__dirname, `data/icons/${prefix}/${icon}.svg`);
    if (!fs.existsSync(iconPath)) {
      return res.status(404).send('Icon not found');
    }
    
    const svg = fs.readFileSync(iconPath, 'utf8');
    res.setHeader('Content-Type', 'image/svg+xml');
    res.send(svg);
  } catch (error) {
    res.status(500).send('Failed to load icon');
  }
});

// 健康检查
app.get('/health', (req, res) => {
  res.json({ status: 'ok', timestamp: new Date().toISOString() });
});

app.listen(PORT, () => {
  console.log(`Iconify server running on port ${PORT}`);
});
```

#### 数据同步脚本

**文件位置：** `scripts/iconify-sync/sync-icons.js`

```javascript
const fs = require('fs');
const path = require('path');
const https = require('https');
const { execSync } = require('child_process');

class IconifySync {
  constructor(options = {}) {
    this.apiUrl = options.apiUrl || 'https://api.iconify.design';
    this.outputDir = options.outputDir || './iconify-data';
    this.collections = options.collections || ['ant-design', 'mdi', 'heroicons', 'bi', 'carbon'];
  }

  // 创建输出目录
  createDirectories() {
    const dirs = [
      this.outputDir,
      path.join(this.outputDir, 'collections'),
      path.join(this.outputDir, 'icons')
    ];
    
    dirs.forEach(dir => {
      if (!fs.existsSync(dir)) {
        fs.mkdirSync(dir, { recursive: true });
      }
    });
    
    this.collections.forEach(collection => {
      const iconDir = path.join(this.outputDir, 'icons', collection);
      if (!fs.existsSync(iconDir)) {
        fs.mkdirSync(iconDir, { recursive: true });
      }
    });
  }

  // 下载单个文件
  async downloadFile(url, outputPath) {
    return new Promise((resolve, reject) => {
      const file = fs.createWriteStream(outputPath);
      
      https.get(url, response => {
        if (response.statusCode !== 200) {
          reject(new Error(`HTTP ${response.statusCode}: ${url}`));
          return;
        }
        
        response.pipe(file);
        
        file.on('finish', () => {
          file.close();
          resolve(outputPath);
        });
        
        file.on('error', reject);
      }).on('error', reject);
    });
  }

  // 同步图标集信息
  async syncCollections() {
    console.log('同步图标集信息...');
    
    const collections = {};
    
    for (const prefix of this.collections) {
      try {
        console.log(`  同步图标集: ${prefix}`);
        
        const url = `${this.apiUrl}/collection?prefix=${prefix}`;
        const outputPath = path.join(this.outputDir, 'collections', `${prefix}.json`);
        
        await this.downloadFile(url, outputPath);
        
        const data = JSON.parse(fs.readFileSync(outputPath, 'utf8'));
        collections[prefix] = {
          name: data.name || prefix,
          total: Object.keys(data.icons || {}).length,
          author: data.author,
          license: data.license,
          version: data.version
        };
        
        console.log(`    完成: ${collections[prefix].total} 个图标`);
      } catch (error) {
        console.error(`    失败: ${error.message}`);
      }
    }
    
    // 保存图标集列表
    fs.writeFileSync(
      path.join(this.outputDir, 'collections.json'),
      JSON.stringify(collections, null, 2)
    );
    
    console.log(`图标集同步完成: ${Object.keys(collections).length} 个`);
  }

  // 同步图标文件
  async syncIcons() {
    console.log('同步图标文件...');
    
    for (const prefix of this.collections) {
      try {
        const collectionPath = path.join(this.outputDir, 'collections', `${prefix}.json`);
        if (!fs.existsSync(collectionPath)) {
          console.warn(`  跳过 ${prefix}: 图标集信息不存在`);
          continue;
        }
        
        const collection = JSON.parse(fs.readFileSync(collectionPath, 'utf8'));
        const icons = Object.keys(collection.icons || {});
        
        console.log(`  同步图标: ${prefix} (${icons.length} 个)`);
        
        let success = 0;
        let failed = 0;
        
        for (const icon of icons) {
          try {
            const url = `${this.apiUrl}/${prefix}/${icon}.svg`;
            const outputPath = path.join(this.outputDir, 'icons', prefix, `${icon}.svg`);
            
            await this.downloadFile(url, outputPath);
            success++;
            
            if (success % 100 === 0) {
              console.log(`    进度: ${success}/${icons.length}`);
            }
          } catch (error) {
            failed++;
          }
        }
        
        console.log(`    完成: ${success} 个成功, ${failed} 个失败`);
      } catch (error) {
        console.error(`  同步 ${prefix} 失败: ${error.message}`);
      }
    }
  }

  // 生成Docker镜像
  generateDockerImage() {
    console.log('生成Docker镜像...');
    
    const dockerfilePath = path.join(this.outputDir, 'Dockerfile');
    const dockerfile = `FROM nginx:alpine

COPY ./collections /usr/share/nginx/html/collections
COPY ./icons /usr/share/nginx/html/icons
COPY ./collections.json /usr/share/nginx/html/collections.json

# 自定义nginx配置
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
`;

    const nginxConfig = `events {
    worker_connections 1024;
}

http {
    include /etc/nginx/mime.types;
    default_type application/octet-stream;
    
    # CORS配置
    add_header Access-Control-Allow-Origin *;
    add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS';
    add_header Access-Control-Allow-Headers 'DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range';
    
    server {
        listen 80;
        root /usr/share/nginx/html;
        
        # 健康检查
        location /health {
            return 200 '{"status":"ok"}';
            add_header Content-Type application/json;
        }
        
        # 图标集列表
        location /collections {
            try_files $uri $uri.json =404;
        }
        
        # 图标集详情
        location /collection {
            try_files /collections/$arg_prefix.json =404;
        }
        
        # SVG图标
        location ~ ^/([^/]+)/([^/]+)\.svg$ {
            try_files /icons/$1/$2.svg =404;
            add_header Content-Type image/svg+xml;
        }
        
        # 静态文件
        location / {
            try_files $uri $uri/ =404;
        }
    }
}
`;

    fs.writeFileSync(dockerfilePath, dockerfile);
    fs.writeFileSync(path.join(this.outputDir, 'nginx.conf'), nginxConfig);
    
    console.log('Docker配置文件已生成');
    console.log(`构建命令: docker build -t iconify-server ${this.outputDir}`);
  }

  // 执行完整同步
  async syncAll() {
    console.log('开始完整同步...');
    
    this.createDirectories();
    await this.syncCollections();
    await this.syncIcons();
    this.generateDockerImage();
    
    console.log('同步完成!');
  }
}

// CLI接口
if (require.main === module) {
  const sync = new IconifySync({
    collections: process.argv[2]?.split(',') || ['ant-design', 'mdi', 'heroicons', 'bi']
  });
  
  sync.syncAll().catch(console.error);
}

module.exports = IconifySync;
```

### 2.2 Docker Compose部署

**文件位置：** `docker/iconify-stack/docker-compose.yml`

```yaml
version: '3.8'

services:
  iconify-server:
    build: 
      context: ./iconify-server
      dockerfile: Dockerfile
    ports:
      - "3000:3000"
    environment:
      - NODE_ENV=production
      - PORT=3000
    volumes:
      - ./data:/app/data:ro
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:3000/health"]
      interval: 30s
      timeout: 10s
      retries: 3
    restart: unless-stopped
    
  nginx-proxy:
    image: nginx:alpine
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
    depends_on:
      - iconify-server
    restart: unless-stopped

volumes:
  iconify-data:
```

**文件位置：** `docker/iconify-stack/nginx.conf`

```nginx
events {
    worker_connections 1024;
}

http {
    upstream iconify {
        server iconify-server:3000;
    }
    
    server {
        listen 80;
        
        # API代理
        location /api/ {
            proxy_pass http://iconify/;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            
            # CORS headers
            add_header Access-Control-Allow-Origin *;
            add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS';
            add_header Access-Control-Allow-Headers 'DNT,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Range';
        }
        
        # 健康检查
        location /health {
            proxy_pass http://iconify/health;
        }
        
        # 默认响应
        location / {
            return 200 'Iconify Server is running';
            add_header Content-Type text/plain;
        }
    }
}
```

## 方案三：混合部署模式

### 3.1 智能降级策略

**文件位置：** `packages/icons/src/smart-icon-loader.ts`

```typescript
import { ref, computed } from 'vue';
import { addIcon, getIcon } from '@iconify/vue';
import { getOfflineIconNames, isOfflineIcon } from './iconify-offline/custom-offline-icons';

interface IconLoaderConfig {
  apiUrl: string;
  timeout: number;
  retryCount: number;
  offlineFirst: boolean;
  cacheEnabled: boolean;
}

class SmartIconLoader {
  private config: IconLoaderConfig;
  private isOnline = ref(navigator.onLine);
  private failedRequests = new Set<string>();
  private cache = new Map<string, any>();
  
  constructor(config: Partial<IconLoaderConfig> = {}) {
    this.config = {
      apiUrl: 'https://api.iconify.design',
      timeout: 5000,
      retryCount: 2,
      offlineFirst: false,
      cacheEnabled: true,
      ...config
    };
    
    // 监听网络状态
    window.addEventListener('online', () => {
      this.isOnline.value = true;
      this.failedRequests.clear();
    });
    
    window.addEventListener('offline', () => {
      this.isOnline.value = false;
    });
  }

  // 智能图标加载策略
  async loadIcon(iconName: string): Promise<boolean> {
    // 1. 检查是否为离线图标
    if (isOfflineIcon(iconName)) {
      return true; // 离线图标总是可用
    }
    
    // 2. 检查Iconify缓存
    if (getIcon(iconName)) {
      return true;
    }
    
    // 3. 检查本地缓存
    if (this.config.cacheEnabled && this.cache.has(iconName)) {
      const cached = this.cache.get(iconName);
      addIcon(iconName, cached);
      return true;
    }
    
    // 4. 检查网络状态和失败记录
    if (!this.isOnline.value || this.failedRequests.has(iconName)) {
      return this.fallbackToOffline(iconName);
    }
    
    // 5. 尝试在线加载
    try {
      const success = await this.loadFromApi(iconName);
      if (success) {
        return true;
      }
    } catch (error) {
      console.warn(`Failed to load icon ${iconName}:`, error);
      this.failedRequests.add(iconName);
    }
    
    // 6. 降级到离线策略
    return this.fallbackToOffline(iconName);
  }

  // 从API加载图标
  private async loadFromApi(iconName: string): Promise<boolean> {
    const [prefix, name] = iconName.split(':');
    const url = `${this.config.apiUrl}/${prefix}/${name}.json`;
    
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), this.config.timeout);
    
    try {
      const response = await fetch(url, {
        signal: controller.signal,
        headers: {
          'Accept': 'application/json',
        }
      });
      
      clearTimeout(timeoutId);
      
      if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
      }
      
      const iconData = await response.json();
      
      // 添加到Iconify缓存
      addIcon(iconName, iconData);
      
      // 添加到本地缓存
      if (this.config.cacheEnabled) {
        this.cache.set(iconName, iconData);
      }
      
      return true;
    } catch (error) {
      clearTimeout(timeoutId);
      throw error;
    }
  }

  // 降级到离线策略
  private fallbackToOffline(iconName: string): boolean {
    // 尝试找到相似的离线图标
    const offlineIcons = getOfflineIconNames();
    const [prefix, name] = iconName.split(':');
    
    // 查找相同前缀的图标
    const similarIcons = offlineIcons.filter(icon => icon.startsWith(`${prefix}:`));
    if (similarIcons.length > 0) {
      console.info(`Using similar offline icon for ${iconName}:`, similarIcons[0]);
      return true;
    }
    
    // 查找功能相似的图标
    const functionalMatches = this.findFunctionalMatch(name);
    if (functionalMatches.length > 0) {
      console.info(`Using functional match for ${iconName}:`, functionalMatches[0]);
      return true;
    }
    
    console.warn(`No offline fallback found for ${iconName}`);
    return false;
  }

  // 查找功能相似的图标
  private findFunctionalMatch(iconName: string): string[] {
    const offlineIcons = getOfflineIconNames();
    const keywords = iconName.toLowerCase().split(/[-_]/);
    
    return offlineIcons.filter(icon => {
      const iconKeywords = icon.split(':')[1].toLowerCase().split(/[-_]/);
      return keywords.some(keyword => iconKeywords.includes(keyword));
    });
  }

  // 预加载关键图标
  async preloadCriticalIcons(iconNames: string[]): Promise<void> {
    console.log('预加载关键图标...');
    
    const loadPromises = iconNames.map(async iconName => {
      try {
        await this.loadIcon(iconName);
      } catch (error) {
        console.warn(`Failed to preload ${iconName}:`, error);
      }
    });
    
    await Promise.allSettled(loadPromises);
    console.log('关键图标预加载完成');
  }

  // 获取加载统计
  getStats() {
    return {
      isOnline: this.isOnline.value,
      failedRequests: this.failedRequests.size,
      cacheSize: this.cache.size,
      offlineIconsCount: getOfflineIconNames().length
    };
  }

  // 清除缓存
  clearCache() {
    this.cache.clear();
    this.failedRequests.clear();
  }
}

// 创建全局实例
export const iconLoader = new SmartIconLoader({
  apiUrl: import.meta.env.VITE_ICONIFY_API_URL,
  offlineFirst: import.meta.env.VITE_OFFLINE_ICONS_MODE === 'true',
  timeout: 3000
});

// 预加载关键图标
const CRITICAL_ICONS = [
  'ant-design:home-outlined',
  'ant-design:user-outlined',
  'ant-design:setting-outlined',
  'ant-design:menu-outlined',
  'ant-design:search-outlined',
  'ant-design:edit-outlined',
  'ant-design:delete-outlined',
  'ant-design:plus-outlined'
];

iconLoader.preloadCriticalIcons(CRITICAL_ICONS);
```

### 3.2 配置管理

**文件位置：** `packages/icons/src/icon-config.ts`

```typescript
interface IconSystemConfig {
  mode: 'offline' | 'online' | 'hybrid';
  apiUrl: string;
  timeout: number;
  cacheEnabled: boolean;
  offlineFirst: boolean;
  debugEnabled: boolean;
  criticalIcons: string[];
  fallbackStrategy: 'similar' | 'default' | 'none';
}

const DEFAULT_CONFIG: IconSystemConfig = {
  mode: 'hybrid',
  apiUrl: 'https://api.iconify.design',
  timeout: 5000,
  cacheEnabled: true,
  offlineFirst: false,
  debugEnabled: false,
  criticalIcons: [],
  fallbackStrategy: 'similar'
};

class IconConfigManager {
  private config: IconSystemConfig;
  
  constructor(userConfig: Partial<IconSystemConfig> = {}) {
    this.config = {
      ...DEFAULT_CONFIG,
      ...this.loadFromEnv(),
      ...userConfig
    };
    
    if (this.config.debugEnabled) {
      console.log('Icon System Config:', this.config);
    }
  }

  // 从环境变量加载配置
  private loadFromEnv(): Partial<IconSystemConfig> {
    const envConfig: Partial<IconSystemConfig> = {};
    
    if (import.meta.env.VITE_OFFLINE_ICONS_MODE) {
      envConfig.mode = import.meta.env.VITE_OFFLINE_ICONS_MODE === 'true' ? 'offline' : 'online';
    }
    
    if (import.meta.env.VITE_ICONIFY_API_URL) {
      envConfig.apiUrl = import.meta.env.VITE_ICONIFY_API_URL;
    }
    
    if (import.meta.env.VITE_ICON_DEBUG) {
      envConfig.debugEnabled = import.meta.env.VITE_ICON_DEBUG === 'true';
    }
    
    return envConfig;
  }

  // 获取配置
  getConfig(): IconSystemConfig {
    return { ...this.config };
  }

  // 更新配置
  updateConfig(updates: Partial<IconSystemConfig>): void {
    this.config = { ...this.config, ...updates };
    
    if (this.config.debugEnabled) {
      console.log('Icon Config Updated:', updates);
    }
  }

  // 检查是否应该使用离线模式
  shouldUseOfflineMode(): boolean {
    return this.config.mode === 'offline' || 
           (this.config.mode === 'hybrid' && this.config.offlineFirst);
  }

  // 检查是否启用在线加载
  isOnlineLoadingEnabled(): boolean {
    return this.config.mode !== 'offline';
  }
}

export const iconConfig = new IconConfigManager();
```

## 部署脚本和工具

### 自动化部署脚本

**文件位置：** `scripts/deploy/deploy-offline-icons.sh`

```bash
#!/bin/bash

set -e

echo "========================================"
echo "Niko-Admin 离线图标部署脚本"
echo "========================================"

# 配置变量
PROJECT_ROOT=$(dirname $(dirname $(dirname $(realpath $0))))
SCRIPTS_DIR="$PROJECT_ROOT/scripts"
ICON_TOOL="$SCRIPTS_DIR/icon-management/offline-icon-tool.js"
DEPLOY_MODE=${1:-"offline"}

echo "项目根目录: $PROJECT_ROOT"
echo "部署模式: $DEPLOY_MODE"

# 检查Node.js
if ! command -v node &> /dev/null; then
    echo "错误: 需要安装Node.js"
    exit 1
fi

# 检查npm
if ! command -v npm &> /dev/null; then
    echo "错误: 需要安装npm"
    exit 1
fi

echo ""
echo "1. 分析项目图标使用情况..."
cd "$PROJECT_ROOT"
node "$ICON_TOOL" scan
node "$ICON_TOOL" analyze

echo ""
echo "2. 生成离线图标代码..."
node "$ICON_TOOL" generate

echo ""
echo "3. 清理未使用的依赖..."
node "$ICON_TOOL" clean

if [ -f "$SCRIPTS_DIR/icon-management/clean-deps.sh" ]; then
    echo "执行依赖清理脚本..."
    chmod +x "$SCRIPTS_DIR/icon-management/clean-deps.sh"
    "$SCRIPTS_DIR/icon-management/clean-deps.sh"
fi

echo ""
echo "4. 更新环境配置..."

# 更新生产环境配置
if [ "$DEPLOY_MODE" = "offline" ]; then
    echo "VITE_OFFLINE_ICONS_MODE=true" > .env.production.local
    echo "已启用离线图标模式"
elif [ "$DEPLOY_MODE" = "server" ]; then
    read -p "请输入内网图标服务器地址: " SERVER_URL
    echo "VITE_OFFLINE_ICONS_MODE=false" > .env.production.local
    echo "VITE_ICONIFY_API_URL=$SERVER_URL" >> .env.production.local
    echo "已配置图标服务器: $SERVER_URL"
fi

echo ""
echo "5. 构建项目..."
cd niko-ui
npm run build:antd

echo ""
echo "6. 生成部署报告..."
cd "$PROJECT_ROOT"
node "$ICON_TOOL" report

echo ""
echo "========================================"
echo "离线图标部署完成!"
echo "========================================"
echo ""
echo "部署信息:"
echo "- 模式: $DEPLOY_MODE"
echo "- 构建产物: niko-ui/apps/web-antd/dist"
echo "- 分析报告: scripts/icon-management/icon-analysis-report.json"
echo ""

if [ "$DEPLOY_MODE" = "server" ]; then
    echo "图标服务器部署:"
    echo "- Docker配置: docker/iconify-server/"
    echo "- 启动命令: docker-compose up -d"
    echo ""
fi

echo "部署验证:"
echo "1. 检查控制台是否有图标加载错误"
echo "2. 测试图标选择器功能"
echo "3. 验证离线环境下的图标显示"
```

### 验证脚本

**文件位置：** `scripts/test/test-offline-icons.js`

```javascript
const puppeteer = require('puppeteer');
const fs = require('fs');

class OfflineIconTester {
  constructor() {
    this.browser = null;
    this.page = null;
  }

  async init() {
    this.browser = await puppeteer.launch({
      headless: false,
      args: ['--no-sandbox', '--disable-setuid-sandbox']
    });
    
    this.page = await this.browser.newPage();
    
    // 设置离线模式
    await this.page.setOfflineMode(true);
    
    console.log('浏览器已启动（离线模式）');
  }

  async testIconLoading(url = 'http://localhost:5173') {
    console.log(`测试地址: ${url}`);
    
    // 监听控制台错误
    const errors = [];
    this.page.on('console', msg => {
      if (msg.type() === 'error') {
        errors.push(msg.text());
      }
    });
    
    // 监听网络请求失败
    const failedRequests = [];
    this.page.on('requestfailed', request => {
      failedRequests.push({
        url: request.url(),
        failure: request.failure().errorText
      });
    });
    
    // 访问页面
    await this.page.goto(url, { waitUntil: 'networkidle2' });
    
    // 等待页面加载
    await this.page.waitForTimeout(3000);
    
    // 检查图标元素
    const iconElements = await this.page.$$('.iconify, [class*="icon-"], svg');
    console.log(`发现 ${iconElements.length} 个图标元素`);
    
    // 检查图标是否正确渲染
    const missingIcons = [];
    for (const element of iconElements) {
      const bbox = await element.boundingBox();
      if (!bbox || bbox.width === 0 || bbox.height === 0) {
        const outerHTML = await element.evaluate(el => el.outerHTML);
        missingIcons.push(outerHTML);
      }
    }
    
    // 生成测试报告
    const report = {
      timestamp: new Date().toISOString(),
      url,
      totalIcons: iconElements.length,
      missingIcons: missingIcons.length,
      consoleErrors: errors.length,
      failedRequests: failedRequests.length,
      details: {
        missingIcons: missingIcons.slice(0, 10), // 只显示前10个
        consoleErrors: errors.slice(0, 10),
        failedRequests: failedRequests.slice(0, 10)
      }
    };
    
    console.log('\n=== 测试报告 ===');
    console.log(`总图标数: ${report.totalIcons}`);
    console.log(`缺失图标: ${report.missingIcons}`);
    console.log(`控制台错误: ${report.consoleErrors}`);
    console.log(`失败请求: ${report.failedRequests}`);
    
    if (report.missingIcons > 0) {
      console.log('\n缺失的图标:');
      report.details.missingIcons.forEach(icon => {
        console.log(`  ${icon}`);
      });
    }
    
    if (report.consoleErrors > 0) {
      console.log('\n控制台错误:');
      report.details.consoleErrors.forEach(error => {
        console.log(`  ${error}`);
      });
    }
    
    // 保存报告
    fs.writeFileSync('offline-icon-test-report.json', JSON.stringify(report, null, 2));
    
    return report;
  }

  async testIconPicker() {
    console.log('测试图标选择器...');
    
    try {
      // 查找图标选择器
      await this.page.waitForSelector('.icon-picker, [class*="icon-picker"]', { timeout: 5000 });
      
      // 点击图标选择器
      await this.page.click('.icon-picker, [class*="icon-picker"]');
      
      // 等待图标列表加载
      await this.page.waitForTimeout(2000);
      
      // 检查是否有图标显示
      const iconItems = await this.page.$$('.icon-item, [class*="icon-item"]');
      
      console.log(`图标选择器中有 ${iconItems.length} 个图标`);
      
      return iconItems.length > 0;
    } catch (error) {
      console.log('图标选择器测试失败:', error.message);
      return false;
    }
  }

  async close() {
    if (this.browser) {
      await this.browser.close();
      console.log('浏览器已关闭');
    }
  }
}

// CLI接口
async function main() {
  const tester = new OfflineIconTester();
  
  try {
    await tester.init();
    
    const url = process.argv[2] || 'http://localhost:5173';
    const report = await tester.testIconLoading(url);
    
    await tester.testIconPicker();
    
    // 判断测试结果
    const success = report.missingIcons === 0 && report.consoleErrors === 0;
    
    console.log(`\n测试${success ? '通过' : '失败'}!`);
    process.exit(success ? 0 : 1);
    
  } catch (error) {
    console.error('测试失败:', error);
    process.exit(1);
  } finally {
    await tester.close();
  }
}

if (require.main === module) {
  main();
}

module.exports = OfflineIconTester;
```

## 总结

本指南提供了三种内网离线图标部署方案：

1. **完全离线模式**：适合小型项目，图标需求相对固定
2. **内网图标服务器**：适合大型企业，有专门的运维团队
3. **混合部署模式**：适合中型项目，需要在灵活性和稳定性之间平衡

每种方案都提供了详细的实施步骤、配置文件和自动化脚本，可以根据实际需求选择合适的方案进行部署。

### 推荐方案选择

- **小团队/简单项目**：选择完全离线模式
- **大型企业/复杂项目**：选择内网图标服务器
- **中等规模项目**：选择混合部署模式

所有方案都包含了完整的测试和验证流程，确保部署后的系统能够在内网环境下正常工作。