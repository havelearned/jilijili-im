<!--suppress ALL -->
<template>
  <div v-if="appBoxPop" class="bg-[#3f51b5] flex items-center justify-between  text-white w-full ">
    <div class="flex items-center space-x-2">
      <q-btn @click="breakToGuildList" class="q-ma-xs" outline dense label="后退" icon="mdi-arrow-left"></q-btn>
    </div>
    <div>
      <span>您当前正处于预览模式。加入该应用开始聊天吧！</span>
    </div>
    <q-btn @click="handelAddAppBox" outline dense
           class="q-ma-xs hover:bg-primary/90 h-10 px-4 py-2 bg-white text-[#3f51b5]">
      加入此道
    </q-btn>
  </div>
  <q-layout view="lHh lpR lFr" container :style="style">
    <q-header elevated bordered reveal>
      <q-toolbar class="global-dark">
        <!-- 如果是用户-->
        <div v-if="friendStore.currFriendUser">
          <q-btn round flat>
            <q-avatar>
              <img v-if="friendStore.currFriendUser && friendStore.currFriendUser.avatar"
                   :src="friendStore.currFriendUser.avatar"/>
            </q-avatar>
          </q-btn>
          <span class="q-subtitle-1 q-pl-md" v-if="friendStore.currFriendUser">
            {{ friendStore.currFriendUser.userName }} {{ friendStore.currFriendUser.userId }}
          </span>
        </div>
        <!-- 如果是频道-->
        <div v-else>
          <q-btn round flat>
            <q-avatar size="lg">
              <q-img src="@/assets/def_0.png"></q-img>
            </q-avatar>
          </q-btn>
          <span class="q-subtitle-1 q-pl-md" v-if="useGrupStore.currGroupInfo">
            {{ useGrupStore.currGroupInfo.groupName }}
          </span>
        </div>
        <q-space/>

        <q-btn icon="add_to_photos" round flat>
          <q-tooltip>创建频道</q-tooltip>

        </q-btn>
        <q-btn-dropdown dropdown-icon="notifications" flat>
          <notifications></notifications>
        </q-btn-dropdown>

        <q-btn round flat icon="attachment">
          <q-tooltip>分享连接</q-tooltip>
        </q-btn>
      </q-toolbar>
    </q-header>
    <globalLeft v-if="appId !== '0'"></globalLeft>
    <letfcollection v-else></letfcollection>
    <globalRgith></globalRgith>
    <MeInfo ref="meInfoRef"></MeInfo>
    <q-page-container>
      <q-page style="width: 100%;" class="global-dark">
        <searchFriend v-if="isShowSearchFriend"></searchFriend>
        <Record></Record>
      </q-page>
    </q-page-container>


    <addGroupDialog ref="addGroupChatDialogRef"></addGroupDialog>
  </q-layout>
</template>

<script lang="ts" setup>
import 'vue3-emoji/dist/style.css'

import {computed, getCurrentInstance, onMounted, ref, watch} from "vue";
import {useQuasar} from "quasar";
import useUserStore from "@/stores/modules/user";
import {useRoute, useRouter} from "vue-router"
import {useHome} from "./index.data"

import globalLeft from "@/components/global/left/index.vue";
import globalRgith from "@/components/global/rgith/index.vue"
import upload from "@/components/upload/index.vue"
import searchFriend from "@/views/friend/searchFriend/index.vue"
import MeInfo from "@/views/me/index.vue"
import addGroupDialog from "@/views/group/dialog/addDialog.vue"
import letfcollection from "@/views/letfcollection/index.vue"
import Record from "@/views/msg/index.vue"

import {chatMessage as messageData} from "@/components/message/message.data";
import {bus, UPDATE_MSG_RECORD_MAP} from "@/boot/eventbus";
import {AppboxApplicationVo} from "@/api/im/appBox/type";
import {useAppBoxs} from "@/stores/modules/appBox";
import {addAppBox, queryAppByUser} from "@/api/im/appBox";
import {useFriendStore} from "@/stores/modules/friend";
import {useWs} from "@/stores/modules/ws";
import {getToken} from "@/utils/auth";
import {MsgRecordVO} from "@/api/im/msgRecord/types";
import {useGroupStore} from "@/stores/modules/group";

