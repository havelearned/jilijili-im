
import {AppboxApplicationVo} from "@/api/im/appBox/type";
import {queryAppByUser} from "@/api/im/appBox";
import {useAppBoxs} from "@/stores/modules/appBox"

const useAppBox = () => {
  const appBoxStore = useAppBoxs()
  const appBoxArray = ref<Array<AppboxApplicationVo>>([])

  // 监听appBoxStore.currAppBoxList变化,更新列表
  watchEffect(() => {
    appBoxArray.value = appBoxStore.currAppBoxList;
  });



  // 获取用户应用列表
  async function fatchAppBoxList() {


    // 从缓存获取应用列表
    if (appBoxStore.currAppBoxList.length < 1) {
      // api获取应用列表
      let res = await queryAppByUser()
      // @ts-ignore
      if (res.code === 200) {
        // @ts-ignore
        appBoxStore.currAppBoxList = res.data
        appBoxArray.value = appBoxStore.currAppBoxList
      }
    } else {
      // 缓存获取应用列表
      appBoxArray.value = appBoxStore.currAppBoxList
    }


  }


  // onMounted(async () => {
  //   await fatchAppBoxList();
  // })
  return {
    appBoxArray,
    fatchAppBoxList,
  }
}


export {
  useAppBox
}
