<template>
  <div >
    <q-badge floating :color="color"  rounded >
      <q-tooltip v-if="title">
        {{title}}
      </q-tooltip>
    </q-badge>
  </div>
</template>
<script lang="ts" setup>
const props = defineProps({
  color: {
    type: String,
    default: 'green'
  },
  title:{
    type: String,
    default: ''
  }
})

</script>
<style scoped>
</style>
