import {LocalStorage} from "quasar";
import {SysUserVo} from "@/api/login/login.data";


export const getToken = (): string | null => {
  return LocalStorage.getItem("token")
}
export const setToken = (tokenValue: string) => {
  LocalStorage.set("token", tokenValue)
}

export const isToken = (): boolean => {
  const token = getToken()
  if (!token) {
    return false
  }
  return true
}

export const removeToken = () => {
  LocalStorage.remove("token")
}


export const getLocalUserInfo = (): any =>{
  if(LocalStorage.getItem("userInfo")){
    // @ts-ignore
    return LocalStorage.getItem("userInfo").user
  }
  return null
}
