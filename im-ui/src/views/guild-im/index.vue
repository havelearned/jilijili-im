<template>
  <q-layout view="lhh lpR lFr">
    <q-drawer dark
              v-model="leftDrawerOpen"
              :mini="miniState"
              :breakpoint="700"
              bordered
              :class="'global-dark'+ $q.dark.isActive ? 'bg-grey-9' : 'bg-grey-3'"
    >
      <appbox></appbox>
    </q-drawer>
    <q-page-container>
      <div class="bg-[#5865F2] min-h-screen">
        <div class="flex">
          <nav class="w-60 flex flex-col bg-[#202225] p-5 space-y-6">
            <q-btn flat @click="activeLabel = '主页';applicationBoxQueryFrom.classesId=undefined;fatchApplicationList()"
                   :class="`${styleClass} + ${activeLabel === '主页' ? 'bg-[#5865F2]' : '' }`">
              <template #default>
                <q-item dense>
                  <q-item-section>
                    <q-avatar>
                      <img src="@/assets/def_0.png" alt="">
                    </q-avatar>
                  </q-item-section>
                  <q-item-section>
                    <div class="text-h6">主页</div>
                  </q-item-section>
                </q-item>
              </template>
            </q-btn>
            <q-btn v-for="(item ,index) in classesList" :key="item.id" flat @click="handelAppBox(item)"
                   :class="`${styleClass} + ${activeLabel === item.classesName ? 'bg-[#5865F2]' : '' }`">
              <template #default>
                <q-item dense>
                  <q-item-section>
                    <div v-html="item.icon"></div>
                  </q-item-section>
                  <q-item-section>
                    <div class="text-h6">{{ item.classesName }}</div>
                  </q-item-section>
                </q-item>
              </template>
            </q-btn>
          </nav>
          <main class="flex-1 global-dark">
            <div class="p-5">
              <q-scroll-area :style="{ height: `calc(${$q.screen.height}px - 40px` }">
                <div class="bg-amber-600 text-white p-10 rounded-lg mb-6"><h1 class="text-2xl mb-3">找到自己的社区</h1>
                  <p class="mb-6">从游戏、音乐到科学技术，都有你的一片天地。</p>
                  <q-input filled outlined square dense dark color="dark" v-model="applicationBoxQueryFrom.appName"
                           label="搜索应用" @keydown.enter="fatchApplicationList"></q-input>
                </div>
                <section><h2 class="text-white text-xl mb-4">推荐应用</h2>
                  <div class="grid grid-cols-4 gap-4">
                    <div v-for="(appbox ,index) in appBoxList" :key="appbox.id"
                         class="tilt-in-fwd-tr rounded-lg border bg-card text-card-foreground shadow-sm w-full  "
                         data-v0-t="card" @click="handelClick(appbox)">
                      <div class="p-6">
                        <ImagePreview :src="appbox.coverUrl"  :height="'150'"></ImagePreview>
                        <div class="p-4">
                          <h3 class="text-white text-lg mb-2">{{ appbox.appName }}</h3>
                          <p class="text-gray-400 text-sm mb-4 ellipsis-3-lines">{{appbox.remark}}.</p>
                          <div class="flex justify-between text-gray-300 text-sm">
                            <span>{{appbox.pv}} 访问量</span>
                            <span>{{ appbox.popularity}} 热度</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </section>
              </q-scroll-area>
            </div>
          </main>
        </div>
      </div>
    </q-page-container>
  </q-layout>
</template>
<script lang="ts" setup>
import appbox from '@/layouts/appbox/index.vue'
import ImagePreview from "@/components/ImagePreview/index.vue"
import {listClasses} from "@/api/im/classes";
import {ClassesVO} from "@/api/im/classes/types";
import {AppboxapplicationQuery, AppboxApplicationVo} from "@/api/im/appBox/type";
import {listAppboxapplication} from "@/api/im/appBox";
import {useAppBoxs} from "@/stores/modules/appBox";

const router = useRouter()
const appBoxStore =  useAppBoxs()

const miniState = ref(true)
const leftDrawerOpen = ref(true)
const activeLabel = ref("主页")
const styleClass = "inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 text-white"

const applicationBoxQueryFrom = ref<AppboxapplicationQuery>({parentId: 0})
const classesList = ref<ClassesVO[]>([]);
const appBoxList = ref<AppboxApplicationVo[]>([]);



function handelClick(appBox:AppboxApplicationVo){
  appBoxStore.previewAppBox = appBox
  appBoxStore.addPop = true
  router.replace({path:'/home/'+appBox.id})
  console.log(appBoxStore.previewAppBox)

}

function handelAppBox(item: any) {
  activeLabel.value = item.classesName;
  applicationBoxQueryFrom.value.classesId = item.id;
  fatchApplicationList()
}


// 搜索应用列表
function fatchApplicationList() {
  listAppboxapplication(applicationBoxQueryFrom.value).then((res: any) => {
    if (res.code === 200) {
      appBoxList.value = res.data
    }
  })
}

// 得到分类列表
function fatchClassesList() {
  listClasses().then((res: any) => {
    if (res.code === 200) {
      classesList.value = res.data
    }
  })
}

onMounted(() => {
  fatchClassesList()
  fatchApplicationList()
})

</script>


<style scoped lang="scss">
.tilt-in-fwd-tr{-webkit-animation:tilt-in-fwd-tr .6s cubic-bezier(.25,.46,.45,.94) both;animation:tilt-in-fwd-tr .6s cubic-bezier(.25,.46,.45,.94) both}

/* ----------------------------------------------
 * Generated by Animista on 2024-2-18 2:21:32
 * Licensed under FreeBSD License.
 * See http://animista.net/license for more info.
 * w: http://animista.net, t: @cssanimista
 * ---------------------------------------------- */

@-webkit-keyframes tilt-in-fwd-tr{0%{-webkit-transform:rotateY(20deg) rotateX(35deg) translate(300px,-300px) skew(-35deg,10deg);transform:rotateY(20deg) rotateX(35deg) translate(300px,-300px) skew(-35deg,10deg);opacity:0}100%{-webkit-transform:rotateY(0) rotateX(0deg) translate(0,0) skew(0deg,0deg);transform:rotateY(0) rotateX(0deg) translate(0,0) skew(0deg,0deg);opacity:1}}@keyframes tilt-in-fwd-tr{0%{-webkit-transform:rotateY(20deg) rotateX(35deg) translate(300px,-300px) skew(-35deg,10deg);transform:rotateY(20deg) rotateX(35deg) translate(300px,-300px) skew(-35deg,10deg);opacity:0}100%{-webkit-transform:rotateY(0) rotateX(0deg) translate(0,0) skew(0deg,0deg);transform:rotateY(0) rotateX(0deg) translate(0,0) skew(0deg,0deg);opacity:1}}
</style>


