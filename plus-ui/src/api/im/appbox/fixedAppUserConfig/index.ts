import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { FixedAppUserConfigVO, FixedAppUserConfigForm, FixedAppUserConfigQuery } from '@/api/im/appbox/fixedAppUserConfig/types';

/**
 * 查询应用的固定用户配置列表
 * @param query
 * @returns {*}
 */

export const listFixedAppUserConfig = (query?: FixedAppUserConfigQuery): AxiosPromise<FixedAppUserConfigVO[]> => {
  return request({
    url: '/im/fixedAppUserConfig/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询应用的固定用户配置详细
 * @param id
 */
export const getFixedAppUserConfig = (id: string | number): AxiosPromise<FixedAppUserConfigVO> => {
  return request({
    url: '/im/fixedAppUserConfig/' + id,
    method: 'get'
  });
};

/**
 * 新增应用的固定用户配置
 * @param data
 */
export const addFixedAppUserConfig = (data: FixedAppUserConfigForm) => {
  return request({
    url: '/im/fixedAppUserConfig',
    method: 'post',
    data: data
  });
};

/**
 * 修改应用的固定用户配置
 * @param data
 */
export const updateFixedAppUserConfig = (data: FixedAppUserConfigForm) => {
  return request({
    url: '/im/fixedAppUserConfig',
    method: 'put',
    data: data
  });
};

/**
 * 删除应用的固定用户配置
 * @param id
 */
export const delFixedAppUserConfig = (id: string | number | Array<string | number>) => {
  return request({
    url: '/im/fixedAppUserConfig/' + id,
    method: 'delete'
  });
};
