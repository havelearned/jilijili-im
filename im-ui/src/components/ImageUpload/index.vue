<template>
  <div>
    <q-avatar rounded  size="100px" font-size="50px"
              color="teal" text-color="white" @click="dialog=true">
      <img :src="avatar">
      <q-tooltip>点击更换</q-tooltip>
    </q-avatar>
    <q-dialog v-model="dialog" class="w-2/3 h-1/5" persistent>
      <q-card>
        <q-file ref="fileRef" dense color="purple-12"
                v-model="model" style="display: none">
        </q-file>
        <q-card-section class="flex justify-center items-center full-width">
          <q-img v-if="!localAvatar" width="200px" height="200px" :src="avatar" fit="fill">
            <template #error>
              <q-img src="@/assets/def_0.png" fit="cover"></q-img>
            </template>
          </q-img>

          <q-img v-else width="200px" height="200px" :src="localAvatar" fit="fill">
            <template #error>
              <q-img src="@/assets/def_0.png" fit="cover"></q-img>
            </template>
          </q-img>
        </q-card-section>
        <q-separator/>
        <q-card-actions align="center">
          <q-btn flat label="选择文件" @click="chooseFile" color="primary"/>
          <q-btn flat label="取消" color="primary" v-close-popup/>
          <q-btn flat label="确定" @click="uploading" color="primary"/>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script lang="ts" setup>
import {setUserAvatar} from "@/api/user";

const props = defineProps({
  avatar: String,
  modelValue: String,
})
const emit = defineEmits(["update:modelValue"])
const $q = useQuasar();

let localAvatar = ref(null);
const model = ref<File>();
const fileRef = ref<any>();
const dialog = ref(false);

// 使用 watch 监听 props 的变化，更新 localAvatar
watch(() => model.value,  (newVal) => {

  model.value = newVal
  fileToDataURL(newVal).then((url) => {
    localAvatar.value = url;
  })
});


const chooseFile = () => {
  fileRef.value.$el.click();
}

const fileInfo = async () => {
  localAvatar.value = null;
}

const fileToDataURL = (file: File): Promise<string> => {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();

    reader.onload = (event: ProgressEvent<FileReader>) => {
      if (event.target && event.target.result) {
        resolve(event.target.result.toString());
      } else {
        reject(new Error('Failed to read the file as Data URL.'));
      }
    };
    reader.onerror = () => {
      reject(new Error('Failed to read the file.'));
    };
    reader.readAsDataURL(file);
  });
};


const uploading = async () => {
  const formData = new FormData();
  formData.append('avatarfile', model.value);
  let res = await setUserAvatar(formData)
  if (res.code === 200) {
    emit("update:modelValue", res.data.imgUrl)
    dialog.value = false
  } else {
    $q.notify({
      position: 'top',
      message: res.msg,
      type: "error"
    })
  }
}

</script>

<style>

</style>
