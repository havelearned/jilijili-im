<template>
  <div class=" row justify-between ">
    <div style="width: 100%; ">
      <q-scroll-area :style="{ height: `calc(${$q.screen.height}px - 100px` }" ref="scrollAreaRef"
                     :bar-style="barStyle" :thumb-style="thumbStyle"
                     class="bg-[#18191c] ">

        <div class="bg-[#18191c] text-white p-4">
          <div class="space-y-6 q-pa-md" v-for="(record,index) in recordList" :key="record.id">
            <div class="border-l-4 border-[#bd1e59] pl-4 ">
              <div class="flex items-center justify-between">
                <div class="flex items-center space-x-2">
                  <span
                    class="relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full">
                    <q-avatar size="50px" rounded>
                     <img class="" :alt="record.author?.nickName"
                          :src="record.author?.avatar">
                    </q-avatar>
                    <span class="flex h-full w-full items-center justify-center rounded-full bg-muted">RS
                   </span>
                  </span>
                  <div><p class="font-bold">{{ record.author?.nickName }}</p>
                    <p class="text-xs text-[#b0b3b8]">{{ record?.createTime }}</p>
                  </div>
                </div>
                <q-icon size="md" name="more"/>
              </div>
              <div v-html="record.content" class="pl-9 mt-2 discord-font q-ma-md">

              </div>
              <!--              反应-->
              <div class="flex items-center space-x-2 mt-2 pl-13" v-if="record.action">
                <q-icon size="xs" color="yellow" name="thumb_down"/>
                <span>{{ record.action.likeCount }}</span>

                <q-icon size="xs" color="yellow" name="sentiment_satisfied"/>
                <span>{{ record.action.fullCount }}</span>
                <q-icon size="xs" color="yellow" name="thumb_up"/>
                <span>{{ record.action.endorseCount }}</span>
              </div>
            </div>

            <!-- 有回复的话 -->
            <div v-if="record.chiller?.length>0">
              <div class="space-y-6 q-pa-md" v-for="(chill,index) in record.chiller" :key="chill.id">
                <div class="flex items-center justify-between">
                  <div class="flex items-center space-x-2 pl-10">
                     <span class="relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full">
<!--                  <img class="aspect-square h-full w-full" alt="Riot Schmick"-->
                       <!--                       :src="record.author.avatar">-->
                        <q-avatar size="50px" rounded>
                         <img class="" :alt="chill.author?.nickName"
                              :src="chill.author?.avatar">
                        </q-avatar>
                      <span class="flex h-full w-full items-center justify-center rounded-full bg-muted">RS</span>
                     </span>
                    <div><p class="font-bold">{{ chill.author?.nickName }}</p>
                      <p class="text-xs text-[#b0b3b8]">{{ chill.createTime }}</p>
                    </div>
                    <div v-html="chill.content" class=" mt-2 discord-font q-ma-md">

                    </div>
                  </div>
                </div>

              </div>
            </div>
          </div>
        </div>
      </q-scroll-area>
      <q-footer class="text-white global-dark q-pa-md">
        <q-form v-model="msgFrom" @submit="handleSubmit">
          <div class="full-width  q-pa-md" v-show="false">
            <upload class="full-width"/>
          </div>
          <q-toolbar class="row">
            <q-btn round flat icon="perm_media" class="q-mr-sm" @click=""/>
            <q-input clearable standout filled bg-color="white" autofocus autogrow
                     placeholder="请输入内容,ctrl+enter发送"
                     maxlength="1200" class="col-grow text-white" ref="qBtnRef" type="textarea"
                     @keyup.ctrl.enter="handleSubmit" v-model.trim="msgFrom.content"/>
            <q-btn round flat icon="send" type="submit"/>
            <!--        <V3Emoji :recent="true"-->
            <!--                 @clickEmoji="handleEmoji"-->
            <!--                 @close="handleEmClose"-->
            <!--                 :fulldata="true"-->
            <!--                 style="width: 50px"-->
            <!--                 :size="'mid'"-->
            <!--                 class="row">-->
            <!--          <q-btn round flat icon="insert_emoticon" class="q-mr-sm"/>-->
            <!--        </V3Emoji>-->

          </q-toolbar>
        </q-form>
      </q-footer>
    </div>
  </div>

</template>
<script lang="ts" setup>
import upload from "@/components/upload/index.vue"

