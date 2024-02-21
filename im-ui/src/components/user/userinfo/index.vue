<template>
<div></div>

</template>
<script lang="ts" setup>

import {SysUserVo} from "@/api/login/login.data";

const props = defineProps({
  userinfo: {type: Object, default: []},
  show: {type: Boolean, default: false},
  title: {type: String, default: '用户列表'}
})


</script>


<style scoped>

</style>
