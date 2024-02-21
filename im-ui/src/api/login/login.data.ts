/**
 clientId  :  "e5cd7e4891bf95d1d19206ce24a7b32e"
 code  :  "0"
 grantType  :  "password"
 password  :  "admin123"
 rememberMe  :  true
 tenantId  :  "000000"
 username  :  "admin"
 uuid  :  "c2c5423be6b0495cb8142c51e5eab961"
 */
import {AxiosPromise} from 'axios';
import {api as request} from '@/boot/axios';


export interface RegisterData extends LoginData{
  userType: string
}

export interface LoginData {
  clientId: string
  code: string
  grantType: string
  password: string
  rememberMe: boolean
  tenantId: string
  username: string
  uuid: string
}

export interface SysUserVo {
  userId: number;
  tenantId: string;
  deptId: number;
  userName: string;
  nickName: string;
  userType: string;
  email: string;
  phonenumber: string;
  sex: string;
  avatar?: string;
  status: string;
  loginIp: string;
  loginDate?: string;
  remark: string;
  createTime: string;
  dept?: string;
  roles?: string[];
  roleIds?: number[];
  postIds?: number[];
  roleId?: number;
  online: boolean;
}

export interface SysUserQuery extends PageQuery{
  userId: number;
  tenantId: string;
  deptId: number;
  userName: string;
  nickName: string;
  userType: string;
  email: string;
  phonenumber: string;
  sex: string;
  avatar?: string;
  status: string;
  loginIp: string;
  loginDate?: string;
  remark: string;
  createTime: string;
  dept?: string;
  roles?: string[];
  roleIds?: number[];
  postIds?: number[];
  roleId?: number;
  online: boolean;
}

export  interface SysUserPasswordForm{
  oldPassword: string;
  newPassword: string;
}

/**
 * 验证码返回
 */
export interface VerifyCodeResult {
  captchaEnabled: boolean;
  uuid?: string;
  img?: string;
}


export interface LoginResult {
  access_token: string;
}


const clientId = import.meta.env.VITE_APP_CLIENT_ID;


export function getUserInfoApi(): AxiosPromise<SysUserVo> {
  return request({
    method: 'get',
    url: '/system/user/getInfo'
  })
}


export function loginApi(data: LoginData):any {
  const params = {
    ...data,
    clientId: data.clientId || clientId,
    grantType: data.grantType || 'password'
  };
  return request({
    // @ts-ignore
    url: '/auth/login',
    headers: {
      isToken: false,
      isEncrypt: true
    },
    method: 'post',
    data: params
  });
}




export const register = (data?: RegisterData) => {
  const params = {
    ...data,
    // @ts-ignore
    clientId: data.clientId || clientId,
    // @ts-ignore
    grantType: data.grantType || 'password'
  };

  return request({
    url: '/auth/register',
    method: 'post',
    headers: {
      isToken: false,
      isEncrypt: true
    },
    data: data
  });
}


export function logoutApi() {
  return request({
    // @ts-ignore
    url: '/auth/logout',
    method: 'post'
  });
}



/**
 * 获取验证码
 */
export function getCodeImg(): AxiosPromise<VerifyCodeResult> {
  return request({
    url: '/auth/code',
    headers: {
      isToken: false
    },
    method: 'get',
    timeout: 20000
  });
}
