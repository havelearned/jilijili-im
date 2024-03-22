<template>
<!--  <img :src="src" alt="Midjourney" class="rounded-t-lg hover:scale-110" :width="100"-->
<!--       :height="height" style="aspect-ratio: 300 / 150; object-fit: cover;">-->

  <q-img :src="src" spinner-color="white"
         :ratio="16/9" fit="cover" class="rounded-borders hover:scale-110 transition-transform"
         :width="width" :height="height"></q-img>
</template>
<script lang="ts" setup>
const props = defineProps({
  src: {
    type: String,
    default: ""
  },
  width: {
    type: [Number, String],
    default: "300"
  },
  height: {
    type: [Number, String],
    default: "150"
  }
});
</script>


<style scoped>

</style>
