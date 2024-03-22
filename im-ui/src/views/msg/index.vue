<template>
    <div class=" row justify-between ">
        <div style="width: 100%; " id="parentBox">
            <q-scroll-area :style="{ height: `calc(${$q.screen.height}px - 140px` }" ref="scrollAreaRef"
                           :bar-style="barStyle" :thumb-style="thumbStyle"
                           class="bg-[#18191c] custom-scroll-area">
                <Communicate class="fixed-top z-40"></Communicate>

                <div class="bg-[#18191c] text-white p-4 " v-if="recordList.length > 0">
                    <!--                     消息记录列表-->
                    <div class="space-y-6 q-pa-md "
                         v-for="(record,index) in recordList"
                         :key="record.id">
                        <div class="border-l-4 border-[#bd1e59] pl-4 hover:bg-black q-pa-xs rounded "
                             @click.right="(e)=>rightClick(e,record)">
                            <PopupMenu :data="record" :context-menu="true"></PopupMenu>

                            <div class="flex items-center justify-between">
                                <div class="flex items-center space-x-2">
                                  <span
                                      class="relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full">
                                    <q-avatar size="50px" rounded >
                                     <img class="" :alt="record.author?.nickName"
                                          :src="record.author?.avatar">
                                        <PopupMenu :data="record" :context-menu="true">
                                            <template v-slot:cardPale="card">
                                                <CommonCardPale :data="card.data?.author"></CommonCardPale>
                                            </template>
                                        </PopupMenu>
                                    </q-avatar>
                                    <span class="flex h-full w-full items-center justify-center rounded-full bg-muted">RS
                                   </span>
                                  </span>
                                    <div><p class="font-bold">{{ record.author?.nickName }}</p>
                                        <p class="text-xs text-[#b0b3b8]">{{ formatDate(record?.createTime) }}</p>
                                    </div>
                                </div>
                                <!-- 消息更多下拉-->
                                <q-btn size="md" icon="more" class="p-1.5">
                                    <PopupMenu :data="record" :context-menu="false"></PopupMenu>
                                </q-btn>

                            </div>
                            <!-- 内容处理 -->
                            <div class="pl-9 mt-2 discord-font q-ma-md">
                                <!-- 文件 -->
                                <div v-if=" record.content?.files" class="q-gutter-md  row items-start">
                                        <q-img v-for="(item,index) in record.content?.files" :key="index"  spinner-color="white"
                                               :ratio="4/3" fit="contain"
                                               class="rounded-borders hover:scale-110 transition-transform  w-2/12 max-w-6/12 max-h-6/12"
                                               :src="item.url"></q-img>
                                </div>

                                <!-- 音频 -->
                                <div v-if="record.content?.audio" class="q-pa-md">
                                    <audio controls :src="record.content?.audio"></audio>
                                </div>

                                <!--普通文本-->
                                <div class="q-pa-md">
                                    {{ record.content.text }}
                                </div>
                            </div>
                            <!--              反应  -->
                            <div class="flex items-center space-x-2 mt-2 pl-13" v-if="record.action">
                                <q-bar v-if="record.action?.likeCount">
                                    <q-icon size="xs" color="yellow" name="thumb_down"/>
                                    <span class="pl-2">{{ record.action.likeCount }}</span>
                                </q-bar>
                                <q-bar v-if="record.action?.fullCount">
                                    <q-icon size="xs" color="yellow" name="sentiment_satisfied"/>
                                    <span class="pl-2">{{ record.action.fullCount }}</span>
                                </q-bar>
                                <q-bar v-if="record.action?.endorseCount">
                                    <q-icon size="xs" color="yellow" name="thumb_up"/>
                                    <span class="pl-2">{{ record.action.endorseCount }}</span>
                                </q-bar>
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
                <!--                    没有消息显示默认内容-->
                <div v-else>
                    <div class="text-center q-pa-md ">
                        <q-icon name="sentiment_dissatisfied" size="50px" color="grey-5"/>
                        <p class="text-center text-grey-5">暂无消息</p>
                    </div>
                </div>
            </q-scroll-area>
            <q-footer class="text-white global-dark q-pa-md">
                <q-form v-model="msgFrom" @submit="handleSubmit">
                    <div class="full-width  q-pa-md" v-if="fileListBar">
                        <upload class="full-width" v-model="msgFrom.content.files"/>
                    </div>
                    <q-toolbar class="row">
                        <recordAudio v-model="msgFrom.content.audio" @handleSubmit="handleSubmit"/>
                        <q-btn round flat icon="perm_media" class="q-mr-sm" @click="fileListBar=!fileListBar">
                        </q-btn>
                        <q-input ref="inputRef" clearable standout filled bg-color="white" autofocus autogrow
                                 placeholder="请输入内容,ctrl+enter发送"
                                 maxlength="1200" class="col-grow text-white" type="textarea"
                                 @keyup.ctrl.enter="handleSubmit" v-model.trim="msgFrom.content.text"/>
                        <q-btn round flat icon="send" type="submit"/>
                        <V3Emoji :recent="true"
                                 @clickEmoji="handleClickEmoji"
                                 @close="handleEmClose"
                                 :fulldata="true"
                                 style="width: 50px"
                                 :size="'mid'"
                                 class="row">
                            <q-btn round flat icon="insert_emoticon" class="q-mr-sm"/>
                        </V3Emoji>

                    </q-toolbar>
                </q-form>
            </q-footer>
        </div>

        <localAudio ref="audioRef"></localAudio>
    </div>
