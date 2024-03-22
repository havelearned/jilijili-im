import {boot} from 'quasar/wrappers'

import VueCropper from 'vue-cropper';
import 'vue-cropper/dist/index.css'
import axios from "axios";
import driveUtils from "@/utils/driveUtils";


let RecordAudio = document.createElement("audio")
document.getElementById('global-app')?.appendChild(RecordAudio)

export default boot(({app}) => {
    app.use(VueCropper)
    app.config.globalProperties.$axios = axios;
    app.config.globalProperties.$recordAudio = document.createElement("audio") as HTMLAudioElement; // 语音录制
    app.config.globalProperties.$audioApp = document.getElementById("audioApp") as HTMLAudioElement; // 本地语音
    app.config.globalProperties.$audioRemoteApp = document.getElementById("audioRemoteApp") as HTMLAudioElement; // 远程语音

    app.config.globalProperties.$videoApp = document.getElementById("videoApp") as HTMLVideoElement; // 本地视频
    app.config.globalProperties.$videoRemoteApp = document.getElementById("videoRemoteApp") as HTMLVideoElement; // 远程视频

    app.provide("drive", driveUtils)
})
