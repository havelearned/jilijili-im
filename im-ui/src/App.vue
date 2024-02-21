<template>
  <router-view/>
  <audio class="hide" id="audioApp"></audio>
  <audio class="hide" id="audioRemoteApp"></audio>
  <video class="hide" id="videoApp"></video>
  <video class="hide" id="videoRemoteApp"></video>
</template>
<script setup lang="ts">
import "tailwindcss/tailwind.css"
import {onMounted} from "vue";
import {LocalStorage} from "quasar";
import {SysUserVo} from "@/api/login/login.data";

let audioApp: HTMLAudioElement;
let audioRemoteApp: HTMLAudioElement;
let videoApp: HTMLAudioElement;
let videoRemoteApp: HTMLAudioElement;

function initAudio() {
  audioApp = document.getElementById("audioApp") as HTMLAudioElement;
  audioRemoteApp = document.getElementById("audioRemoteApp") as HTMLAudioElement;
  videoApp = document.getElementById("videoApp") as HTMLVideoElement;
  videoRemoteApp = document.getElementById("videoRemoteApp") as HTMLVideoElement;
}

function loadWebSocket() {
  const userVO: SysUserVo = LocalStorage.getItem("userInfo");
  // initWebSocket(`ws://localhost:8080/im/ws/${userVO.user.userId}/${getToken()}`);
}

onMounted(() => {
  initAudio()
})
</script>

<style lang="scss">
@import "@/css/global.scss";

.hide {
  display: none;;
}
</style>
