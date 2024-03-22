import {api as request, globalHeaders} from '@/boot/axios';


/**
 *
 * @param data
 */
export const upload = (data: any):any => {
  return request({
    headers: globalHeaders(),
    url: '/resource/oss/upload',
    method: 'post',
    data: data,
  });
};

