import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { ClassesVO, ClassesForm, ClassesQuery } from '@/api/im/classes/types';

/**
 * 查询应用分类列表
 * @param query
 * @returns {*}
 */

export const listClasses = (query?: ClassesQuery): AxiosPromise<ClassesVO[]> => {
  return request({
    url: '/im/classes/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询应用分类详细
 * @param id
 */
export const getClasses = (id: string | number): AxiosPromise<ClassesVO> => {
  return request({
    url: '/im/classes/' + id,
    method: 'get'
  });
};

/**
 * 新增应用分类
 * @param data
 */
export const addClasses = (data: ClassesForm) => {
  return request({
    url: '/im/classes',
    method: 'post',
    data: data
  });
};

/**
 * 修改应用分类
 * @param data
 */
export const updateClasses = (data: ClassesForm) => {
  return request({
    url: '/im/classes',
    method: 'put',
    data: data
  });
};

/**
 * 删除应用分类
 * @param id
 */
export const delClasses = (id: string | number | Array<string | number>) => {
  return request({
    url: '/im/classes/' + id,
    method: 'delete'
  });
};
