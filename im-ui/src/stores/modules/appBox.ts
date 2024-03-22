import {defineStore} from "pinia";

import {AppboxApplicationVo} from "@/api/im/appBox/type";
import {SysUserVo} from "@/api/login/login.data";

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

    // 聊天对象
    const chatType = ref<number>(1); // 1用户 2群聊 3应用


    function setAddPop(v: boolean) {
        addPop.value = v
    }

    function setCurrAppBoxList(v: AppboxApplicationVo[]) {
        currAppBoxList.value = v
    }

    function setCurrAppBox(v: AppboxApplicationVo) {
        currAppBox.value = v
    }

    function setPreviewAppBox(v: AppboxApplicationVo) {
        previewAppBox.value = v
    }

    function setPreview() {
        previewAppBox.value = undefined
    }

    function setActive(v: number | string | undefined | null) {
        active.value = v
    }

    return {
        chatType,
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
