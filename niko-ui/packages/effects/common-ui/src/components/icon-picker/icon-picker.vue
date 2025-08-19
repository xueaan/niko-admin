<script setup lang="ts">
import type { VNode } from 'vue';

import { computed, ref, useAttrs, watch, watchEffect } from 'vue';

import { usePagination } from '@vben/hooks';
import { EmptyIcon, Grip, listIcons } from '@vben/icons';
import { $t } from '@vben/locales';

import {
  Button,
  Input,
  Pagination,
  PaginationEllipsis,
  PaginationFirst,
  PaginationLast,
  PaginationList,
  PaginationListItem,
  PaginationNext,
  PaginationPrev,
  RadioGroup,
  RadioGroupItem,
  VbenIcon,
  VbenIconButton,
  VbenPopover,
} from '@vben-core/shadcn-ui';
import { isFunction } from '@vben-core/shared/utils';

import { objectOmit, refDebounced, watchDebounced } from '@vueuse/core';

import { 
  fetchIconsData, 
  getAllOfflineIcons,
  type IconMode 
} from './icons';
import { getOfflineIconStats } from '@vben/icons';

interface Props {
  pageSize?: number;
  /** 图标集的名字 */
  prefix?: string;
  /** 是否自动请求API以获得图标集的数据.提供prefix时有效 */
  autoFetchApi?: boolean;
  /**
   * 图标列表
   */
  icons?: string[];
  /** Input组件 */
  inputComponent?: VNode;
  /** 图标插槽名，预览图标将被渲染到此插槽中 */
  iconSlot?: string;
  /** input组件的值属性名称 */
  modelValueProp?: string;
  /** 图标样式 */
  iconClass?: string;
  type?: 'icon' | 'input';
}

const props = withDefaults(defineProps<Props>(), {
  prefix: 'ant-design',
  pageSize: 36,
  icons: () => [],
  iconSlot: 'default',
  iconClass: 'size-4',
  autoFetchApi: true,
  modelValueProp: 'modelValue',
  inputComponent: undefined,
  type: 'input',
});

const emit = defineEmits<{
  change: [string];
}>();

const attrs = useAttrs();

const modelValue = defineModel({ default: '', type: String });

const visible = ref(false);
const currentSelect = ref('');
const currentPage = ref(1);
const keyword = ref('');
const keywordDebounce = refDebounced(keyword, 300);
const innerIcons = ref<string[]>([]);

// 图标加载模式（从localStorage读取或使用默认值）
const iconMode = ref<IconMode>(
  (localStorage.getItem('icon-picker-mode') as IconMode) || 'online'
);

// 离线图标统计信息
const offlineStats = computed(() => getOfflineIconStats());

// 监听模式变化，保存到localStorage
watch(iconMode, async (mode) => {
  localStorage.setItem('icon-picker-mode', mode);
  // 重置分页
  currentPage.value = 1;
  // 模式切换时重新加载图标
  if (props.prefix && props.prefix !== 'svg' && props.autoFetchApi) {
    innerIcons.value = await fetchIconsData(props.prefix, mode);
  }
});

/* 当检索关键词变化时，重置分页 */
watch(keywordDebounce, () => {
  currentPage.value = 1;
});
  
watchDebounced(
  () => props.prefix,
  async (prefix) => {
    if (prefix && prefix !== 'svg' && props.autoFetchApi) {
      innerIcons.value = await fetchIconsData(prefix, iconMode.value);
    }
  },
  { immediate: true, debounce: 500, maxWait: 1000 },
);

const currentList = computed(() => {
  try {
    // 离线模式下，直接返回所有离线图标
    if (iconMode.value === 'offline') {
      return getAllOfflineIcons();
    }
    
    // 在线模式
    if (props.prefix) {
      if (
        props.prefix !== 'svg' &&
        props.autoFetchApi &&
        props.icons.length === 0
      ) {
        return innerIcons.value;
      }
      const icons = listIcons('', props.prefix);
      if (icons.length === 0) {
        console.warn(`No icons found for prefix: ${props.prefix}`);
      }
      return icons;
    } else {
      return props.icons;
    }
  } catch (error) {
    console.error('Failed to load icons:', error);
    return [];
  }
});

const showList = computed(() => {
  return currentList.value.filter((item) =>
    item.includes(keywordDebounce.value),
  );
});

const { paginationList, total, setCurrentPage } = usePagination(
  showList,
  props.pageSize,
);

// 监听图标列表变化，确保当前页码有效
watch([showList, total], () => {
  const totalPages = Math.ceil(total.value / props.pageSize);
  if (totalPages > 0 && currentPage.value > totalPages) {
    currentPage.value = 1;
    setCurrentPage(1);
  }
});

watchEffect(() => {
  currentSelect.value = modelValue.value;
});

watch(
  () => currentSelect.value,
  (v) => {
    emit('change', v);
  },
);

const handleClick = (icon: string) => {
  currentSelect.value = icon;
  modelValue.value = icon;
  close();
};

const handlePageChange = (page: number) => {
  currentPage.value = page;
  setCurrentPage(page);
};

function toggleOpenState() {
  visible.value = !visible.value;
}

function open() {
  visible.value = true;
}

function close() {
  visible.value = false;
}

function onKeywordChange(v: string) {
  keyword.value = v;
}

