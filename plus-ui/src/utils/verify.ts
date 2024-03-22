// 是否为空
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

// 动态组合校验规则的函数
export function combineRules(...rules: Array<(val: string) => boolean>): Array<(val: string) => boolean> {
  return rules;
}


export function removeDuplicates<T, K extends keyof T>(list: T[], key: K): T[] {
  const seen = new Set<T>();
  return list.filter(item => {
    const keyValue = item[key];
    if (!seen.has(keyValue)) {
      seen.add(keyValue);
      return true;
    }
    return false;
  });
}



