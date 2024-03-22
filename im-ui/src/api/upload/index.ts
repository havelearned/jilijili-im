import {api as request, globalHeaders} from '@/boot/axios';
import {AxiosPromise} from "axios";


/**
 *
 * @param data
 */
export const uploadAudio = (data: any): any => {
    const formData = new FormData();
    formData.append('file', data, 'audio.mp3'); // 将音频 Blob 添加到 FormData
    return request({
        headers: {
            ...globalHeaders(),
            'Content-Type': 'multipart/form-data',
        },
        url: '/resource/oss/upload',
        method: 'post',
        data: formData,
    });
};

