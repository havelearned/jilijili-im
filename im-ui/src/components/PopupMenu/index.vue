<!--
  @FileDescription:  弹出菜单,
  @Date: 2024/3/17 10:48 ,
  @version: 0.0.1,
  @Author: 罗家炬,
 -->
<template>
        <q-popup-proxy :context-menu="contextMenu"  class="global-dark">
            <slot name="cardPale" :data="prop.data"></slot>
            <q-list class=""  dense style="min-width: 100px">
                <q-item clickable v-close-popup>
                    <q-item-section>删除消息</q-item-section>
                </q-item>
                <q-separator />
                <q-item clickable>
                    <q-item-section>添加反应</q-item-section>
                    <q-item-section side>
                        <q-icon name="keyboard_arrow_right" />
                    </q-item-section>
                    <q-menu anchor="top end" self="top start">
                        <q-list>
                            <!-- TODO 列出反应表情包 -->
                        </q-list>
                    </q-menu>

                </q-item>
                <q-separator />
                <q-item clickable v-close-popup>
                    <q-item-section @click="copyBef">复制文本|消息连接</q-item-section>
                </q-item>
            </q-list>
        </q-popup-proxy>

</template>
<script lang="ts" setup>
import {copyToClipboard} from "@/utils/verify";
import {MsgRecordVO} from "@/api/im/msgRecord/types";
import {onMounted} from "vue";

const prop = defineProps({
    data: {
        type: Object as () => MsgRecordVO,
        required: false
    },
    contextMenu:{
        type: Boolean,
        required: false
    }
});

function copyBef() {
    if(prop.data?.content.text){
        copyToClipboard(prop.data?.content.text)
    }else if(prop.data?.content.audio){
        copyToClipboard(prop.data?.content.audio)
    }else if(prop.data?.content.files){
        let files = prop.data?.content.files
        files = files.map((item: { url: any; })=>item.url+"\n")

        copyToClipboard(files)
    }else{
        copyToClipboard('')
    }
}


</script>


<style scoped>

</style>