const searchInputProps = computed(() => {
  return {
    placeholder: $t('ui.iconPicker.search'),
    [props.modelValueProp]: keyword.value,
    [`onUpdate:${props.modelValueProp}`]: onKeywordChange,
    class: 'mx-2',
  };
});

function updateCurrentSelect(v: string) {
  currentSelect.value = v;
  const eventKey = `onUpdate:${props.modelValueProp}`;
  if (attrs[eventKey] && isFunction(attrs[eventKey])) {
    attrs[eventKey](v);
  }
}
const getBindAttrs = computed(() => {
  return objectOmit(attrs, [`onUpdate:${props.modelValueProp}`]);
});

defineExpose({ toggleOpenState, open, close });
</script>
<template>
  <VbenPopover
    v-model:open="visible"
    :content-props="{ align: 'end', alignOffset: -11, sideOffset: 8 }"
    content-class="p-0 pt-3 w-full"
    trigger-class="w-full"
  >
    <template #trigger>
      <template v-if="props.type === 'input'">
        <component
          v-if="props.inputComponent"
          :is="inputComponent"
          :[modelValueProp]="currentSelect"
          :placeholder="$t('ui.iconPicker.placeholder')"
          role="combobox"
          :aria-label="$t('ui.iconPicker.placeholder')"
          aria-expanded="visible"
          :[`onUpdate:${modelValueProp}`]="updateCurrentSelect"
          v-bind="getBindAttrs"
        >
          <template #[iconSlot]>
            <VbenIcon
              :icon="currentSelect || Grip"
              class="size-4"
              aria-hidden="true"
            />
          </template>
        </component>
        <div class="relative w-full" v-else>
          <Input
            v-bind="$attrs"
            v-model="currentSelect"
            :placeholder="$t('ui.iconPicker.placeholder')"
            class="h-8 w-full pr-8"
            role="combobox"
            :aria-label="$t('ui.iconPicker.placeholder')"
            aria-expanded="visible"
          />
          <VbenIcon
            :icon="currentSelect || Grip"
            class="absolute right-1 top-1 size-6"
            aria-hidden="true"
          />
        </div>
      </template>
      <VbenIcon
        :icon="currentSelect || Grip"
        v-else
        class="size-4"
        v-bind="$attrs"
      />
    </template>
    <div class="mb-2 flex w-full flex-col gap-2">
      <!-- 模式切换 -->
      <div class="flex items-center justify-between px-2">
        <RadioGroup v-model="iconMode" class="flex gap-2">
          <div class="flex items-center space-x-2">
            <RadioGroupItem value="offline" id="offline" />
            <label for="offline" class="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70">
              离线模式
            </label>
          </div>
          <div class="flex items-center space-x-2">
            <a href="https://icon-sets.iconify.design/" target="_blank" class="flex items-center space-x-2 text-primary hover:underline">
              <RadioGroupItem value="online" id="online" />
              <label for="online" class="text-sm font-medium cursor-pointer">
                在线搜索
              </label>
            </a>
          </div>
        </RadioGroup>
        <div class="text-xs text-muted-foreground">
          离线图标: {{ offlineStats.total }}个
        </div>
      </div>
      <!-- 搜索框 -->
      <div class="flex w-full">
        <component
          v-if="inputComponent"
          :is="inputComponent"
          v-bind="searchInputProps"
        />
        <Input
          v-else
          class="mx-2 h-8 w-full"
          :placeholder="$t('ui.iconPicker.search')"
          v-model="keyword"
        />
      </div>
    </div>

    <template v-if="paginationList.length > 0">
      <div class="grid max-h-[360px] w-full grid-cols-6 justify-items-center">
        <VbenIconButton
          v-for="(item, index) in paginationList"
          :key="index"
          :tooltip="item"
          tooltip-side="top"
          @click="handleClick(item)"
        >
          <VbenIcon
            :class="{
              'text-primary transition-all': currentSelect === item,
            }"
            :icon="item"
          />
        </VbenIconButton>
      </div>
      <div
        v-if="total >= pageSize"
        class="flex-center flex justify-end overflow-hidden border-t py-2 pr-3"
      >
        <Pagination
          :items-per-page="36"
          :sibling-count="1"
          :total="total"
          :page="currentPage"
          show-edges
          size="small"
          @update:page="handlePageChange"
        >
          <PaginationList
            v-slot="{ items }"
            class="flex w-full items-center gap-1"
          >
            <PaginationFirst class="size-5" />
            <PaginationPrev class="size-5" />
            <template v-for="(item, index) in items">
              <PaginationListItem
                v-if="item.type === 'page'"
                :key="index"
                :value="item.value"
                as-child
              >
                <Button
                  :variant="item.value === currentPage ? 'default' : 'outline'"
                  class="size-5 p-0 text-sm"
                >
                  {{ item.value }}
                </Button>
              </PaginationListItem>
              <PaginationEllipsis
                v-else
                :key="item.type"
                :index="index"
                class="size-5"
              />
            </template>
            <PaginationNext class="size-5" />
            <PaginationLast class="size-5" />
          </PaginationList>
        </Pagination>
      </div>
    </template>

    <template v-else>
      <div class="flex-col-center text-muted-foreground min-h-[150px] w-full">
        <EmptyIcon class="size-10" />
        <div class="mt-1 text-sm">{{ $t('common.noData') }}</div>
      </div>
    </template>
  </VbenPopover>
</template>
