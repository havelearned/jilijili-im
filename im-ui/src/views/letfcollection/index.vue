<!--
  @FileDescription:  ,
  @Date: 2024/2/18 18:10 ,
  @version: 0.0.1,
  @Author: 罗家炬,
 -->
<template>
  <q-drawer v-model="leftDrawerOpen"
            class="global-dark" show-if-above bordered :breakpoint="690"
            :mini-width="350">
    <q-toolbar>
        <span class="text-h6">{{
            useUser.currUser && useUser.currUser.userName ?
              useUser.currUser.userName : ''
          }}</span>
      <q-space/>
      <q-avatar class="cursor-pointer">
        <img v-if="useUser.currUser && useUser.currUser.avatar" :src="useUser.currUser.avatar"/>
        <img v-else src="@/assets/def_0.png">
      </q-avatar>
      <q-btn round flat icon="more_vert">
        <q-menu auto-close :offset="[-30, 8]">
          <q-list class="global-dark">
            <q-item clickable>
              <q-item-section @click="()=>{useUser.logOut();$router.push('/login')}">
                退出
              </q-item-section>
            </q-item>
            <q-item clickable @click="showMeInfo">
              个人资料
            </q-item>
          </q-list>
        </q-menu>
      </q-btn>
    </q-toolbar>
    <q-list bordered separator class="">
      <q-item clickable v-ripple :active="true" active-class="bg-grey-5">
        <q-item-section avatar>
          <q-icon name="people"/>
        </q-item-section>
        <q-item-section>好友</q-item-section>
        <q-item-section side>上次访问时间</q-item-section>
      </q-item>

      <q-item v-ripple active-class=" text-grey-8">
        <q-item-section avatar>
          <q-icon name="shop"/>
        </q-item-section>
        <q-item-section>商店</q-item-section>
        <q-item-section side>上次访问时间</q-item-section>
      </q-item>
    </q-list>
    <q-separator size="0.5rem"></q-separator>
    <q-banner inline-actions rounded dark class="" size="sm">
      <q-btn flat size="1rem" label="消息">
        <q-badge color="red" floating>4</q-badge>
      </q-btn>
      <template v-slot:action>
        <q-btn-dropdown dense flat dropdown-icon="add">
          <q-list class="global-dark">
            <q-item>
              <q-btn flat label="新建私聊" @click="console.log(addGroupChatDialogRef?.show())"></q-btn>
            </q-item>
            <q-item>
              <q-btn flat label="添加好友" @click="friendStore.isShowSearchFriend = true"></q-btn>
            </q-item>
          </q-list>
        </q-btn-dropdown>

      </template>
    </q-banner>
    <q-tabs align="left" no-caps v-model="tabs" class="shadow-2">
      <q-tab name="groupChat" label="群聊"/>
      <q-tab name="online" label="好友" @click="()=>{friendQueryForm.relationStatus = 2;fatchFriendList()}"/>
      <q-tab name="valid" label="待验证" @click="()=>{friendQueryForm.relationStatus = 1;fatchFriendList()}"/>
      <q-tab name="backList" label="黑名单" @click="()=>{friendQueryForm.relationStatus = 3;fatchFriendList()}"/>
    </q-tabs>
    <q-scroll-area :style="{ height: `calc(${$q.screen.height}px - 340px)` }">
      <q-tab-panels v-model="tabs" class="global-dark" animated swipeable
                    vertical transition-prev="jump-up" transition-next="jump-up">
        <q-tab-panel name="groupChat">
          <group :ref="groupRef"></group>
        </q-tab-panel>
        <q-tab-panel name="online">
          <q-toolbar>
            <q-input rounded outlined dense class="WAL__field full-width"
                     bg-color="white" v-model="friendQueryForm.userName"
                     @keydown.enter="handelSearchFriend"
                     placeholder="搜索好友用户名称 开始新对话">
              <template v-slot:append>
                <q-btn dense icon="search"
                       @click="handelSearchFriend"/>
              </template>
            </q-input>
          </q-toolbar>
          <q-list>
            <q-item v-for="(friend, index) in friendList" :key="friend.userId"
                    clickable
                    @dblclick="clickCurrFriendUser(friend)">
              <q-item-section avatar>
                <q-avatar>
                  <dotOnline :color="'red'" class="absolute-top-left"></dotOnline>
                  <img v-if="friend && friend.avatar" :src="friend.avatar"/>
                  <img v-else src="@/assets/def_0.png">
                  <dotOnline class="absolute-bottom-right" :title="'在线'" v-if="friend.online"></dotOnline>
                  <dotOnline :color="'grey'" class="absolute-bottom-right" :title="'离线'" v-else></dotOnline>
                </q-avatar>
              </q-item-section>

              <q-item-section>
                <q-item-label lines="1">
                  {{ friend ? friend.userName : '' }}
                </q-item-label>
                <q-item-label class="conversation__summary" caption>
                  <q-icon name="check"/>
                  {{ friend ? friend.userType : '' }}
                </q-item-label>
              </q-item-section>
              <q-space/>
              <q-item-section side>
                <q-btn-dropdown dense>
                  <q-list class="global-dark">
                    <q-item @click="handelFriendVideo">
                      <q-item-section class="">
                        <q-btn dense color="purple" label="视频通话"></q-btn>
                      </q-item-section>
                    </q-item>

                    <q-item @click="handelFriendVideo">
                      <q-item-section>
                        <q-btn dense color="orange" label="语音通话"></q-btn>
                      </q-item-section>
                    </q-item>
                    <q-item @click="handelFriendVideo">
                      <q-item-section>
                        <q-btn dense class="text-red-400" label="屏蔽此人"
                               @click="handelShieldFriend(friend)"></q-btn>
                      </q-item-section>
                    </q-item>

                    <q-item>
                      <q-item-section>
                        <q-btn dense class="text-red-400" label="删除此人"
                               @click="handelDeleteFriend(friend.userId)"></q-btn>
                      </q-item-section>
                    </q-item>

                    <q-item @click="handelFriendVideo">
                      <q-item-section>
                        <q-btn-dropdown dense label="导出记录">
                          <q-list class="global-dark">
                            <q-item @click="handelFriendVideo">
                              <q-item-section>
                                <q-btn dense label="PDF" @click="handelExport"></q-btn>
                              </q-item-section>
                            </q-item>

                            <q-item @click="handelFriendVideo">
                              <q-item-section>
                                <q-btn dense label="Image"></q-btn>
                              </q-item-section>
                            </q-item>
                          </q-list>
                        </q-btn-dropdown>
                      </q-item-section>
                    </q-item>
                  </q-list>
                </q-btn-dropdown>
              </q-item-section>

            </q-item>
          </q-list>
        </q-tab-panel>
        <q-tab-panel name="valid">
          <q-toolbar>
            <q-input rounded outlined dense class="WAL__field full-width"
                     bg-color="white" v-model="friendQueryForm.userName"
                     @keydown.enter="handelSearchFriend"
                     placeholder="搜索好友用户名称 开始新对话">
              <template v-slot:append>
                <q-btn dense icon="search"
                       @click="handelSearchFriend"/>
              </template>
            </q-input>
          </q-toolbar>
          <q-list>
            <q-item
              v-for="(friend, index) in friendList"
              :key="friend.userId"
              clickable
              @dblclick="()=>{setCurrentConversation(index);
                currentFriendId = friend.userId;
                panels = friend.userId
              }"
            >
              <q-item-section avatar>
                <q-avatar>
                  <img v-if="friend && friend.avatar" :src="friend.avatar"/>
                  <img v-else src="@/assets/def_0.png">
                  <q-badge color="red" floating>4</q-badge>
                </q-avatar>
              </q-item-section>

              <q-item-section>
                <q-item-label lines="1">
                  {{ friend ? friend.userName : '' }}
                </q-item-label>
                <q-item-label class="conversation__summary" caption>
                  <q-icon name="check"/>
                  {{ friend ? friend.userType : '' }}
                </q-item-label>
              </q-item-section>
              <q-space/>
              <q-item-section>
                <q-btn dense class="text-green-5" @click="handelAcceptFriend(friend)">同意</q-btn>
                <q-btn dense class="text-red-400" @click="handelDeleteFriend(friend.userId)">拒绝</q-btn>
              </q-item-section>
            </q-item>
          </q-list>
        </q-tab-panel>
        <q-tab-panel name="backList">
          <q-toolbar>
            <q-input rounded outlined dense class="WAL__field full-width"
                     bg-color="white" v-model="friendQueryForm.userName"
                     @keydown.enter="handelSearchFriend"
                     placeholder="搜索好友用户名称 开始新对话">
              <template v-slot:append>
                <q-btn dense icon="search"
                       @click="handelSearchFriend"/>
              </template>
            </q-input>
          </q-toolbar>
          <q-list>
            <q-item
              v-for="(friend, index) in friendList"
              :key="friend.userId"

              @dblclick="()=>{setCurrentConversation(index);
                currentFriendId = friend.userId;
                panels = friend.userId
              }"
            >
              <q-item-section avatar>
                <q-avatar>
                  <img v-if="friend && friend.avatar" :src="friend.avatar"/>
                  <img v-else src="@/assets/def_0.png">
                  <q-badge color="red" floating>4</q-badge>
                </q-avatar>
              </q-item-section>

              <q-item-section>
                <q-item-label lines="1">
                  {{ friend ? friend.userName : '' }}
                </q-item-label>
                <q-item-label class="conversation__summary" caption>
                  <q-icon name="check"/>
                  {{ friend ? friend.userType : '' }}
                </q-item-label>
              </q-item-section>
              <q-space/>
              <q-item-section side>
                <q-btn dense class="text-red-400" @click="handelDeleteFriend(friend.userId)">解除</q-btn>
              </q-item-section>
            </q-item>
          </q-list>
        </q-tab-panel>

      </q-tab-panels>

    </q-scroll-area>
  </q-drawer>
  <addGroupDialog ref="addGroupChatDialogRef"></addGroupDialog>
  <meInfo ref="meInfoRef"></meInfo>
