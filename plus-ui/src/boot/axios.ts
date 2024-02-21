import {boot} from 'quasar/wrappers';
import axios, {AxiosInstance, AxiosResponse, InternalAxiosRequestConfig} from 'axios';

import {getToken, isToken} from '@/utils/auth';
import {tansParams} from "@/utils/ruoyi";
import {decryptBase64, decryptWithAes, encryptBase64, encryptWithAes, generateAesKey} from "@/utils/crypto";
import {decrypt, encrypt} from "@/utils/jsencrypt";
import {HttpStatus} from "@/enums/RespEnum";
import errorCode from "@/utils/errorCode";
import {Notify} from "quasar";
import useUserStore from "@/stores/modules/user";

const userDesc = useUserStore();

const encryptHeader = 'encrypt-key';

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $axios: AxiosInstance;
    $api: AxiosInstance;
  }
}


export const globalHeaders = () => {
  return {
    Authorization: 'Bearer ' + getToken(),
    clientid: import.meta.env.VITE_APP_CLIENT_ID
  };
};
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers['clientid'] = import.meta.env.VITE_APP_CLIENT_ID;


// Be careful when using SSR for cross-request state pollution
// due to creating a Singleton instance here;
// If any client changes this (global) instance, it might be a
// good idea to move this instance creation inside of the
// "export default () => {}" function below (which runs individually
// for each client)
const api = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 50000,
});

api.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    if (getToken()) {
      config.headers["Authorization"] = 'Bearer ' + getToken();
    }

    // 是否需要加密
    const isEncrypt = (config.headers || {}).isEncrypt === 'true';

    if (getToken() && !isToken) {
      config.headers['Authorization'] = 'Bearer ' + getToken(); // 让每个请求携带自定义token 请根据实际情况自行修改
    }

    // get请求映射params参数
    if (config.method === 'get' && config.params) {
      let url = config.url + '?' + tansParams(config.params);
      url = url.slice(0, -1);
      config.params = {};
      config.url = url;
    }

    // 当开启参数加密
    if (isEncrypt && (config.method === 'post' || config.method === 'put')) {
      // 生成一个 AES 密钥
      const aesKey = generateAesKey();
      config.headers[encryptHeader] = encrypt(encryptBase64(aesKey));
      config.data = typeof config.data === 'object' ? encryptWithAes(JSON.stringify(config.data), aesKey) : encryptWithAes(config.data, aesKey);
    }

    // FormData数据去请求头Content-Type
    if (config.data instanceof FormData) {
      delete config.headers['Content-Type'];
    }
    return config;

  });

api.interceptors.response.use((res: AxiosResponse) => {
  // 加密后的 AES 秘钥
  const keyStr = res.headers[encryptHeader];
  // 加密
  if (keyStr != null && keyStr != '') {
    const data = res.data;
    // 请求体 AES 解密
    const base64Str = decrypt(keyStr);
    // base64 解码 得到请求头的 AES 秘钥
    const aesKey = decryptBase64(base64Str.toString());
    // aesKey 解码 data
    const decryptData = decryptWithAes(data, aesKey);
    // 将结果 (得到的是 JSON 字符串) 转为 JSON
    res.data = JSON.parse(decryptData);
  }

  // 未设置状态码则默认成功状态
  const code = res.data.code || HttpStatus.SUCCESS;


  // 获取错误信息
  const msg = errorCode[code] || res.data.msg || errorCode['default'];


  // 二进制数据则直接返回
  if (res.request.responseType === 'blob' || res.request.responseType === 'arraybuffer') {
    return res.data;
  }

  if (code !== HttpStatus.SUCCESS) {
    handelMsg(code, res.data)

    return Promise.reject('error');
  } else {
    return Promise.resolve(res.data);
  }
}, (error: any) => {
  let {message} = error;
  if (message == 'Network Error') {
    message = '连接异常';
  } else if (message.includes('timeout')) {
    message = '请求超时';
  } else if (message.includes('Request failed with status code')) {
    message = '系统接口' + message.substr(message.length - 3) + '异常';
  }
  Notify.create({
    type: 'negative',
    message: message,
    position: 'top',
    timeout: 3000
  })
  return Promise.reject(error);
})

export default boot(({app}) => {
  // for use inside Vue files (Options API) through this.$axios and this.$api

  app.config.globalProperties.$axios = axios;
  // ^ ^ ^ this will allow you to use this.$axios (for Vue Options API form)
  //       so you won't necessarily have to import axios in each vue file

  app.config.globalProperties.$api = api;
  // ^ ^ ^ this will allow you to use this.$api (for Vue Options API form)
  //       so you can easily perform requests against your app's API
});


export {api};


function handelMsg(code: any, res: any) {
  switch (code) {
    case HttpStatus.BAD_METHOD:
      notify('negative','请求方法错误')
      break;
    case HttpStatus.NOT_FOUND:
      notify('negative','请求资源不存在')
      break;
    case HttpStatus.FORBIDDEN:
      notify('negative','当前操作没有权限')
      break;
    case HttpStatus.UNAUTHORIZED:
      notify('negative','登录过期，无法访问,请重新登录!!!')
      break;
    case HttpStatus.SERVICE_ERROR:
      notify('negative','系统异常')
      break;
    case HttpStatus.SEE_OTHER:
      break;
    default:
      notify('negative',res.msg)
        break;
  }
}

function notify(type: string, msg: any) {
  Notify.create({
    type: type,
    message: msg,
    position: 'top',
    timeout: 3000
  })
}
