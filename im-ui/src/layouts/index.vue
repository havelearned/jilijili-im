<template>
  <q-layout view="lhh lpR lFr">
    <q-drawer dark
              v-model="leftDrawerOpen"
              :mini="miniState"
              :breakpoint="700"
              bordered
              :class="'global-dark'+ $q.dark.isActive ? 'bg-grey-9' : 'bg-grey-3'"
    >
      <appbox></appbox>
    </q-drawer>
    <q-page-container>
      <router-view/>
    </q-page-container>
  </q-layout>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import appbox from "@/layouts/appbox/index.vue";

const router = useRouter;

const leftDrawerOpen = ref(true);
const rightDrawerOpen = ref(true);
const miniState = ref(true);

function toggleLeftDrawer() {
  leftDrawerOpen.value = !leftDrawerOpen.value;
}

function toggleRightDrawer() {
  rightDrawerOpen.value = !rightDrawerOpen.value;
}


onMounted(() => {
})
</script>
