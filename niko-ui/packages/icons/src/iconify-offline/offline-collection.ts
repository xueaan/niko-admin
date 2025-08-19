/**
 * 离线图标集合管理
 * 用于图标选择器的离线模式
 */

// 所有已注册的离线图标列表
const OFFLINE_ICONS = [
  // 系统图标
  'ant-design:user-outlined',
  'ant-design:setting-outlined',
  'ant-design:tool-outlined',
  'ant-design:inbox-outlined',
  
  // 菜单图标
  'eos-icons:system-group',
  'eos-icons:role-binding-outlined',
  'ph:user-duotone',
  'ph:user-list',
  'ph:users-light',
  'ph:browser-duotone',
  'ic:sharp-menu',
  'ic:baseline-house',
  'ic:baseline-monitor',
  'ic:round-launch',
  'ic:outline-computer',
  'mingcute:department-line',
  'mingcute:profile-line',
  'icon-park-outline:appointment',
  'icon-park-outline:monitor',
  'icon-park-twotone:setting-two',
  
  // 开发相关图标
  'devicon:redis-wordmark',
  'devicon:spring-wordmark',
  'devicon:vscode',
  'devicon:windows8',
  'devicon:linux',
  'logos:redis',
  'logos:chrome',
  'logos:firefox',
  'logos:microsoft-edge',
  'logos:opera',
  'logos:quarkus-icon',
  'logos:safari',
  'logos:vue',
  'skill-icons:java-light',
  'skill-icons:typescript',
  'vscode-icons:file-type-excel',
  
  // 功能图标
  'fluent-mdl2:dictionary',
  'fluent-mdl2:flow',
  'fluent-mdl2:leave-user',
  'fluent-mdl2:build-definition',
  'fluent:form-24-regular',
  'fe:notice-push',
  'material-symbols:logo-dev-outline',
  'material-symbols:generating-tokens-outline',
  'material-symbols:cloud-done-outline-rounded',
  'material-symbols:menu',
  'arcticons:one-hand-operation',
  'arcticons:uc-browser',
  'streamline:interface-login-dial-pad-finger-password-dial-pad-dot-finger',
  
  // 文件和文件夹
  'solar:folder-with-files-outline',
  'flat-color-icons:folder',
  'flat-color-icons:plus',
  'flat-color-icons:leave',
  'flat-color-icons:command-line',
  'flat-color-icons:android-os',
  'bx:package',
  'bx:file',
  
  // 监控相关
  'solar:monitor-bold-duotone',
  'solar:monitor-camera-outline',
  'solar:monitor-smartphone-outline',
  
  // 工具图标
  'akar-icons:schedule',
  'mdi:tools',
  'mdi:workflow-outline',
  'mdi:cc-outline',
  'mdi:button-pointer',
  'mdi:wechat',
  'tabler:code',
  'tabler:category-plus',
  'tabler:file-type-xml',
  'carbon:model-alt',
  'carbon:task-approved',
  'carbon:sql',
  
  // 其他图标
  'simple-icons:authy',
  'simple-icons:tencentqq',
  'bi:houses-fill',
  'lucide:table',
  'lucide:book-open-text',
  'lucide:copyright',
  'emojione:evergreen-tree',
  'ri:instance-line',
  'ri:todo-line',
  'ri:baidu-fill',
  'ri:dingding-fill',
  'ri:dingding-line',
  'ri:taobao-fill',
  'fa-brands:alipay',
  'la:memory',
  'wpf:macos',
  'majesticons:iphone-x-apps-line',
];

/**
 * 获取所有离线图标
 */
export function getOfflineIcons(): string[] {
  return [...OFFLINE_ICONS];
}

/**
 * 根据前缀获取离线图标
 */
export function getOfflineIconsByPrefix(prefix: string): string[] {
  if (!prefix || prefix === 'all') {
    return getOfflineIcons();
  }
  return OFFLINE_ICONS.filter((icon) => icon.startsWith(`${prefix}:`));
}

/**
 * 检查图标是否为离线图标
 */
export function isOfflineIcon(iconName: string): boolean {
  return OFFLINE_ICONS.includes(iconName);
}

/**
 * 获取离线图标统计信息
 */
export function getOfflineIconStats() {
  const stats: Record<string, number> = {};
  
  OFFLINE_ICONS.forEach((icon) => {
    const [prefix] = icon.split(':');
    stats[prefix] = (stats[prefix] || 0) + 1;
  });
  
  return {
    total: OFFLINE_ICONS.length,
    byPrefix: stats,
    prefixes: Object.keys(stats).sort(),
  };
}

/**
 * 搜索离线图标
 */
export function searchOfflineIcons(keyword: string): string[] {
  if (!keyword) {
    return getOfflineIcons();
  }
  
  const lowerKeyword = keyword.toLowerCase();
  return OFFLINE_ICONS.filter((icon) => {
    const iconName = icon.toLowerCase();
    const [prefix, name] = iconName.split(':');
    
    // 搜索前缀或名称
    return (
      iconName.includes(lowerKeyword) ||
      prefix.includes(lowerKeyword) ||
      name.includes(lowerKeyword) ||
      // 搜索名称中的单词
      name.split('-').some((word) => word.includes(lowerKeyword))
    );
  });
}