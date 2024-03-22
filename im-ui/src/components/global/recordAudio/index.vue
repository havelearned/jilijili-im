<template>
    <div>
        <q-btn round flat icon="graphic_eq" class="q-mr-sm" @click="fixed = !fixed"></q-btn>
        <q-dialog v-model="fixed">
            <q-card>
                <q-card-section>
                    <div class="text-h6">按住说话,松开发送</div>
                </q-card-section>
                <q-separator/>
                <q-card-section style="max-height: 50vh" class="flex justify-center" @mousedown="down" @mouseup="up">
                    <ul class="wave-menu" v-if="show">
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                    </ul>
                    <q-btn v-if="start" class="start-btn">
                        <q-icon name="graphic_eq"></q-icon>
                        <svg viewBox="0 0 16 16" class="bi bi-whatsapp" fill="currentColor" height="16" width="16"
                             xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M0 1.146C0 .513.526 0 1.175 0h13.65C15.474 0 16 .513 16 1.146v13.708c0 .633-.526 1.146-1.175 1.146H1.175C.526 16 0 15.487 0 14.854V1.146zm4.943 12.248V6.169H2.542v7.225h2.401zm-1.2-8.212c.837 0 1.358-.554 1.358-1.248-.015-.709-.52-1.248-1.342-1.248-.822 0-1.359.54-1.359 1.248 0 .694.521 1.248 1.327 1.248h.016zm4.908 8.212V9.359c0-.216.016-.432.08-.586.173-.431.568-.878 1.232-.878.869 0 1.216.662 1.216 1.634v3.865h2.401V9.25c0-2.22-1.184-3.252-2.764-3.252-1.274 0-1.845.7-2.165 1.193v.025h-.016a5.54 5.54 0 0 1 .016-.025V6.169h-2.4c.03.678 0 7.225 0 7.225h2.4z"></path>
                        </svg>
                    </q-btn>
                </q-card-section>
                <q-separator/>
                <q-btn @click="playAudio"
                >播放
                </q-btn>
            </q-card>
        </q-dialog>
    </div>

</template>
<script lang="ts" setup>
import {uploadAudio} from "@/api/upload";

const {proxy} = getCurrentInstance()

const emit = defineEmits(["update:modelValue", 'handleSubmit'])

const fixed = ref(false)
const show = ref(false)
const start = ref(true)


let audioChunks: BlobPart[] | Blob[] = [];
let bolo: Blob = undefined

var mediaRecorder = ref<MediaRecorder>(null)


function down() {
    startRecord()
    console.log(1)
    show.value = true
    start.value = false


}

function up() {
    console.log(2)
    show.value = false
    start.value = true
    // fixed.value = false
    endRecord()
}

// 开始录制
function startRecord() {
    if (!mediaRecorder.value) {
        console.log("出现错误!!!")
        return
    }
    mediaRecorder.value.start()

}

// 结束录制
function endRecord() {
    if (!mediaRecorder.value) {
        console.log("出现错误!!!");
        return
    }
    mediaRecorder.value.stop();

}

// 音频文件上传
async function uploadServer(blob: Blob) {
    try {
        const res = await uploadAudio(blob);
        if (res.code === 200) {
            console.log(res.data.url);
            // 更新url
            emit("update:modelValue", res.data.url);

            // 执行父组件提交
            await nextTick();
            emit('handleSubmit', true);
        }
    } catch (error) {
        console.error('Error uploading audio:', error);
        // 处理错误
    }
}

// 播放音频
function playAudio() {
    proxy.$recordAudio.play()
}

onMounted(() => {
    nextTick(()=>{
        init()
    })

});

// 初始化
function init() {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        console.log("getUserMedia supported.");
        navigator.mediaDevices
            .getUserMedia({audio: true,},).then(function (stream) {
            console.log(stream.getAudioTracks())
            mediaRecorder.value = new MediaRecorder(stream);
            mediaRecorder.value.ondataavailable = function (e) {
                if (e.data.size > 0) {
                    audioChunks.push(e.data);
                    console.log(e.data)

                }

            };
            mediaRecorder.value.onstop = function (e) {
                console.log("stop")
                bolo = new Blob(audioChunks, {type: "audio/ogg; codecs=opus"});
                if (calculateAudioDuration(audioChunks) <= 12) {
                    alert("录音时间太短")
                    fixed.value = false
                } else if (calculateAudioDuration(audioChunks) >= 1124) {
                    alert("录音时间太长")
                    fixed.value = false
                    uploadServer(bolo)
                } else {
                    uploadServer(bolo)
                    fixed.value = false
                }
                audioChunks = [];
                var audioURL = window.URL.createObjectURL(bolo);
                proxy.$recordAudio.src = audioURL

            }
        })
            .catch(function (err) {
                console.log("The following getUserMedia error occured: " + err);
            });
    } else {
        console.log("没有查询到输入设备");
    }
}

