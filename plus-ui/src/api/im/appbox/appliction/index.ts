import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { AppboxapplicationVO, AppboxapplicationForm, AppboxapplicationQuery } from '@/api/im/appbox/appliction/types';

/**
 * 查询创建应用or频道列表
 * @param query
 * @returns {*}
 */

export const listAppboxapplication = (query?: AppboxapplicationQuery): AxiosPromise<AppboxapplicationVO[]> => {
  return request({
    url: '/im/appboxapplication/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询创建应用or频道详细
 * @param id
 */
export const getAppboxapplication = (id: string | number): AxiosPromise<AppboxapplicationVO> => {
  return request({
    url: '/im/appboxapplication/' + id,
    method: 'get'
  });
};

/**
 * 新增创建应用or频道
 * @param data
 */
export const addAppboxapplication = (data: AppboxapplicationForm) => {
  return request({
    url: '/im/appboxapplication',
    method: 'post',
    data: data
  });
};

/**
 * 修改创建应用or频道
 * @param data
 */
export const updateAppboxapplication = (data: AppboxapplicationForm) => {
  return request({
    url: '/im/appboxapplication',
    method: 'put',
    data: data
  });
};

/**
 * 删除创建应用or频道
 * @param id
 */
export const delAppboxapplication = (id: string | number | Array<string | number>) => {
  return request({
    url: '/im/appboxapplication/' + id,
    method: 'delete'
  });
};
