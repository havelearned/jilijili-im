<template>
  <div v-show="loading">
    <q-item v-for="(index) in count ">
      <q-item-section avatar>
        <q-skeleton type="QAvatar"/>
      </q-item-section>

      <q-item-section>
        <q-item-label>
          <q-skeleton type="text"/>
        </q-item-label>
        <q-item-label caption>
          <q-skeleton type="text" width="65%"/>
        </q-item-label>
      </q-item-section>
    </q-item>
  </div>
</template>

<script setup lang="ts" >
import {useSkeleton} from './skeleton.data'

const {loading,hideLoading,count} = useSkeleton()

defineExpose({
  hideLoading() {
    hideLoading()
  },
  setCount(value: number) {
    count.value = value
  }
})

</script>
