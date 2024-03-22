<template>
    <q-uploader
        :url="url"
        label="Custom header"
        @uploaded="uploaded"
        :headers="headers"
        field-name="file"
        @removed="removed"
        multiple
        v-model="resultData"
        class="global-dark "
        :auto-upload="true">
        <template v-slot:header="scope">
            <div class="row no-wrap items-center q-pa-sm q-gutter-xs global-dark">
                <q-btn v-if="scope.queuedFiles.length > 0" icon="clear_all" @click="scope.removeQueuedFiles" round dense
                       flat>
                    <q-tooltip>清除所有</q-tooltip>
                </q-btn>
                <q-btn v-if="scope.uploadedFiles.length > 0" icon="done_all" @click="scope.removeUploadedFiles" round
                       dense flat>
                    <q-tooltip>Remove Uploaded Files</q-tooltip>
                </q-btn>
                <q-spinner v-if="scope.isUploading" class="q-uploader__spinner"/>
                <q-btn v-if="scope.canAddFiles" type="a" icon="add_box" @click="scope.pickFiles" round dense flat>
                    <q-uploader-add-trigger/>
                    <q-tooltip>选中文件</q-tooltip>
                </q-btn>
                <div class="col ">
                    <div class="q-uploader__title">文件列表</div>
                    <div class="q-uploader__subtitle">{{ scope.uploadSizeLabel }} / {{
                            scope.uploadProgressLabel
                        }}
                    </div>

                </div>


                <q-btn v-if="scope.isUploading" icon="clear" @click="scope.abort" round dense flat>
                    <q-tooltip>Abort Upload</q-tooltip>
                </q-btn>
            </div>
        </template>
        <template v-slot:list="scope">
            <div class="row q-gutter-md global-dark justify-start no-wrap">
                <q-card dark class="my-card col-2 " v-for="file in scope.files" :key="file.__key">
                    <q-img
                        :src="file.__img.src"
                        spinner-color="white"
                        style="height: 170px; max-width: 300px"
                        img-class="my-custom-image"
                        class="rounded-borders  ">
                        <div class="absolute-bottom text-subtitle1 text-center " style="font-size: 10px">
                            {{ file.name }}
                        </div>
                    </q-img>
                    <q-btn class="absolute-top-right" icon="clear" color="red" flat
                           @click="scope.removeFile(file)"></q-btn>
                </q-card>
            </div>
        </template>
    </q-uploader>
</template>
<script setup lang="ts">

import {getToken} from "@/utils/auth";

const emit = defineEmits(["update:modelValue"])


const url = 'http://localhost:8080/resource/oss/upload'

const headers = [
    {name: "Authorization", value: 'Bearer ' + getToken()},
    {name: "clientid", value: import.meta.env.VITE_APP_CLIENT_ID}
]

const resultData = ref()
const urlList = ref<any[]>([])

function uploaded(info: any) {

    console.log(info)
    if (info.xhr) {
        let res = JSON.parse(info.xhr.response)
        if (res.code == 200) {
            let data = res.data
            urlList.value.push({name: data.fileName, url: data.url})
            emit("update:modelValue", urlList.value)
        }
    }
}

function removed(files: any) {
    // 名字一样的删除
    urlList.value = urlList.value.filter(item => {
        return files[0].name !== item.name
    })
    emit("update:modelValue", urlList.value)
}



</script>
