import {api as request} from '@/boot/axios';
import { AxiosPromise } from 'axios';
import { MsgRecordVO, MsgRecordForm, MsgRecordQuery } from '@/api/im/msgRecord/types';

/**
 * 查询聊天消息记录列表
 * @param query
 * @returns {*}
 */

export const listMsgRecord = (query?: MsgRecordQuery): AxiosPromise<MsgRecordVO[]> => {
  return request({
    url: '/im/msgRecord/list',
    method: 'get',
    params: query
  });
};

/**
 * 查询聊天消息记录详细
 * @param id
 */
export const getMsgRecord = (id: string | number): AxiosPromise<MsgRecordVO> => {
  return request({
    url: '/im/msgRecord/' + id,
    method: 'get'
  });
};

/**
 * 新增聊天消息记录
 * @param data
 */
export const addMsgRecord = (data: MsgRecordForm) => {
  return request({
    url: '/im/msgRecord',
    method: 'post',
    data: data
  });
};

/**
 * 修改聊天消息记录
 * @param data
 */
export const updateMsgRecord = (data: MsgRecordForm) => {
  return request({
    url: '/im/msgRecord',
    method: 'put',
    data: data
  });
};

/**
 * 修改已读状态
 * @param data
 */
export const updateMsgRead = (data: MsgRecordForm) => {
    return request({
        url: '/im/msgRecord/read',
        method: 'put',
        data: data
    });
};

/**
 * 删除聊天消息记录
 * @param id
 */
export const delMsgRecord = (id: string | number | Array<string | number>) => {
  return request({
    url: '/im/msgRecord/' + id,
    method: 'delete'
  });
};
