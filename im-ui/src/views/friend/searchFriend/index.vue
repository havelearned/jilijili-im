<template>
  <div class="q-pa-md">
    <div>
      <q-toolbar-title>添加好友</q-toolbar-title>
    </div>
    <div>
      <q-input v-model="friendQueryFrom.userName"
               @keydown.enter="handelSearch"
               bg-color="white" filled clearable label="你可以通过好友id或者用户名添加好友">
        <template v-slot:append>
          <q-btn color="white" text-color="black" @click="handelSearch" label="搜索"/>
        </template>
      </q-input>

      <q-separator/>
    </div>

    <q-table
      grid
      title="Treats"
      :rows="friendList"
      :columns="columns"
      row-key="userId"
      hide-header>
      <template #item="props">
        <q-card class="my-card q-ma-sm">
          <q-img :src="props.row.avatar">
            <template #error>
              <q-img src="@/assets/def_0.png"></q-img>
            </template>
          </q-img>
          <q-card-section>
            <q-btn fab color="red" icon="add" class="absolute" @click="handelAddFriend(props.row.userId)"
                   style="top: 0; right: 12px; transform: translateY(-50%);">
              <q-tooltip>发送好友申请</q-tooltip>
            </q-btn>
            <div class="row no-wrap items-center">
              <div class="col text-h6 ellipsis text-black">
                {{ props.row.userName }}
                <q-icon v-if="props.row.sex === '0'" name="man" color="blue"></q-icon>
                <q-icon v-else-if="props.row.sex === '1'" name="woman" color="pink"></q-icon>
                <q-icon v-else name="?" color="grey"></q-icon>
              </div>
            </div>
          </q-card-section>
          <q-card-section class="q-pt-none">
            <div class="text-caption text-grey ">
              {{ props.row.userType }}
              <q-icon v-if="props.row.online === null" name="online_prediction" color="red">
                <q-tooltip>离线</q-tooltip>
              </q-icon>
              <q-icon v-else name="online_prediction" color="green">
                <q-tooltip>在线</q-tooltip>
              </q-icon>
            </div>
          </q-card-section>
          <q-separator/>
        </q-card>
      </template>
    </q-table>

  </div>
</template>
<script lang="ts" setup>

import {FriendForm, FriendQuery} from "@/api/im/friend/types";
import {addFriend, searchFriend} from "@/api/im/friend";
import {getLocalUserInfo} from "@/utils/auth";
import {useFriendStore} from "@/stores/modules/friend";

const $q = useQuasar()
const friendQueryFrom = ref<FriendQuery>({pageNum: 0, pageSize: 10})
const {friendList, setFriendList} = useFriendStore()
const columns = [
  {name: 'userName', required: true, field: "userName", align: 'left'},
  {name: 'userId', align: 'center', field: 'userId', sortable: true},
  {name: 'sex', field: 'sex', sortable: true},
  {name: 'userType', label: 'userType', field: 'userType'}
]


async function handelSearch() {
  let res = await searchFriend(friendQueryFrom.value)
  if (res.code === 200) {
    setFriendList(res.rows)
  } else {
  }
}

// 发送好友申请
async function handelAddFriend(friendId: string | number) {
  if (getLocalUserInfo().userId === friendId) {
    $q.notify({
      position: "top",
      message: '不能添加自己为好友!!!',
      color: 'negative'
    })
    return
  }
  let param: FriendForm = {
    userId: getLocalUserInfo().userId,
    friendId: friendId,
    relationStatus: 1
  }
  let res = await addFriend(param)
  if (res.code === 200) {
    $q.notify({
      position: "top",
      message: '已发送,好友申请!',
      color: 'positive'
    })
  } else {
    $q.notify({
      position: "top",
      message: res.msg,
      color: 'negative'
    })
  }

}

</script>


<style scoped lang="scss">

.my-card {
  width: 100%;
  max-width: 250px

}

</style>
