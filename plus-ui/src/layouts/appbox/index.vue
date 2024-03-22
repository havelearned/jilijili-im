<template>
  <q-scroll-area
    :thumb-style="thumbStyle"
    :bar-style="barStyle"
    class="col"
    ref="firstRef"
    style="height: calc(100%); border-right: 1px solid #ddd"
  >
    <q-list>
      <q-item clickable v-ripple active-class="bg-red-400" :active="appBoxStore.active===0"
              @click="changeApp(0)">
        <q-item-section avatar>
          <q-avatar>
            <q-img src="@/assets/def_0.png"></q-img>
          </q-avatar>
          <q-tooltip anchor="center right" :offset="[14,-20]">好友</q-tooltip>
        </q-item-section>
      </q-item>

      <q-separator class="rounded"
                   v-if="appBoxStore.previewAppBox" size="5px" color="purple" spaced/>

      <q-item v-if="appBoxStore.previewAppBox" clickable v-ripple active-class="bg-red-400"
              :active="appBoxStore.previewAppBox.id === appBoxStore.active">
        <q-item-section avatar>
          <q-avatar rounded size="lg">
            <q-img :src="appBoxStore.previewAppBox.iconUrl" fit="cover" sizes="100px"></q-img>
          </q-avatar>
        </q-item-section>
        <q-item-section> {{ appBoxStore.previewAppBox.appName }}</q-item-section>
        <q-tooltip>{{ appBoxStore.previewAppBox.appName }}</q-tooltip>
      </q-item>

      <q-separator v-if="appBoxStore.currAppBoxList.length>0" size="5px" color="purple" spaced class="rounded"/>

      <q-item clickable v-ripple active-class="bg-red-400"
              v-for="(app,index) in appBoxArray" :active="appBoxStore.active === app.id"
              @click="changeApp(app.id)">
        <q-item-section avatar>
          <q-avatar rounded size="lg">
            <q-img :src="app.iconUrl" fit="cover" sizes="100px"></q-img>
          </q-avatar>
        </q-item-section>
        <q-item-section> {{ app.appName }}</q-item-section>
        <q-tooltip>{{ app.appName }}</q-tooltip>
      </q-item>

      <q-item clickable v-ripple @click="$router.push('/test')">
        <q-item-section avatar>
          <q-icon size="md" name="star"/>
        </q-item-section>
        <q-item-section> Star</q-item-section>
        <q-tooltip>Test</q-tooltip>
      </q-item>

      <q-item clickable v-ripple
              active-class="bg-red-400"
              @click="handelToMore" :active="appBoxStore.active===2">
        <q-item-section avatar>
          <q-icon size="md" name="expand_circle_down"/>
          <q-tooltip anchor="bottom left" self="center left">
            更多频道
          </q-tooltip>
        </q-item-section>

        <q-item-section> Send</q-item-section>
      </q-item>

      <q-separator/>

      <q-item clickable v-ripple>
        <q-item-section avatar>
          <q-icon size="md" name="add"/>
          <q-tooltip anchor="center right" self="center left">
            添加群聊
          </q-tooltip>
        </q-item-section>

        <q-item-section> Drafts</q-item-section>
      </q-item>
    </q-list>

  </q-scroll-area>

</template>
<script setup lang="ts">
import {useAppBox} from "./appbox.data"
import {onMounted} from "vue"
import {useRouter} from "vue-router"
import {useScrollArea} from "@/components/scrollarea"
import {useAppBoxs} from "@/stores/modules/appBox";
import {getAppBoxUserList} from "@/api/im/appBox";

const appBoxStore = useAppBoxs()
const {appBoxArray, fatchAppBoxList} = useAppBox()
const router = useRouter()

const {thumbStyle, barStyle} = useScrollArea()


// 改变容器
function changeApp(id: number) {
  appBoxStore.active = id
  if (appBoxStore.previewAppBox && appBoxStore.active !== appBoxStore.previewAppBox.id) {
    appBoxStore.previewAppBox = undefined
    appBoxStore.addPop = false
  }

  getAppBoxUserList().then((res:any)=>{
    if(res.code===200){
      appBoxStore.currAppBoxUserList = res.rows
      console.log("获取用户列表",appBoxStore.currAppBoxUserList)
    }
  })

  router.push({path: `/home/${id}`})
}

function handelToMore() {
  appBoxStore.active = 2
  router.push('/guild-list')
}

onMounted(() => {
  fatchAppBoxList()
  if (appBoxStore.previewAppBox) {
    appBoxStore.active = appBoxStore.previewAppBox.id
  } else {
    if (appBoxStore.currAppBox) {
      appBoxStore.active = appBoxStore.currAppBox.id
    } else {
      appBoxStore.active = 0
    }
  }
})


</script>
<style lang=""></style>
