import {defineStore} from "pinia";
import {SysUserVo} from "@/api/login/login.data";
import {reactive, ref} from "vue";
import {FriendForm, FriendQuery} from "@/api/im/friend/types";
import {getLocalUserInfo} from "@/utils/auth";
import {acceptFriend, addFriend, delFriend, listFriend, updateFriend} from "@/api/im/friend";
import {MsgRecordVO} from "@/api/im/msgRecord/types";


export const useFriendStore = defineStore('friend', () => {
  // 当前聊天好友
  // @ts-ignore
  let currFriendUser = reactive<SysUserVo>({})
  // 当前好友列表
  let currFriendList = reactive<Array<SysUserVo>>([])
  const isShowSearchFriend = ref(false)
  // 当前好友聊天记录

  const friendList = ref<Array<SysUserVo>>([]);
  const friendQueryForm = ref<FriendQuery>({
    createTime: undefined,
    friendId: undefined,
    pageNum: 0,
    pageSize: 100,
    relationStatus: 2,
  });
  const friendFrom = ref<FriendForm>({
    friendId: undefined,
    id: undefined, relationStatus: 0,
    remark: "",
    userId: undefined
  })

  /**
   * 添加好友
   */
  const add = async (friendId: number | string) => {
    resetFrom()
    let param = {
      userId: getLocalUserInfo().userId,
      friendId: friendId,
      relationStatus: 1
    }
    let res = await addFriend(param)
    if (res.code === 200) {
      if (friendList.value) {
        friendList.value = friendList.value.filter(item => item.userId != param.friendId)
      }
    }
  }

  /**
   * 获取好友列表
   */
  const getFriendList = async (param: FriendQuery) => {
    resetFrom()
    let res = await listFriend(param)
    if (res.code === 200) {
      friendList.value = res.rows
      // 存储当前好友列表
      currFriendList = friendList.value
    }
  }

  /**
   * 删除好友
   */
  const handelDeleteFriend = async (friendId: number | string) => {
    resetFrom()
    let res = await delFriend(friendId)
    if (res.code == 200) {
      if (friendList.value) {
        friendList.value = friendList.value.filter(item => item.userId != friendId)
      }
    }
  }

  /**
   * 接受好友
   */
  const handelAcceptFriend = async (friend: SysUserVo | FriendForm) => {
    resetFrom()
    friendFrom.value.userId = getLocalUserInfo()?.userId // 当前用户id
    friendFrom.value.friendId = friend.userId // 好友id
    friendFrom.value.relationStatus = 2 // 好友关系
    let res = await acceptFriend(friendFrom.value)
    if (res.code === 200) {
      if (friendList.value) {
        friendList.value = friendList.value.filter(item => item.userId != friendFrom.value.friendId)
      }
    }
  }


  /**
   * 屏蔽好友
   */
  const handelShieldFriend = async (friend: SysUserVo | FriendForm) => {
    resetFrom()
    friendFrom.value.userId = getLocalUserInfo()?.userId // 当前用户id
    friendFrom.value.friendId = friend.userId // 好友id
    friendFrom.value.relationStatus = 3 // 好友关系
    let res = await updateFriend(friendFrom.value)
    if (res.code === 200) {
      if (friendList.value) {
        friendList.value = friendList.value.filter(item => item.userId != friendFrom.value.friendId)
      }
    }
  }

  /**
   * 搜索好友
   */
  const handelSearchFriend = () => {
    setTimeout(async () => {
      let res = await listFriend(friendQueryForm.value)
      if (res.code === 200) {
        friendList.value = res.rows
      } else {
        friendList.value = []
      }
    }, 500)

  }

  const resetFrom = () => {
    friendFrom.value = {
      friendId: undefined,
      id: undefined,
      relationStatus: undefined,
      remark: undefined,
      userId: undefined
    }
  }

  function  setCurrFriendList(user: SysUserVo[]) {
    currFriendList = user
  }

  function  setCurrFriendUser(user: SysUserVo) {
    currFriendUser = user
  }
  function  setFriendList(userVo:Array<SysUserVo>) {
    friendList.value = userVo
  }

  return {
    // 搜索好友
    handelSearchFriend,
    // 搜索好友
    friendQueryForm,
    // 搜索好友
    friendList,
    setFriendList,
    // 获取好友列表
    getFriendList,
    // 同意加为好友
    handelAcceptFriend,
    // 删除好友,
    handelDeleteFriend,
    // 屏蔽好友
    handelShieldFriend,
    // 添加好友
    add,
    isShowSearchFriend,
    // 当前好友
    currFriendUser,
    setCurrFriendUser,
    // 当前好友列表
    currFriendList,
    setCurrFriendList
  }
})
