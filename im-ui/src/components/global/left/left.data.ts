
import {AppboxApplicationVo} from "@/api/im/appBox/type";
import {getAppTreeById} from "@/api/im/appBox";


export const useApplicationInfo = () => {

  const appInfo = ref<AppboxApplicationVo>({});
  const applicationInfo = ref<Array<AppboxApplicationVo>>([])


  async function fetchApplicationInfo(appId: string) {
    let res = await getAppTreeById(appId)
    // @ts-ignore
    if (res.code === 200) {
      applicationInfo.value = res.data
      appInfo.value= applicationInfo.value[0]
    }
  }

  return {
    applicationInfo,
    fetchApplicationInfo,
    appInfo,

  }
}
