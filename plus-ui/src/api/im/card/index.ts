import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { CardVO, CardForm, CardQuery } from '@/api/im/card/types';

/**
 * 查询社交卡片列表
 * @param query
 * @returns {*}
 */

export const listCard = (query?: CardQuery): AxiosPromise<CardVO[]> => {
  return request({
    url: '/im/card/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询社交卡片详细
 * @param id
 */
export const getCard = (id: string | number): AxiosPromise<CardVO> => {
  return request({
    url: '/im/card/' + id,
    method: 'get'
  });
};

/**
 * 新增社交卡片
 * @param data
 */
export const addCard = (data: CardForm) => {
  return request({
    url: '/im/card',
    method: 'post',
    data: data
  });
};

/**
 * 修改社交卡片
 * @param data
 */
export const updateCard = (data: CardForm) => {
  return request({
    url: '/im/card',
    method: 'put',
    data: data
  });
};

/**
 * 删除社交卡片
 * @param id
 */
export const delCard = (id: string | number | Array<string | number>) => {
  return request({
    url: '/im/card/' + id,
    method: 'delete'
  });
};