</template>
<script lang="ts" setup>
import upload from "@/components/upload/index.vue"
import PopupMenu from "@/components/PopupMenu/index.vue"
import {useFriendStore} from "@/stores/modules/friend";
import {useAppBoxs} from "@/stores/modules/appBox";
import {useGroupStore} from "@/stores/modules/group";
import useUserStore from "@/stores/modules/user";
import {MsgRecordQuery, MsgRecordVO} from "@/api/im/msgRecord/types";
import {useScrollArea} from "@/components/scrollarea";
import {addMsgRecord, updateMsgRead} from "@/api/im/msgRecord";
import {useWs} from "@/stores/modules/ws";
import {bus} from "@/boot/eventbus";
import {getToken} from "@/utils/auth";
import V3Emoji from "vue3-emoji";
import {formatDate, insertContent} from "@/utils/verify";
import Communicate from "@/components/communicate/Communicate.vue";

const $q = useQuasar()

const useWsStore = useWs();
const friendStore = useFriendStore()
const groupStore = useGroupStore()
const useStore = useUserStore()
const appStore = useAppBoxs()

// ref
const scrollAreaRef = ref<any>(null)
const audioRef = ref()
const inputRef = ref()

const preRecord = ref<number>(); // 上一次记录
const recordList = ref<MsgRecordVO[]>([]);// 聊天记录

const position = ref($q.screen.height)
const {thumbStyle, barStyle} = useScrollArea() // 滚动条样式

const fileListBar = ref(false)
const popupMenuShow = ref(false)
const msgFrom = ref<MsgRecordQuery>({pageNum: 0, pageSize: 50, content: {video: [], text: '', files: [], audio: ''}})

function previewImg() {
    console.log("hello")
}

// 选中表情
function handleClickEmoji(v: any) {

    if (!msgFrom.value.content.text) msgFrom.value.content.text = ''
    nextTick(() => {
        let input = inputRef.value.$el.querySelector('textarea')
        msgFrom.value.content.text = insertContent(msgFrom.value.content.text, v.emoji, input.selectionStart)
    })
}

function handleEmClose() {

}

function handleSubmit1() {
    console.log("提交表单=>", msgFrom.value)
}

// 录音前:清空 输入框
function beforeRecord() {
    // console.log(msgFrom.value)
    // msgFrom.value.content.text = ''
    // msgFrom.value.content.files = []
}

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
    msgFrom.value.content = JSON.stringify(msgFrom.value.content)
    console.log("提交表单=>", msgFrom)

    // 发送消息
    addMsgRecord(msgFrom.value).then((res: any) => {
        console.log("addMsgRecord(msgFrom.value)=>", res)
        if (res.code === 200) {
            res.data.content = JSON.parse(res.data.content)
            if (appStore.chatType === 1) {
                recordList.value?.push(res.data)
            }
            // 输入框清空
            msgFrom.value.content = {}
            fileListBar.value = false
            scrollToBottom()
        }
    })
}


// 下拉滚动到底部
function scrollToBottom() {
    position.value = position.value + (250 * recordList.value.length);
    nextTick(() => {
        if (scrollAreaRef.value) {
            scrollAreaRef.value.setScrollPosition('vertical', position.value, 300);
        }
    })

}

// 鼠标右击
function rightClick(e: any, data: any) {
    e.preventDefault();
    console.log(e, data)
}


/**
 * 设置消息已读
 * @param param
 */
function setMsgReadStatus(param: any) {
    updateMsgRead(param).then((res: any) => {
        if (res.code === 200) {
            // 设置消息已读
        }
    })

}

// 监听聊天数据,聊天内容转为json格式
bus.on("UPDATE_FRIEND_MSG", (data: MsgRecordVO[]) => {
    // data = fmData(data)
    console.log("聊天记录=>", data)
    clearRecord();
    data.forEach((item: MsgRecordVO) => {
        if (item.content)
            try {
                if (JSON.parse(item.content.toString())) {
                    item.content = JSON.parse(item.content.toString())
                }
            } catch (e) {
                console.log(e);
            }
    })
    recordList.value = data
    scrollToBottom()
})

// 清空聊天记录
function clearRecord() {
    recordList.value = []
}


// 连接websocket
function connWs() {
    useWsStore.initWs(`ws://localhost:8080/im-websocket?token=${getToken()}`)
    useWsStore.websocket.addEventListener("message", (e: MessageEvent) => {
        // 确保数据是字符串类型
        try {
            let res = JSON.parse(e.data);
            let msgVo: MsgRecordVO = res.data;
            console.log("接收到消息=>", msgVo)

            // 处理接收到的消息
            if (msgVo.toType === 1) {  // 用户消息
                let friendId = friendStore.currFriendUser.userId;
                if (msgVo.senderId.toString() === friendId.toString()) {
                    // 设置消息为已读
                    setMsgReadStatus({toType: msgVo.toType, id: msgVo.id})
                    bus.emit("UPDATE_FRIEND_MSG_DOT", msgVo)
                    audioRef.value.play()
                } else { // 其他用户发来消息
                }
            }
            msgVo.content = JSON.parse(msgVo.content.toString());
            recordList.value.push(msgVo);
            scrollToBottom();
        } catch (error) {
            console.error('Error parsing JSON:', error);
        }
    });
}

nextTick(() => {
    connWs()
})
bus.on('DELETE_CLEAR_RECORD', () => {
    clearRecord()
})

onUnmounted(() => {
    bus.off("UPDATE_FRIEND_MSG_DOT")
    // bus.off("UPDATE_FRIEND_MSG")
    bus.off("DELETE_CLEAR_RECORD")
})

</script>


<style lang="scss">
.my-emoji {
    vertical-align: middle;
    height: 2em;
    width: 2em;
}

/* 设置字体和大小 */
.discord-font {
    font-family: 'Whitney', 'Helvetica Neue', Helvetica, Arial, sans-serif;
    font-size: 14px;
    line-height: 1.4;
    color: #dcddde;
}
</style>
