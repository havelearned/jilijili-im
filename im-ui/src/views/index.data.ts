import {LocalStorage} from "quasar";
import {getUserByUserId} from "@/api/user";
import {useFriendStore} from "@/stores/modules/friend"

const useHome = () => {
  const friendStore = useFriendStore()

  // 用户信息
  const userInfo = ref({})
  // t侧边栏
  const leftDrawerOpen = ref(false);
  const rigthDrawerOpen = ref(true);
  //搜索
  const search = ref("");
  // 当前tab信息
  const tabs = ref('online');
  const panels = ref('default');
  const currentFriendId = ref();
  const message = ref("");


  // 设置当前tab索引
  function setCurrentConversation(index: number) {
    if(currentFriendId.value){
      getUserByUserId(currentFriendId.value).then(res=>{
        friendStore.currFriendUser= {...res.data.user}
      })
    }
  }

  onMounted(() => {
    // @ts-ignore
    userInfo.value = LocalStorage.getItem("userinfo")
  })

  return {
    setCurrentConversation,
    tabs,
    panels,
    currentFriendId,
    userInfo,
    leftDrawerOpen,
    rigthDrawerOpen,
    search,
    message,
  }
}

export {
  useHome,

}
