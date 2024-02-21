import {defineStore} from "pinia";
import {ref} from "vue";
import {loginApi, LoginData, logoutApi, SysUserVo} from "@/api/login/login.data";
import {getToken, removeToken} from "@/utils/auth";
import {LocalStorage} from "quasar";
import {getUserInfo} from "@/api/user";


export const useUserStore = defineStore('user', () => {
    const token = ref(getToken());
    const currUser = ref<SysUserVo>();
    // 用户频道权限编码集合
    const permissionsChannel = ref<Array<string[]>>([]);
    const getUserinfo = async () => {
      let res: any | null = LocalStorage.getItem('userInfo')
      if (res && res.user) {
        currUser.value = res.user
        return res.user
      } else {
        res = await getUserInfo()
        if (res.code === 200) {
          LocalStorage.set("userInfo", res.data)
          currUser.value = res.data.user
        } else {
        }
        return null
      }
    }
    const login = async (userInfo: LoginData): Promise<void> => {
      const res = await loginApi(userInfo)
      if (res) {
        const data = res.data;
        currUser.value = data.user;
        return Promise.resolve();
      }
      return Promise.reject(res)
    }
    const logOut = async (): Promise<void> => {
      await logoutApi();
      token.value = '';
      LocalStorage.clear();
      await removeToken()
    }
    return {
      token,
      currUser,
      permissionsChannel,
      login,
      logOut,
      getUserinfo
    }
  })
;


export default useUserStore;

