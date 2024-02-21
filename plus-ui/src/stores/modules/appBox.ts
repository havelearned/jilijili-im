import {defineStore} from "pinia";
import {ref} from "vue";
import {AppboxApplicationVo} from "@/api/im/appBox/type";
import {SysUserVo} from "@/api/login/login.data";
import {MsgRecordVO} from "@/api/im/msgRecord/types";

export const useAppBoxs = defineStore('appBox', () => {
  // 当前选中应用
  const currAppBox = ref<AppboxApplicationVo>()
  // 当前用户应用
  const currAppBoxList = ref<AppboxApplicationVo[]>([])
  // 显示添加应用弹窗
  const addPop = ref<boolean>(false)
  // 当前预览的应用
  const previewAppBox = ref<AppboxApplicationVo>()
  // 当前激活的appbox id
  const active = ref<number | string | undefined | null>(0)
  // 头部提示bar对象
  const hitTitleBar = ref({
    id: undefined,
    isAppBox: false, // 是否是appbox
    iconUrl: undefined,
    avatar: undefined,
    title: undefined,
    remark: undefined
  })
  // 当前应用的用户列表
  const currAppBoxUserList = ref<SysUserVo[]>();

  // 聊天记录
  const chatRecordMap= ref<Map<string|number,MsgRecordVO[]>>(new Map<string|number,MsgRecordVO[]>)
  // 聊天对象
  const chatType =ref<number>(1); // 1用户 2群聊 3应用

  /**
   * 获取当前用户的应用
   */
  function fatchCurrAppBoxList() {

  }

  function setAddPop(v:boolean){
    addPop.value = v
  }
  function setCurrAppBoxList(v: AppboxApplicationVo[]){
    currAppBoxList.value = v
  }
  function setCurrAppBox(v: AppboxApplicationVo) {
    currAppBox.value = v
  }

  function  setPreviewAppBox(v: AppboxApplicationVo) {
    previewAppBox.value = v
  }

  function  setPreview() {
    previewAppBox.value = undefined
  }

  function setActive(v: number | string | undefined | null) {
    active.value = v
  }

  return {
    chatType,
    chatRecordMap,
    currAppBoxUserList,
    hitTitleBar,
    active,
    setActive,
    previewAppBox,
    setPreviewAppBox,
    setPreview,
    currAppBox,
    setCurrAppBox,
    currAppBoxList,
    setCurrAppBoxList,
    addPop,
    setAddPop
  }

})