import {useFriendStore} from "@/stores/modules/friend";
import {onMounted, ref, watch} from "vue";
import {useAppBoxs} from "@/stores/modules/appBox";
import {useGroupStore} from "@/stores/modules/group";
import useUserStore from "@/stores/modules/user";
import {MsgRecordQuery, MsgRecordVO} from "@/api/im/msgRecord/types";
import {useScrollArea} from "@/components/scrollarea";
import {addMsgRecord, listMsgRecord} from "@/api/im/msgRecord";
import {useQuasar} from "quasar";
import {bus, UPDATE_FRIEND_MSG, UPDATE_MSG_RECORD_MAP} from "@/boot/eventbus";
import {FriendVO} from "@/api/im/friend/types";

const $q = useQuasar()
const friendStore = useFriendStore()
const groupStore = useGroupStore()
const useStore = useUserStore()
const appStore = useAppBoxs()

// ref
const scrollAreaRef = ref<any>(null)

const preRecord = ref<number>(); // 上一次记录
const recordList = ref<MsgRecordVO[]>();// 聊天记录

const position = ref($q.screen.height)
const {thumbStyle, barStyle} = useScrollArea() // 滚动条样式

const msgFrom = ref<MsgRecordQuery>({pageNum: 0, pageSize: 50})


// 发送消息
function handleSubmit() {
  if (appStore.chatType === 1) {
    msgFrom.value.toId = friendStore.currFriendUser.userId
    msgFrom.value.toAppId = undefined
    msgFrom.value.toGroupId = undefined
  } else if (appStore.chatType == 2) {
    msgFrom.value.toGroupId = groupStore.currGroupInfo.id
    msgFrom.value.toId = undefined
    msgFrom.value.toAppId = undefined
  } else {
    msgFrom.value.toGroupId = groupStore.currGroupInfo.id
  }
  msgFrom.value.toType = appStore.chatType;
  msgFrom.value.senderId = useStore.currUser?.userId
  console.log("提交表单=>", msgFrom)
  // 发送消息
  addMsgRecord(msgFrom.value).then((res: any) => {
    console.log("addMsgRecord(msgFrom.value)=>", res)
    if (res.code === 200) {
      let list  = appStore.chatRecordMap.get(msgFrom.value.toId)
      list.push({...msgFrom.value})
      appStore.chatRecordMap.set(msgFrom.value.toId, list)
      // 输入框清空
      msgFrom.value.content = ""
      scrollToBottom()
    }
  })
}

function scrollToBottom() {
  scrollAreaRef.value.setScrollPosition('vertical', position.value, 300);
  position.value = position.value + 200;
}


watch(() => appStore.chatRecordMap, (n, o) => {
  // 当前聊天对象 1 用户 2群聊 3应用
  if (appStore.chatType === 1) {
    recordList.value = appStore.chatRecordMap.get(friendStore.currFriendUser?.userId)
  } else if (appStore.chatType === 2) {
    recordList.value = appStore.chatRecordMap.get(groupStore.currGroupInfo.id)
  } else {
    console.log("错误!!!")
  }
})


onMounted(() => {
  scrollToBottom()
  // 更新消息y
  bus.on(UPDATE_MSG_RECORD_MAP, (data: MsgRecordVO) => {
    let msgList = appStore.chatRecordMap.get(data.toId)
    console.log("msgList", msgList)
    msgList.push(data)
    appStore.chatRecordMap.set(data.toId, msgList)
    scrollToBottom()
  })

  // 查询好友聊天记录
  bus.on(UPDATE_FRIEND_MSG, (friend: FriendVO) => {
    // 得到好友聊天记录
    let query: MsgRecordQuery = {
      pageNum: 0, pageSize: 50,
      senderId: useUserStore().currUser?.userId,
      toId: friend.userId,
      toType: 1
    }
    listMsgRecord(query).then((record: any) => {
      if (record.code == 200) {
        useAppBoxs().chatRecordMap?.set(friend.userId, record.rows)
        useAppBoxs().chatType = 1
        recordList.value = useAppBoxs().chatRecordMap?.get(friend.userId)
      }

    })
  })


})

</script>


<style lang="sass">
.my-emoji
  vertical-align: middle
  height: 2em
  width: 2em


/* 设置字体和大小 */
.discord-font
  font-family: 'Whitney', 'Helvetica Neue', Helvetica, Arial, sans-serif
  font-size: 14px
  line-height: 1.4
  color: #dcddde

</style>
