import {api as request} from '@/boot/axios';
import {AxiosPromise} from 'axios';
import {
  AppboxApplicationFrom,
  AppboxapplicationQuery,
  AppboxapplicationVO,
  AppboxApplicationVo
} from "@/api/im/appBox/type";
import {R} from "@/api/result.data";
import {SysUserQuery, SysUserVo} from "@/api/login/login.data";
import {useAppBoxs} from "@/stores/modules/appBox";


/**
 * 获取当前应用下的用户列表
 * @param query
 */

export const getAppBoxUserList = (query?: SysUserQuery): AxiosPromise<any[]> => {
  let appId = useAppBoxs().active
  return request({
    url: '/im/appBox/appBoxUsers/'+appId,
    method: 'get',
    params: query
  });
};

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
 * 通过id查询应用信息
 * @param id
 */
export const getAppTreeById = (id: string | number | null):
  AxiosPromise<any> => {
  return request({
    url: 'im/appboxapplication/tree/' + id,
    method: 'get',
  });
};


/**
 * 通过用户id查询应用
 * @param id
 */
export const queryAppByUser = ():
  AxiosPromise<R<Array<AppboxApplicationVo>>> => {
  return request({
    url: 'im/appboxapplication/user',
    method: 'get',
  });
};

// 用户添加应用
export const addAppBox = (data: any): any => {
  return request({
    url: '/im/appBox/'+data,
    method: 'post',
    data: data
  });
};


export const edit = (data: AppboxApplicationFrom): AxiosPromise<R<any>> => {
  return request({
    url: 'im/appboxapplication',
    method: 'put',
    data: data
  });
};

export const removeAppBox = (data: any): AxiosPromise<R<any>> => {
  return request({
    url: 'im/appBox/' + data.join(','),
    method: 'delete',
  });
};




