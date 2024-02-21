<template>
  <q-toolbar>
    <q-input rounded outlined dense class="WAL__field full-width"
             bg-color="white" v-model="groupChatQueryFrom.groupName"
             @keydown.enter="fatchGroupChatList"
             placeholder="搜索群名称,开始新对话">
      <template v-slot:append>
        <q-btn dense icon="search"
               @click="fatchGroupChatList"/>
      </template>
    </q-input>
  </q-toolbar>
  <q-list>
    <q-item clickable v-for="(group,index) in groupStore.groupList" :key="group.id"
            @dblclick="fatchGroupChatUserList(group)">
      <q-item-section avatar>
        <q-avatar>
          <img v-if="group && group.avatar" :src="group.avatar"/>
          <img v-else src="@/assets/def_0.png">
          <q-badge color="red" floating>4</q-badge>
        </q-avatar>
      </q-item-section>
      <q-item-section>
        <q-item-label lines="1">
          <div class="ellipsis">{{ group.groupName }}</div>
        </q-item-label>
      </q-item-section>
      <q-space/>
      <q-item-section side>
        <q-btn-dropdown dense>
          <q-list class="global-dark">
            <q-item>
              <q-item-section>
                <q-btn dense class="text-red-400" label="退出此群"
                       @click="quitGroupChat(group.id,null)"></q-btn>
              </q-item-section>
            </q-item>
            <q-item v-if="getLocalUserInfo().userId === group.groupLeaderId">
              <q-item-section>
                <q-btn dense class="text-red-400" label="解散"
                       @click="dissolutionGroupChat(group.id,null)"></q-btn>
              </q-item-section>
            </q-item>
          </q-list>
        </q-btn-dropdown>
      </q-item-section>
      <q-separator color="red"/>
    </q-item>
  </q-list>
</template>
<script lang="ts" setup>

import {getLocalUserInfo} from "@/utils/auth";
import {onMounted} from "vue";
import {useGroupStore} from "@/stores/modules/group";
import {getGroupChatList, getUserList} from "@/api/im/chatGroup";
import {useFriendStore} from "@/stores/modules/friend";
import {ChatGroupData} from "@/api/im/chatGroup/type";
import {useAppBoxs} from "@/stores/modules/appBox";


const {
  groupChatQueryFrom,
  fatchGroupChatList,  quitGroupChat, dissolutionGroupChat
} = useGroupStore()
const groupStore = useGroupStore()



const fatchGroupChatUserList = async (group: ChatGroupData) => {
  useAppBoxs().chatType = 2
  useFriendStore().currFriendUser = null
  groupStore.currGroupInfo = group
  groupStore.groupChatQueryFrom.id = group.id
  let res = await getUserList(groupStore.groupChatQueryFrom);
  if (res.code === 200) {
    groupStore.currGroupUserList = res.rows
  }
}

onMounted(() => {
  // await fatchGroupChatList();
  getGroupChatList(groupChatQueryFrom).then((res: any) => {
    if (res.code === 200) {
      groupStore.groupList = res.rows;
    }
  })

});

defineExpose({fatchGroupChatList});

</script>


<style scoped>

</style>
