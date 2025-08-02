import type { GrantType } from '@vben/common-ui';
import type { HttpResponse } from '@vben/request';

import { h } from 'vue';

import { useAppConfig } from '@vben/hooks';

import { Modal } from 'ant-design-vue';

import { requestClient } from '#/api/request';

const { clientId, sseEnable } = useAppConfig(
  import.meta.env,
  import.meta.env.PROD,
);

export namespace AuthApi {
  /**
   * @description: 所有登录类型都需要用到的
   * @param clientId 客户端ID 这里为必填项 但是在loginApi内部处理了 所以为可选
   * @param grantType 授权/登录类型
   * @param tenantId 租户id
   */
  export interface BaseLoginParams {
    clientId?: string;
    grantType: GrantType;
    tenantId: string;
  }



  /**
   * @description: 验证码登录需要用到的参数
   * @param code 验证码 可选(未开启验证码情况)
   * @param uuid 验证码ID 可选(未开启验证码情况)
   * @param username 用户名
   * @param password 密码
   */
  export interface SimpleLoginParams extends BaseLoginParams {
    code?: string;
    uuid?: string;
    username: string;
    password: string;
  }

  export type LoginParams = SimpleLoginParams;

  // /** 登录接口参数 */
  // export interface LoginParams {
  //   code?: string;
  //   grantType: string;
  //   password: string;
  //   tenantId: string;
  //   username: string;
  //   uuid?: string;
  // }

  /** 登录接口返回值 */
  export interface LoginResult {
    access_token: string;
    client_id: string;
    expire_in: number;
  }

  export interface RefreshTokenResult {
    data: string;
    status: number;
  }
}

/**
 * 登录
 */
export async function loginApi(data: AuthApi.LoginParams) {
  return requestClient.post<AuthApi.LoginResult>(
    '/auth/login',
    { ...data, clientId },
    {
      encrypt: true,
    },
  );
}

/**
 * 用户登出
 * @returns void
 */
export async function doLogout() {
  const resp = await requestClient.post<HttpResponse<void>>(
    '/auth/logout',
    null,
    {
      isTransformResponse: false,
    },
  );
  // 无奈之举 对错误用法的提示
  if (resp.code === 401 && import.meta.env.DEV) {
    Modal.destroyAll();
    Modal.warn({
      title: '后端配置出现错误',
      centered: true,
      content: h('div', { class: 'flex flex-col gap-2' }, [
        `检测到你的logout接口返回了401, 导致前端一直进入循环逻辑???`,
        ...Array.from({ length: 3 }, () =>
          h(
            'span',
            { class: 'font-bold text-red-500 text-[18px]' },
            '去检查你的后端配置!别盯着前端找问题了!这不是前端问题!',
          ),
        ),
      ]),
    });
  }
}

/**
 * 关闭sse连接
 * @returns void
 */
export function seeConnectionClose() {
  /**
   * 未开启sse 不需要处理
   */
  if (!sseEnable) {
    return;
  }
  return requestClient.get<void>('/resource/sse/close');
}

/**
 * @param companyName 租户/公司名称
 * @param domain 绑定域名(不带http(s)://) 可选
 * @param tenantId 租户id
 */
export interface TenantOption {
  companyName: string;
  domain?: string;
  tenantId: string;
}

/**
 * @param tenantEnabled 是否启用租户
 * @param voList 租户列表
 */
export interface TenantResp {
  tenantEnabled: boolean;
  voList: TenantOption[];
}

/**
 * 获取租户列表 下拉框使用
 */
export function tenantList() {
  return requestClient.get<TenantResp>('/auth/tenant/list');
}

/**
 * vben的 先不删除
 * @returns string[]
 */
export async function getAccessCodesApi() {
  return requestClient.get<string[]>('/auth/codes');
}


