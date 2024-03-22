<template>
  <q-drawer v-model="rgDrawerOpen" width="250"
            side="left" class="global-dark" show-if-above bordered :breakpoint="690">
    <q-scroll-area style="height: calc(100% - 150px); margin-top: 150px; border-right: 1px solid #ddd">
      <q-list padding>
        <q-item clickable v-ripple class="bi-menu-button"
                v-for="(info,index) in applicationInfo" :key="info.id"
                @click="channerClickHandel(info)" :active="active === info.id">
          <q-item-section avatar>
            <div v-if="info.parentId ===  0 ">
              <q-avatar size="lg">
                <q-img :src="info.iconUrl"></q-img>
              </q-avatar>
            </div>
            <div v-else v-html="info.iconUrl"></div>
          </q-item-section>
          <q-item-section>
            {{ info.appName }}
          </q-item-section>
          <q-item-section side>
            <q-icon name="person_add"></q-icon>
          </q-item-section>
        </q-item>
      </q-list>
    </q-scroll-area>

    <q-img class="absolute-top"
           :src="appInfo.coverUrl" style="height: 150px">
      <div class="absolute-top text-center">
        <div class="column">
          <div class="text-weight-bold">
            <q-btn dense icon="local_fire_department" color="red" flat>{{ appInfo.popularity }}</q-btn>
            {{ appInfo.appName }}
          </div>
          <q-btn-dropdown class="absolute-right" flat>
            <q-btn>退出</q-btn>
          </q-btn-dropdown>
        </div>
      </div>
        <template #error>
            <q-img src="https://cdn.quasar.dev/img/parallax1.jpg" style="height: 150px" />
        </template>
    </q-img>

      <GlobalBottomMe></GlobalBottomMe>
  </q-drawer>
</template>
<script lang="ts" setup>

import {useApplicationInfo} from "@/components/global/left/left.data";
import {useAppBoxs} from "@/stores/modules/appBox";
import {AppboxApplicationVo} from "@/api/im/appBox/type";
import useUserStore from "@/stores/modules/user";

const route = useRoute()
const appBoxStore = useAppBoxs()
const userStore = useUserStore()
const {fetchApplicationInfo, appInfo, applicationInfo} = useApplicationInfo()

const rgDrawerOpen = ref(true)

const appId = ref('')
const active = ref<any>(appInfo.value.id)


// 监听路由参数
watch(
  () => route.params,
  (newParams, oldParams) => {
    appId.value = <string>newParams.id
  }, {immediate: true} // 立即执行一次 handler
);

// 监听当前应用变化
watch(
  () => appBoxStore.active, (newVal, oldVal) => {
    active.value = newVal ? newVal : 0
    if (newVal) {
      fetchApplicationInfo(active.value)
    } else {

    }

  }, {immediate: true}
);

function channerClickHandel(info: AppboxApplicationVo) {
  active.value = info.id
  appBoxStore.hitTitleBar = {
    isAppBox: true,
    id: info.id,
    title: info.appName,
    iconUrl: info.iconUrl,
    avatar: undefined,
    remark: info.remark
  }
  // bus.emit("changeCurrentHit", info)
}


onMounted(() => {

})

defineExpose({
  // init
})
</script>


<style scoped>

</style>
