import type { Recordable } from '@vben/types';

import {
  getOfflineIcons,
  getOfflineIconsByPrefix,
} from '@vben/icons';

export type IconMode = 'offline' | 'online';

/**
 * 一个缓存对象，在不刷新页面时，无需重复请求远程接口
 */
export const ICONS_MAP: Recordable<string[]> = {};

interface IconifyResponse {
  prefix: string;
  total: number;
  title: string;
  uncategorized?: string[];
  categories?: Recordable<string[]>;
  aliases?: Recordable<string>;
}

const PENDING_REQUESTS: Recordable<Promise<string[]>> = {};

/**
 * 获取图标数据，支持在线/离线/自动模式
 * @param prefix 图标集名称
 * @param mode 图标加载模式
 * @returns 图标集中包含的所有图标名称
 */
export async function fetchIconsData(
  prefix: string,
  mode: IconMode = 'online',
): Promise<string[]> {
  // 离线模式优先尝试离线图标
  if (mode === 'offline') {
    const offlineIcons = getOfflineIconsByPrefix(prefix);
    if (offlineIcons.length > 0) {
      ICONS_MAP[prefix] = offlineIcons;
      return offlineIcons;
    }
    
    // 离线模式下，如果没有找到离线图标，返回空数组
    console.warn(`No offline icons found for prefix: ${prefix}`);
    return [];
  }
  
  // 在线模式获取
  if (mode === 'online') {
    return fetchOnlineIconsData(prefix);
  }
  
  return [];
}

/**
 * 通过Iconify接口获取图标集数据（原有的在线获取逻辑）
 * @param prefix 图标集名称
 * @returns 图标集中包含的所有图标名称
 */
async function fetchOnlineIconsData(prefix: string): Promise<string[]> {
  if (Reflect.has(ICONS_MAP, prefix) && ICONS_MAP[prefix]) {
    return ICONS_MAP[prefix];
  }
  if (Reflect.has(PENDING_REQUESTS, prefix) && PENDING_REQUESTS[prefix]) {
    return PENDING_REQUESTS[prefix];
  }
  PENDING_REQUESTS[prefix] = (async () => {
    try {
      const controller = new AbortController();
      const timeoutId = setTimeout(() => controller.abort(), 1000 * 10);
      const response: IconifyResponse = await fetch(
        `https://api.iconify.design/collection?prefix=${prefix}`,
        { signal: controller.signal },
      ).then((res) => res.json());
      clearTimeout(timeoutId);
      const list = response.uncategorized || [];
      if (response.categories) {
        for (const category in response.categories) {
          list.push(...(response.categories[category] || []));
        }
      }
      ICONS_MAP[prefix] = list.map((v) => `${prefix}:${v}`);
    } catch (error) {
      console.error(`Failed to fetch icons for prefix ${prefix}:`, error);
      return [] as string[];
    }
    return ICONS_MAP[prefix];
  })();
  return PENDING_REQUESTS[prefix];
}

/**
 * 获取所有离线图标（用于离线模式下显示所有图标）
 */
export function getAllOfflineIcons(): string[] {
  return getOfflineIcons();
}