/**
 * 计算音频时长
 * @param chunks
 */
function calculateAudioDuration(chunks: any) {
    let totalSize = 0;
    for (const chunk of chunks) {
        totalSize += chunk.size; // 假设每个chunk都是Blob对象，并且size属性表示其字节大小
    }
    console.log(totalSize)
    // 假设每个采样点的字节大小为2（16位音频），声道数为1（单声道），采样率为44100Hz
    // 这些值应根据实际音频流的设置进行调整
    const bytesPerMillisecond = 2 * 1 * 44100; // 2 bytes/sample * 1 channel * 44100 samples/second
    const durationInMilliseconds = totalSize / bytesPerMillisecond;
    console.log(durationInMilliseconds * 100);
    return durationInMilliseconds * 100;
}
</script>


<style scoped lang="scss">
.wave-menu {
    border: 4px solid #545FE5;
    border-radius: 50px;
    width: 200px;
    height: 45px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0;
    margin: 0;
    cursor: pointer;
    transition: ease 0.2s;
    position: relative;
    background: #fff;
}

.wave-menu li {
    list-style: none;
    height: 30px;
    width: 4px;
    border-radius: 10px;
    background: #545FE5;
    margin: 0 6px;
    padding: 0;
    animation-name: wave1;
    animation-duration: 0.3s;
    animation-iteration-count: infinite;
    animation-direction: alternate;
    transition: ease 0.2s;
}

.wave-menu:hover > li {
    background: #fff;
}

.wave-menu:hover {
    background: #545FE5;
}

.wave-menu li:nth-child(2) {
    animation-name: wave2;
    animation-delay: 0.2s;
}

.wave-menu li:nth-child(3) {
    animation-name: wave3;
    animation-delay: 0.23s;
    animation-duration: 0.4s;
}

.wave-menu li:nth-child(4) {
    animation-name: wave4;
    animation-delay: 0.1s;
    animation-duration: 0.3s;
}

.wave-menu li:nth-child(5) {
    animation-delay: 0.5s;
}

.wave-menu li:nth-child(6) {
    animation-name: wave2;
    animation-duration: 0.5s;
}

.wave-menu li:nth-child(8) {
    animation-name: wave4;
    animation-delay: 0.4s;
    animation-duration: 0.25s;
}

.wave-menu li:nth-child(9) {
    animation-name: wave3;
    animation-delay: 0.15s;
}

@keyframes wave1 {
    from {
        transform: scaleY(1);
    }

    to {
        transform: scaleY(0.5);
    }
}

@keyframes wave2 {
    from {
        transform: scaleY(0.3);
    }

    to {
        transform: scaleY(0.6);
    }
}

@keyframes wave3 {
    from {
        transform: scaleY(0.6);
    }

    to {
        transform: scaleY(0.8);
    }
}

@keyframes wave4 {
    from {
        transform: scaleY(0.2);
    }

    to {
        transform: scaleY(0.5);
    }
}

/* From uiverse.io by @alshahwan */
/* From uiverse.io by @alshahwan */
.start-btn {
    background-color: #fff;
    border: 1px solid #0077b5;
    padding: 5px;
    position: relative;
    width: 7.2em;
    height: 2em;
    transition: 0.5s;
    font-size: 17px;
    border-radius: 0.4em;
}

.start-btn p {
    position: absolute;
    top: 0.4em;
    left: 1.2em;
    margin: 0;
    padding: 0;
    transition: .5s;
    color: #0077b5;
}

.start-btn svg {
    position: absolute;
    top: 0.45em;
    right: 0.5em;
    margin: 0;
    padding: 0;
    opacity: 0;
    transition: 0.5s;
    height: 1em;
    fill: #fff
}

.start-btn:hover p {
    left: 0.6em;
    color: #fff
}

.start-btn:hover svg {
    opacity: 1;
}

.start-btn:hover {
    background-color: #0077b5;
}


</style>
