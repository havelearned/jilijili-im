<template>
    <div class="max-w-sm mx-auto global-dark text-white rounded-lg overflow-hidden">
        <div class="p-5">
            <div class="flex items-center space-x-3 mb-4"><span
                class="relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full"><img
                class="aspect-square h-full w-full" alt="Profile picture"
                :src="data?.avatar"></span>
                <div class="flex-grow"><h1 class="text-xl font-bold">{{ data.nickName }}</h1>
                    <p class="text-sm text-gray-400">{{ data.userName }}</p></div>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="text-red-500">
                    <path
                        d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"></path>
                </svg>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                     class="text-green-500">
                    <path
                        d="M22 4s-.7 2.1-2 3.4c1.6 10-9.4 17.3-18 11.6 2.2.1 4.4-.6 6-2C3 15.5.5 9.6 3 5c2.2 2.6 5.6 4.1 9 4-.9-4.2 4-6.6 7-3.8 1.1 0 3-1.2 3-1.2z"></path>
                </svg>
            </div>
            <div class="global-dark p-3 rounded mb-4"><p class="text-sm mb-2">{{ data.userType }}</p>
                <div class="flex items-center justify-between"><span
                    class="text-xs text-gray-400">{{ data.remark ? data.remark : '这家伙很懒，啥都没留下!' }}</span>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="text-gray-400">
                        <path d="m6 9 6 6 6-6"></path>
                    </svg>
                </div>
            </div>
            <div class="text-sm mb-4" v-if="data?.createTime">
                <p></p>
                <p>{{ data.createTime }}</p>
                <p>加入此系统</p>
            </div>
            <!--      当前活动      -->
            <div class="bg-[#2C2F33] p-3 rounded mb-4" v-show="false">
                <div class="flex items-center mb-2">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="text-yellow-400">
                        <path d="M6 9H4.5a2.5 2.5 0 0 1 0-5H6"></path>
                        <path d="M18 9h1.5a2.5 2.5 0 0 0 0-5H18"></path>
                        <path d="M4 22h16"></path>
                        <path d="M10 14.66V17c0 .55-.47.98-.97 1.21C7.85 18.75 7 20.24 7 22"></path>
                        <path d="M14 14.66V17c0 .55.47.98.97 1.21C16.15 18.75 17 20.24 17 22"></path>
                        <path d="M18 2H6v7a6 6 0 0 0 12 0V2Z"></path>
                    </svg>
                    <span class="ml-2">League of Legends</span></div>
                <p class="text-xs">Ущелье призывателей (Обычная)</p>
                <p class="text-xs">В игре</p>
                <p class="text-xs">{{ data.createTime }}</p></div>
            <div class="text-sm global-dark"><p class="mb-2">备注</p>
                <div class=" p-3 rounded mb-4">
                    <q-input dense clearable class="text-xs text-gray-400"  label="添加备注" v-model="data.remark"  ></q-input>
                </div>

                <q-btn class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-secondary text-secondary-foreground hover:bg-secondary/80 h-10 px-4 py-2 w-full"
                @click="privateSendMsg">
                    私信 {{ data.nickName }}
                </q-btn>
            </div>
        </div>
    </div>

</template>
<script lang="ts" setup>

import {SysUserVo} from "@/api/login/login.data";
import {useFriendStore} from "@/stores/modules/friend";
import {Notify} from "quasar";
import useUserStore from "@/stores/modules/user";

const prop = defineProps({
    data: {
        type: Object as () => SysUserVo,
        required: false
    },
})

function privateSendMsg(){
    // 发起私密聊天
    // 如何是和当前用户聊天，稍微提示一下
    if (useFriendStore().currFriendUser?.userId.toString() === prop.data?.userId.toString()) {
        Notify.create({
            message: '已经在私聊了哦!',
            color: 'negative',
            position: 'top',
            timeout: 1000,
        })
    }else if(useUserStore().currUser.userId.toString() === prop.data?.userId.toString()){ // 和自己私聊
        Notify.create({
            message: '不能和自己私聊哦!',
            color: 'negative',
            position: 'top',
            timeout: 1000,
        })
    }
    else{
        console.log("发送私聊消息")
    }

}

</script>


<style scoped>

</style>
