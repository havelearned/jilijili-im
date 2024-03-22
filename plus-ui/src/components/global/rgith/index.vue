<template>
  <q-drawer v-model="rgDrawerOpen" side="right" class="global-dark" show-if-above bordered :breakpoint="690"
            :width="250">
    <q-scroll-area style="height: calc(100%);width: 100%" :bar-style="barStyle" :thumb-style="thumbStyle">
      <q-toolbar>
        <div class="text-h6">当前活动</div>
        <q-space/>
        <div class="">好友</div>

        <div class="">群聊</div>
      </q-toolbar>
      <userComponents v-if="groupUserList.length > 0" :user-list="groupUserList" title="用户列表" show/>
      <div v-else class="absolute-center">
        <div class="card">
          <div class="card-img">
            <div class="img"></div>
          </div>
          <div class="card-title">Product title</div>
          <div class="card-subtitle">Product description. Lorem ipsum dolor sit amet, consectetur adipisicing elit.
          </div>
          <hr class="card-divider">
          <div class="card-footer">
            <div class="card-price"><span>$</span> 123.45</div>
            <button class="card-btn">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                <path
                  d="m397.78 316h-205.13a15 15 0 0 1 -14.65-11.67l-34.54-150.48a15 15 0 0 1 14.62-18.36h274.27a15 15 0 0 1 14.65 18.36l-34.6 150.48a15 15 0 0 1 -14.62 11.67zm-193.19-30h181.25l27.67-120.48h-236.6z"></path>
                <path
                  d="m222 450a57.48 57.48 0 1 1 57.48-57.48 57.54 57.54 0 0 1 -57.48 57.48zm0-84.95a27.48 27.48 0 1 0 27.48 27.47 27.5 27.5 0 0 0 -27.48-27.47z"></path>
                <path
                  d="m368.42 450a57.48 57.48 0 1 1 57.48-57.48 57.54 57.54 0 0 1 -57.48 57.48zm0-84.95a27.48 27.48 0 1 0 27.48 27.47 27.5 27.5 0 0 0 -27.48-27.47z"></path>
                <path
                  d="m158.08 165.49a15 15 0 0 1 -14.23-10.26l-25.71-77.23h-47.44a15 15 0 1 1 0-30h58.3a15 15 0 0 1 14.23 10.26l29.13 87.49a15 15 0 0 1 -14.23 19.74z"></path>
              </svg>
            </button>
          </div>
        </div>

      </div>
      <!--      <userCard ></userCard>-->
    </q-scroll-area>
  </q-drawer>
</template>
<script lang="ts" setup>

import userComponents from "@/components/user/userlist/index.vue"

import {reactive, ref, watch} from "vue";
import {useScrollArea} from "@/components/scrollarea";
import {useGroupStore} from "@/stores/modules/group";
import {useFriendStore} from "@/stores/modules/friend";
import {useAppBoxs} from "@/stores/modules/appBox";

const {thumbStyle, barStyle} = useScrollArea()
const skeletonRef = ref<any>(null)
const rgDrawerOpen = ref(true)

const groupStore = useGroupStore()
const friendStore = useFriendStore()
const appBoxStore = useAppBoxs()

const groupUserList = ref<Array<any>>([])

let currentChatUser = reactive({})

//监听 群聊用户组
watch(() => groupStore.currGroupUserList, (n: Array<any>, o: Array<any>) => {
    skeletonRef.value?.setCount(n.length)
    skeletonRef.value?.showLoading()
    setTimeout(() => {
      groupUserList.value = n
      currentChatUser = {}
      skeletonRef.value?.hideLoading()
    }, 1000)
  }
)

// 监听 好友用户组
watch(() => friendStore.currFriendUser, (n, o) => {
    skeletonRef.value?.showLoading()
    setTimeout(() => {
      currentChatUser = {...n}
      groupUserList.value = []
      skeletonRef.value?.hideLoading()
    }, 1000)
  }
)

// 监听 应用用户组
watch(() => appBoxStore.currAppBoxUserList, (n: any, o: any) => {
  skeletonRef.value?.showLoading()
  setTimeout(() => {
    groupUserList.value = n
    skeletonRef.value?.hideLoading()
  })
})


</script>


<style scoped lang="scss">
@import "@/css/card/def";


</style>