</template>
<script lang="ts" setup>
import addGroupDialog from "@/views/group/dialog/addDialog.vue"
import dotOnline from "@/components/dot/online/index.vue"
import group from "@/views/group/index.vue"
import meInfo from "@/views/me/index.vue"

import {computed, onMounted, ref} from "vue";
import useUserStore from "@/stores/modules/user";
import {useHome} from "@/views/index.data";
import {useQuasar} from "quasar";
import {useFriendStore} from "@/stores/modules/friend";
import {SysUserVo} from "@/api/login/login.data";
import {listFriend} from "@/api/im/friend";
import {getUserByUserId} from "@/api/user";
import {listMsgRecord} from "@/api/im/msgRecord";
import {MsgRecordQuery} from "@/api/im/msgRecord/types";
import {useAppBoxs} from "@/stores/modules/appBox";
import {bus} from "@/boot/eventbus";

const friendList = ref<SysUserVo[]>([])

const {
  leftDrawerOpen, tabs, panels, currentFriendId,
  setCurrentConversation
} = useHome()

const {
  friendQueryForm, setCurrFriendUser,
  handelSearchFriend, handelDeleteFriend, handelAcceptFriend, handelShieldFriend
} = useFriendStore()
const friendStore = useFriendStore()

const useUser = useUserStore()


const $q = useQuasar();

const addGroupChatDialogRef = ref(null)
const groupRef = ref<any>(null)

const meInfoRef = ref(null)

const style = computed(() => ({
  height: $q.screen.height + "px",
}));

function showMeInfo() {
  if (meInfoRef.value) {
    meInfoRef.value?.show()
  }
}

function clickCurrFriendUser(friend: SysUserVo) {
  getUserByUserId(friend.userId).then((res: any) => {
    if (res.code == 200) {
      // 得到好友信息
      friendStore.currFriendUser = res.data.user
      bus.emit('UPDATE_FRIEND_MSG',friend)
    }
  })
}

// 获取好友列表
async function fatchFriendList() {
  // 获取当前用户id
  friendQueryForm.userId = useUser.currUser?.userId

  let res = await listFriend(friendQueryForm)
  if (res.code === 200) {
    friendList.value = res.rows
  }

}

/**
 * @description: 好友视频
 */
function handelFriendVideo() {
}


/**
 * @description: 导出
 */
function handelExport() {
}


onMounted(async () => {
  await useUser.getUserinfo()
  await fatchFriendList()


})

</script>


<style scoped>

</style>
