<template>
    <div>
        <q-dialog
            v-model="dialog"
            persistent
            :maximized="maximizedToggle"
            transition-show="slide-up"
            transition-hide="slide-down"
        >
            <q-card class="global-dark">
                <q-bar>
                    <q-space/>
                    <q-btn flat icon="close" v-close-popup>
                        <q-tooltip class="">Close</q-tooltip>
                    </q-btn>
                </q-bar>
                <q-separator/>
                <div class="full-width flex flex-center">
                    <q-splitter class="w-6/12"
                                v-model="splitterModel">
                        <template v-slot:before>
                            <q-tabs v-model="tab" vertical class="text-teal">
                                <q-tab name="mails" icon="mail" label="我的账号"/>
                                <q-tab name="alarms" icon="alarm" label="个人资料"/>
                                <q-tab name="movies" icon="movie" label="登录退出"/>
                            </q-tabs>
                        </template>
                        <template v-slot:after>
                            <q-scroll-area
                                :style="{ height: `calc(${$q.screen.height}px - 50px)` }">
                                <q-tab-panels v-model="tab" animated swipeable vertical transition-prev="jump-up"
                                              transition-next="jump-up"
                                              class="global-dark">

                                    <q-tab-panel name="mails">
                                        <div class="bg-[#1a1a1a] min-h-screen text-white">
                                            <header class="flex justify-between items-center p-4 bg-[#202225]">
                                                <q-toolbar>
                                                    <q-toolbar-title>我的账号</q-toolbar-title>
                                                    <q-space/>
                                                </q-toolbar>
                                            </header>
                                            <div class="max-w-2xl mx-auto py-10">
                                                <q-form @submit="handelUpdateSubmit">
                                                    <section class="bg-[#262626] p-6 rounded-lg">
                                                        <div class="flex items-center justify-between">
                                                            <div class="flex items-center">
                                                                <ImageUpload class="q-ma-md" :avatar="user.avatar" v-model="user.avatar"/>
                                                            </div>
                                                            <q-btn v-if="editStatus" @click="changeEditStatus"
                                                                   class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 bg-[#007fff] text-white">
                                                                编辑个人资料
                                                            </q-btn>
                                                            <q-btn v-else type="submit"
                                                                   class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 bg-[#007fff] text-white">
                                                                完成
                                                            </q-btn>
                                                        </div>
                                                        <div class="mt-6 space-y-4" v-if="editStatus">
                                                            <div class="flex justify-between"><span>昵称</span><span>
                            {{ user.nickName }}
                          </span></div>
                                                            <div class="flex justify-between"><span>用户名</span><span>
                            {{ user.userName }}
                          </span></div>
                                                            <div class="flex justify-between"><span>电子邮箱</span>
                                                                <span>
                            {{ user.email }}
                            </span>
                                                            </div>
                                                            <div class="flex justify-between">
                                                                <span>手机号码</span>
                                                                <span>
                              {{ user.phonenumber }}
                            </span>
                                                            </div>
                                                        </div>

                                                        <div class="mt-6 space-y-4" v-else>

                                                            <div class="flex justify-between"><span>昵称</span><span>
                              <q-input :disable="editStatus" filled disable dense dark v-model="user.nickName"
                                       :dense="dense"
                                       :rules="[val=> isNotEmpty(val),val=>validNickname(val),]"/>

                            </span></div>
                                                            <div class="flex justify-between"><span>用户名</span><span>
                              <q-input :disable="editStatus" filled disable dense dark
                                       v-model="user.userName"></q-input>
                            </span></div>
                                                            <div class="flex justify-between"><span>电子邮箱</span>
                                                                <span>
                                  <q-input :disable="editStatus" filled disable dense dark
                                           v-model="user.email"
                                           :rules="[val=> isNotEmpty(val),val=>isValidEmail(val),]">
                                  </q-input>
                              </span>
                                                            </div>
                                                            <div class="flex justify-between">
                                                                <span>手机号码</span>
                                                                <span>
                              <q-input :disable="editStatus" filled disable dense dark
                                       v-model="user.phonenumber"
                                       :rules="[val=> isNotEmpty(val),val=>validPhone(val),]">
                              </q-input>
                            </span>
                                                            </div>

                                                        </div>
                                                    </section>
                                                </q-form>
                                                <section class="bg-[#262626] mt-6 p-6 rounded-lg">
                                                    <q-btn
                                                        class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 w-full bg-[#007fff] text-white"
                                                        @click="changePassDialog=true">
                                                        更改密码
                                                    </q-btn>
                                                </section>
                                                <section class="bg-[#262626] mt-6 p-6 rounded-lg">
                                                    <div class="mt-6"><p>注销账号,请谨慎操作</p>
                                                        <div class="flex justify-between mt-4">
                                                            <q-btn
                                                                class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 bg-red-600 text-white">
                                                                关闭账号
                                                            </q-btn>
                                                        </div>
                                                    </div>
                                                </section>
                                            </div>
                                        </div>

                                    </q-tab-panel>
                                    <q-tab-panel name="alarms">
                                        <!--
                                        // v0 by Vercel.
                                        // https://v0.dev/t/j7dqz3yOeZP
                                        -->
                                        <div class="bg-[#1a1a1a] min-h-screen text-white">
                                            <div class="max-w-4xl mx-auto">
                                                <header class="flex justify-between items-center p-4 bg-[#202225]">
                                                    <q-toolbar>
                                                        <q-toolbar-title>个人资料</q-toolbar-title>
                                                        <q-space/>
                                                    </q-toolbar>
                                                </header>
                                                <div class="p-4 space-y-6">
                                                    <div class="bg-[#2f3136] p-4 rounded-md">
                                                        <div class="flex items-center space-x-4"><span
                                                            class="relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full"><span
                                                            class="flex h-full w-full items-center justify-center rounded-full bg-muted">Am</span></span>
                                                            <div><h2 class="text-lg font-semibold">Amani</h2>
                                                                <p class="text-sm text-[#b9bbbe]">amini_73838</p></div>
                                                        </div>
                                                        <div class="mt-4">
                                                            <button
                                                                class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 text-[#00aff4] border-[#00aff4]">
                                                                设置头像
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="bg-[#2f3136] p-4 rounded-md">
                                                        <div class="flex flex-col space-y-4">
                                                            <div><label for="username" class="block mb-2 text-sm font-medium">用户名</label><input
                                                                class="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                                                id="username" placeholder="Amani"></div>
                                                            <div><label for="bio" class="block mb-2 text-sm font-medium">个人介绍</label><textarea
                                                                class="flex min-h-[80px] w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                                                                id="bio" placeholder="这个用户很懒，什么也没留下。"></textarea></div>
                                                        </div>
                                                    </div>
                                                    <div class="bg-[#2f3136] p-4 rounded-md"><h2 class="text-lg font-semibold mb-4">开启Nitro
                                                        功能!</h2>
                                                        <div class="flex items-center space-x-4"><span
                                                            class="relative flex h-10 w-10 shrink-0 overflow-hidden rounded-full"><span
                                                            class="flex h-full w-full items-center justify-center rounded-full bg-muted">Am</span></span>
                                                            <div><h3 class="text-lg font-semibold">Amani</h3>
                                                                <p class="text-sm text-[#b9bbbe]">amini_73838</p></div>
                                                        </div>
                                                        <div class="mt-4">
                                                            <button
                                                                class="inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 bg-[#5865f2] text-white">
                                                                开启Nitro
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </q-tab-panel>

                                    <q-tab-panel name="movies">

                                    </q-tab-panel>
                                </q-tab-panels>

                            </q-scroll-area>
                        </template>

                    </q-splitter>
                </div>
            </q-card>
            <q-dialog v-model="changePassDialog" persistent position="standard">
                <div class=" inset-0 flex justify-center items-center">
                    <div class="bg-gray-800 p-6 rounded-lg max-w-sm w-full mx-4">
                        <div class="flex justify-between items-start">
                            <h2 class="text-xl font-semibold text-white">更新密码</h2>
                            <q-btn class="text-white text-sm p-2 hover:bg-gray-700 rounded-md"  v-close-popup>
                                <q-icon name="close"></q-icon>
                            </q-btn>
                        </div>
                        <p class="text-gray-400 my-4 text-sm">输入当前密码和新密码。</p>
                        <q-form @submit="resetPass" class="flex flex-col space-y-4">
                            <q-input dense dark  v-model="passwordFrom.oldPassword" class="  w-full border border-input  py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50   text-white  border-none"
                                     label="当前密码" type="password"> </q-input>
                            <q-input dense dark v-model="passwordFrom.newPassword" class="  w-full border border-input  py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50   text-white  border-none"
                                     label="新密码" type="password"></q-input>
                            <q-input dense dark v-model="passwordFrom.newPassword" class="  w-full border border-input  py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50   text-white  border-none"
                                     label="确认新密码" type="password" ></q-input>

                            <div class="flex space-x-2">
                                <q-btn v-close-popup
                                       class="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 flex-1 bg-blue-600 text-white rounded-md">
                                    取消
                                </q-btn>
                                <q-btn type="submit"
                                       class="inline-flex items-center justify-center whitespace-nowrap text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-primary/90 h-10 px-4 py-2 flex-1 bg-blue-600 text-white rounded-md">
                                    完成
                                </q-btn>
                            </div>
                        </q-form>
                    </div>
                </div>
            </q-dialog>
        </q-dialog>
    </div>
</template>

<script setup lang="ts">
import ImageUpload from '@/components/ImageUpload/index.vue'

import {useUserStore} from "@/stores/modules/user"
import {SysUserPasswordForm, SysUserVo} from "@/api/login/login.data";
import {resetPassword, setUserInfo} from "@/api/user";
import {isNotEmpty, isValidEmail, validNickname, validPhone} from "@/utils/verify";
import {Notify} from "quasar";

const userStore = useUserStore()

const dialog = ref(false)
const maximizedToggle = ref(true)
const tab = ref('mails')
const splitterModel = ref(20)
const editStatus = ref(true)
const changePassDialog = ref(false)

const user = ref<SysUserVo>({})
const passwordFrom = ref<SysUserPasswordForm>({oldPassword: "",newPassword: "",})

watch(() => user.value?.avatar, (newVal) => {
  userStore.getUserinfo()
});

function changeEditStatus() {
  editStatus.value = !editStatus.value
}

async function handelUpdateSubmit() {
  let res = await setUserInfo(user.value)
  if (res.code === 200) {
    console.log("执行了这里")
    Notify.create({
      position: "top",
      message: "更改成功,部分数据重新登陆后生效!!!",
      color: "positive"
    })
    user.value = await userStore.getUserinfo()
    changeEditStatus()
  } else {
    console.log(res)
  }
}

async function resetPass() {
  let res = await resetPassword(passwordFrom.value)
  if(res.code === 200){
    Notify.create({
      position: "top",
      message: "密码更新成功,3秒后跳转登录页面!!!",
      color: "positive"
    })
    changePassDialog.value = false
    // 密码更新重新登录
    setTimeout(async () => {
      await userStore.logOut()
    }, 3000)
  } else {
    Notify.create({
      position: "top",
      message: "更改失败,"+res.msg,
      type: "error"
    })
  }
}


onMounted(() => {
  // 获取当前登录的用户信息
  if (userStore.currUser) {
    user.value = userStore.currUser
  }
})


defineExpose(
  {
    show() {
      dialog.value = true
    },
    hide() {
      dialog.value = false
    },
  }
)

</script>
