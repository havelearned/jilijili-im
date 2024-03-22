<template>
    <router-view/>
    <audio class="hide" id="audioApp"></audio>
    <audio class="hide" id="audioRemoteApp"></audio>
    <video class="hide" id="videoApp"></video>
    <video class="hide" id="videoRemoteApp"></video>
    <div class="" id="global-app"></div>
</template>
<script setup lang="ts">
import "tailwindcss/tailwind.css"

onMounted(() => {

    const drive = inject("drive")
    drive.getBrowserInfo()
    drive.getWebInfo()

})
</script>

<style lang="scss">
@import "@/css/global.scss";

.hide {
    display: none;;
}
</style>
