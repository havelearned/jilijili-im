// 是否为空
import {Notify} from "quasar";

export function isNotEmpty(val: string): boolean | string {
  return !!val || '不能为空';
}

// 校验邮箱格式
export function isValidEmail(val: string): boolean | string {
  return /.+@.+\..+/.test(val) || '请输入合法的邮箱地址';
}

// 校验昵称6-16个字符,不能出现特殊字符
export function validNickname(val: string): boolean | string {
  return /^[\u4e00-\u9fa5A-Za-z0-9_]{6,16}$/.test(val) || '请输入6-16个字符的昵称,不能出现特殊字符';
}

//只校验中国手机号,考虑脱敏后的校验 182****4601
export function validPhone(val: string): boolean | string {
  // 去除脱敏后的手机号中的星号，获得实际的数字
  const realNumber = val.replace(/\*/g, 'x'); // 替换为任意数字
  // 进行校验
  return /^1[3456789]\d{9}$/.test(realNumber) || '请输入正确的手机号';
}


/**
 * 时间戳转字符串
 * @param timestamp
 */
export function timestampToISOString(timestamp: number): string {
    const date = new Date(timestamp);
    return date.toISOString();
}


/**
 * 在字符串指定位置插入内容
 */
export function insertContent(str: string, insertion: string, index: number): string {
    // 在指定索引位置插入内容
    if(!str){
        str+=''
    }
    return str.substring(0, index) + insertion + str.substring(index);
}


/**
 * 时间格式化
 * @param inputDateStr 时间字符串
 * @return 本周内返回 今天 昨天 周三 ....., 不在本周内返回 yyyy-MM-dd HH:mm
 */
export function formatDate(inputDateStr: string): string {
    // 将输入的时间字符串转换为Date对象
    const inputDate = new Date(inputDateStr);
    // 获取当前日期
    const today = new Date();
    // 计算两个日期之间的毫秒差
    const diffMilliseconds = Math.abs(today - inputDate);
    // 转换为天数
    const diffDays = diffMilliseconds / (1000 * 60 * 60 * 24);
    const hours = inputDate.getHours();
    const minutes = inputDate.getMinutes();
    if (diffDays <= 1) {
        return `${diffDays <= 0 ? "昨天 " : "今天 "} ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
    } else if (diffDays <= 7) {
        const dayOfWeek = inputDate.getDay();
        const daysOfWeek = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];
        return `${daysOfWeek[dayOfWeek] +" "+ hours.toString().padStart(2,'0')+":"+minutes.toString().padStart(2,'0')}`;
    } else {
        // 如果超过本周，格式化为 "更久以前：年月日时间"
        const year = inputDate.getFullYear();
        const month = (inputDate.getMonth() + 1).toString().padStart(2, '0');
        const day = inputDate.getDate().toString().padStart(2, '0');
        return `更久以前：${year}-${month}-${day} ${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
    }
}

/**
 * 复制到剪切板
 */
export function copyToClipboard(text: string): void {
    navigator.clipboard.writeText(text).then(res=>{
        // 通知
        Notify.create({
            position: 'center',
            message: '复制成功',
            icon: 'content_copy',
            color: 'positive',
            timeout: 1000
        });
    }).catch(err=>{
        Notify.create({
            position: 'center',
            message: '复制失败',
            icon: 'content_copy',
            color: 'negative',
            timeout: 1000
        });
    })

}
