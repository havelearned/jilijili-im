import {api as request, globalHeaders} from '@/boot/axios';
import {AxiosPromise} from 'axios';
import {UserInfoVo, UserQuery} from "@/api/user/type";
import {LoginData, RegisterData, SysUserVo} from "@/api/login/login.data";


/**
 * 获取用户信息
 * @param query
 */
export const getUserInfo = (query?: UserQuery): AxiosPromise<any> => {
  return request({
    url: '/system/user/getInfo',
    method: 'get',
    params: query
  });
};

/**
 * 根据用户id获取用户信息
 * @param userId
 */
export const getUserByUserId = (userId:any):AxiosPromise<any> => {
  return request({
    url: '/system/user/'+userId,
    method: 'get',
  });
};

/**
 * 普通登录
 * @param data
 */
export const login = (data?: LoginData) => {
  return request({
    url: '/login',
    method: 'post',
    data: data
  });
}

export const register = (data?: RegisterData) => {
  return request({
    url: '/register',
    method: 'post',
    data: data
  });
}


/**
 * 修改用户头像
 * @param data
 */
export const setUserAvatar=(data: any) :any =>{

  return request({
    headers: globalHeaders(),
    url: '/system/user/profile/avatar',
    method: 'post',
    data: data
  });
}

/**
 * 修改用户信息
 * @param data
 */
export  const setUserInfo=(data: any) :any =>{
  return request({
    url: '/system/user',
    method: 'put',
    data: data
  });
}


/**
 * 重置密码 加密
 */
export const resetPassword = (data: any):any => {
  return request({
    headers:{
      isEncrypt: true
    },
    url: '/system/user/profile/updatePwd',
    method: 'put',
    data: data
  });
}
