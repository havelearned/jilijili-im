<template>
  <div class="q-pa-md row justify-start">
    <div style="width: 100%" >
      <q-scroll-area
        :style="{ height: `calc(${$q.screen.height}px - 120px)` }"
        :thumb-style="thumbStyle"
        :bar-style="barStyle"
        ref="scrollAreaRef">
        <div id="messageBoxId">
          <div v-for="itemRecord in computedMessageRecord" ref="chatMessageBoxRef">
            <q-chat-message class="q-pa-md"
              :name="itemRecord.name"
              :avatar="itemRecord.avatar"
              :stamp="itemRecord.stamp"
              :sent="itemRecord.sent"
              :text-color="itemRecord.textColor"
              :bg-color="itemRecord.bgColor"
            >
              <div>message lenth {{ computedMessageRecord.length }}</div>

              <div>
                {{ itemRecord.data }}
                <img src="https://cdn.quasar.dev/img/discord-omq.png" class="my-emoticon"/>
              </div>
            </q-chat-message>

            <q-chat-message style="border-left: 4px solid #db36a4"  class="q-pa-md"
              name="Jane"
              avatar="https://cdn.quasar.dev/img/avatar5.jpg"
              bg-color="amber"
            >
              <q-spinner-dots size="1rem"/>
            </q-chat-message>
          </div>
        </div>
      </q-scroll-area>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {computed, defineExpose, defineProps, nextTick, onMounted, ref, watch} from "vue";
import {useQuasar} from "quasar";
import {chatMessage} from "@/components/message/message.data";
import {useScrollArea} from "@/components/scrollarea/index"
import html2canvas from 'html2canvas'
import {jsPDF} from "jspdf";

const props = defineProps<{
  message: {
    type: {};
    default: {};
  };
}>();

const {thumbStyle,barStyle} =useScrollArea();
const messageRecord = ref<Array<chatMessage>>([]);

const scrollAreaRef = ref(null);
const chatMessageBoxRef = ref(null);
const $q = useQuasar();

// 计算高度
const chatMessageBoxHeights = computed(() => {
  if (chatMessageBoxRef.value) {
    return chatMessageBoxRef.value.reduce(
      (sum, element) => sum + element.clientHeight,
      0
    );
  }
});


const chatMessageBoxElements = computed(() => {
  if (chatMessageBoxRef.value) {
    return chatMessageBoxRef.value.map((element) => element);
  }
});

const computedMessageRecord = computed(() => {
  return messageRecord.value;
});

watch(() => props.message, (newMessage) => {

    //加入聊天记录
    messageRecord.value.push(newMessage);

    // 重新定位最底部
    nextTick(() => {
      scrollAreaRef.value.setScrollPosition(
        "vertical",
        chatMessageBoxHeights.value + 100,
        300
      );
    });
  }
);

// 导出图片
function exportPicture(link, name = "未命名文件") {
  const file = document.createElement("a");
  file.style.display = "none";
  file.href = link;
  file.download = decodeURI(name);
  document.body.appendChild(file);
  file.click();
  document.body.removeChild(file);
}


// 导出文件
async function exportFile(fileType: string) {
  if (fileType === 'png') {
    let messageBox = document.querySelector("#messageBoxId");
    await html2canvas(messageBox, {}).then(canvas => {
      exportPicture(canvas.toDataURL(), '截图.png');
    });
  } else if (fileType === 'pdf') {
    let messageBox = document.querySelector("#messageBoxId");
    await html2canvas(messageBox, {}).then(canvas => {
      const contentWidth = canvas.width;
      const contentHeight = canvas.height;
      const pageHeight = 800

      const totalPages = Math.ceil(contentHeight / pageHeight);

      const pdf = new jsPDF('portrait', 'px', [800, 800]);
      for (let page = 0; page < totalPages; page++) {
        const startY = page * pageHeight;
        const portionCanvas = document.createElement('canvas');
        portionCanvas.width = contentWidth;
        portionCanvas.height = Math.min(pageHeight, contentHeight - startY);
        const portionContext = portionCanvas.getContext('2d');
        portionContext?.drawImage(canvas, 0, startY, contentWidth, portionCanvas.height, 0, 0, contentWidth, portionCanvas.height);
        console.log("pdf height=>", portionCanvas.height)
        if (page > 0) {
          pdf.addPage(); // Add a new page for each portion after the first one
        }
        pdf.addImage(portionCanvas.toDataURL('image/png'), 'PNG', 0, 0, contentWidth, portionCanvas.height);
      }
      // pdf.addImage(canvas.toDataURL('image/png'), 'PNG', 0, 0, contentWidth, contentHeight);
      pdf.save('截图.pdf');
      const pdfurl = pdf.output()
      exportPicture(pdfurl, '截图.pdf');
    })
  }
}

defineExpose({exportFile})
onMounted(() => {
});
</script>

<style lang="sass">
.my-emoticon
  vertical-align: middle
  height: 2em
  width: 2em
</style>