const route = useRoute()
const router = useRouter()
const $q = useQuasar();
const instance = getCurrentInstance()
const useUser = useUserStore();
const useWsStore = useWs();

const {
  active, addPop, currAppBox, hitTitleBar, previewAppBox, currAppBoxList,
  setPreview,
  setAddPop, setCurrAppBoxList, setCurrAppBox, setPreviewAppBox, setActive
} = useAppBoxs()

const {
  isShowSearchFriend, friendList, friendQueryForm, getFriendList,
  handelSearchFriend, handelDeleteFriend, handelAcceptFriend, handelShieldFriend
} = useFriendStore()
const friendStore = useFriendStore()

const {
  leftDrawerOpen, rigthDrawerOpen, search, message, tabs, panels, currentFriendId,
  setCurrentConversation
} = useHome()

const useGrupStore = useGroupStore()

const groupRef = ref<any>(null)
const meInfoRef = ref<any>(null)
const qBtnRef = ref<any>(null)
const addGroupChatDialogRef = ref<any>(null)


const sendData = ref<messageData>({name: '', avatar: '', stamp: '', sent: false, textColor: '', bgColor: '', data: ''});
const chatMessageRef = ref(null)
const appId = ref<string>('0')
const emiji = ref('')
const uploadStatus = ref(false)
const appBoxPop = ref(addPop)

// // 监听路由参数
watch(
  () => route.params,
  (newParams: any, oldParams: any) => {
    appId.value = newParams.id
  }, {immediate: true} // 立即执行一次 handler
);


const style = computed(() => ({
  height: $q.screen.height + "px",
}));
const channData = ref<AppboxApplicationVo>();


let cursorPos = 0


// 打开我的信息
function showMeInfo() {
  if (meInfoRef.value) {
    meInfoRef.value?.show()
  }

}


function handleEmClose() {
  console.log("关闭表情框?")
}


function handleReturn() {
  message.value += "\n";
}

// 选择表情包
function handleEmoji(val: string | any) {
  /*  val {
      emoji : "😗"
      emoji_version :"1.0"
      name:"kissing face"
      skin_tone_support:false
      slug:"kissing_face"
      unicode_version:"1.0"
    }*/
  // 获取当前输入框光标位置
  cursorPos = qBtnRef.value.$el.querySelector("textarea").selectionStart
  message.value = message.value.substring(0, cursorPos) + val.emoji + message.value.substring(cursorPos + 1)
}


// 发送消息
function handleSend() {
  sendData.value = {
    name: "me",
    avatar: "https://cdn.quasar.dev/img/avatar3.jpg",
    stamp: "7 minutes ago",
    sent: true,
    textColor: "white",
    bgColor: "primary",
    data: message.value,
  };
  console.log(emiji.value)
  message.value = ""
}

// 重置按钮操作
const resetQuery = () => {
  friendQueryForm = {
    createTime: undefined,
    friendId: undefined,
    pageNum: 0,
    pageSize: 100,
    relationStatus: undefined,
    userId: undefined
  }
}


// 路由跳转/guild-list
function breakToGuildList() {
  // 清除预览应用
  appBoxPop.value = false
  router.replace("/guild-list")
}

// 添加应用
function handelAddAppBox() {
  instance?.proxy?.$log_confirm("加入应用", "确定加入此应用吗?", () => {
    let appId = previewAppBox?.id
    // 添加应用
    addAppBox(appId).then((res: any) => {
      if (res.code === 200) {
        appBoxPop.value = false
        queryAppByUser().then((res: any) => {
          if (res.code == 200)
            setCurrAppBoxList(res.data)
        })
        setActive(appId)
      }
      useAppBoxs().previewAppBox = undefined
    })

  })
}

onMounted(async () => {
  await useUser.getUserinfo()
  useWsStore.initWs(`ws://localhost:8080/im-websocket?token=${getToken()}`)

  useWsStore.websocket.addEventListener("message", (e: MessageEvent) => {
    let data: MsgRecordVO = JSON.parse(e.data)
    data = data.data
    if (data.toType === 1) {
      bus.emit(UPDATE_MSG_RECORD_MAP,data) // 更新聊天记录
    }
  })

})


</script>


