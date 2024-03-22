import {api as request} from '@/boot/axios';
import {FriendForm, FriendQuery} from '@/api/im/friend/types';

/**
 * 查询好友列表
 * @param query
 * @returns {*}
 */

export const listFriend = (query?: FriendQuery): any => {
  return request({
    url: '/im/friend/list',
    method: 'get',
    params: query
  });
};

/**
 * 搜索用户
 * @param query
 */
export const searchFriend = (query?: FriendQuery): any => {
  return request({
    url: '/im/friend/search',
    method: 'get',
    params: query
  });
};

/**
 * 查询好友详细
 * @param id
 */
export const getFriend = (id: string | number): any => {
  return request({
    url: '/im/friend/' + id,
    method: 'get'
  });
};

/**
 * 新增好友
 * @param data
 */
export const addFriend = (data: FriendForm):any => {
  return request({
    url: '/im/friend',
    method: 'post',
    data: data
  });
};

/**
 * 修改好友
 * @param data
 */
export const updateFriend = (data: FriendForm):any => {
  return request({
    url: '/im/friend',
    method: 'put',
    data: data
  });
};

/**
 * 同意好友申请
 * @param data
 */
export const acceptFriend = (data: FriendForm):any => {
  return request({
    url: '/im/friend/accept',
    method: 'put',
    data: data
  });
};

/**
 * 删除好友
 * @param id
 */
export const delFriend = (id: string | number | Array<string | number>) :any=> {
  return request({
    url: `/im/friend/${id}`,
    method: 'delete'
  });
};


