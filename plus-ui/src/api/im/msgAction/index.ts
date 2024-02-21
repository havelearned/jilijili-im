import request from '@/utils/request';
import { AxiosPromise } from 'axios';
import { MsgActionVO, MsgActionForm, MsgActionQuery } from '@/api/im/msgAction/types';

/**
 * 查询聊天动作列表
 * @param query
 * @returns {*}
 */

export const listMsgAction = (query?: MsgActionQuery): AxiosPromise<MsgActionVO[]> => {
  return request({
    url: '/im/msgAction/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询聊天动作详细
 * @param id
 */
export const getMsgAction = (id: string | number): AxiosPromise<MsgActionVO> => {
  return request({
    url: '/im/msgAction/' + id,
    method: 'get'
  });
};

/**
 * 新增聊天动作
 * @param data
 */
export const addMsgAction = (data: MsgActionForm) => {
  return request({
    url: '/im/msgAction',
    method: 'post',
    data: data
  });
};

/**
 * 修改聊天动作
 * @param data
 */
export const updateMsgAction = (data: MsgActionForm) => {
  return request({
    url: '/im/msgAction',
    method: 'put',
    data: data
  });
};

/**
 * 删除聊天动作
 * @param id
 */
export const delMsgAction = (id: string | number | Array<string | number>) => {
  return request({
    url: '/im/msgAction/' + id,
    method: 'delete'
  });
};
