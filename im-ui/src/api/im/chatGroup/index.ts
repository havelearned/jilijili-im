import {api as request} from '@/boot/axios';
import {ChatGroupData, ChatGroupForm} from "@/api/im/chatGroup/type";


/**
 * 获取用户群聊信息
 * @param query
 */
export const getUserList = (query?: ChatGroupForm): any => {
  return request({
    url: '/im/groupChat/userList',
    method: 'get',
    params: query
  });
};

/**
 * 获取用户群聊信息
 * @param query
 */
export const getGroupChatList = (query?: ChatGroupForm): any => {
  return request({
    url: '/im/groupChat/groupChatList',
    method: 'get',
    params: query
  });
};


/**
 * 获取用户群聊信息
 * @param query
 */
export const getUserChatGroup = (query?: ChatGroupData): any => {
  return request({
    url: '/im/groupChat/list',
    method: 'get',
    params: query
  });
};

/**
 * 获取群聊信息
 * @param id
 */
export const getChatGroupInfo = (id: string | number): any => {
  return request({
    url: '/im/groupChat/' + id,
    method: 'get',
  });
};


/**
 * 添加群聊信息
 * @param data
 */
export const addChatGroup = (data: ChatGroupData): any => {
  return request({
    url: '/im/groupChat',
    method: 'post',
    data: data
  });
};

/**
 * 退出群聊
 */
export const exitGroup = (groupId: string | number, userId: string | number): any => {
  return request({
    url: `/im/groupChat/exit/${groupId}/${userId}`,
    method: 'delete',
  });
};


export const deleteGroup = (groupId: string | number, userId: string | number): any => {
  return request({
    url: `/im/groupChat/${groupId}/${userId}`,
    method: 'delete',
  });
};
