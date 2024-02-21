<template>
  <q-list v-if="loading">
    <q-item v-for="(index) in count">
      <q-item-section avatar>
        <q-skeleton type="QAvatar"/>
      </q-item-section>
      <q-item-section>
        <q-item-label>
          <q-skeleton type="text"/>
        </q-item-label>
        <q-item-label caption>
          <q-skeleton type="text"/>
        </q-item-label>
      </q-item-section>
    </q-item>
  </q-list>

</template>
<script lang="ts" setup>
import {useSkeleton} from '@/components/skeleton/skeleton.data'
import {defineExpose} from "vue"

const {loading, hideLoading, count,showLoading} = useSkeleton()


defineExpose({
  hideLoading() {
    hideLoading()
  },
  showLoading() {
    showLoading()
  },
  setCount(value: number) {
    count.value = value
  }
})



</script>


<style scoped>

</style>
