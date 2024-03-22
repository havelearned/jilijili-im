<template>
  <div>
    <div class="row">
      <div class="column col-4">
        <div class="row">
          <q-input class="col-6" dense filled square clearable v-model="tempToken" placeholder="请输入token"></q-input>
          <q-btn @click="setToken(tempToken)">设置token</q-btn>
        </div>
        <q-btn @click="init">连接WeSocket</q-btn>
        <div class="row">
          <q-input class="col-6" dense filled square clearable v-model="message" placeholder="请输入消息"></q-input>
          <q-btn class="col-6" @click="sendMessage">发送测试消息</q-btn>
        </div>
        <q-btn @click="">断开</q-btn>
      </div>
      <div class="col-4">
        <q-scroll-area style="height: 800px;" class="q-pa-md bg-grey">
          <div v-for="(msg,index) in msgRecords">
            <q-chat-message
              :name="msg.senderId+''"
              :text="[msg.content]"
              :sent="msg.senderId == MyId"
            />
          </div>
        </q-scroll-area>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import {initWebSocket} from "@/utils/websocket";
import {getToken, setToken} from "@/utils/auth";
import {useLoading} from "@/components/loading/loading.data";


const {hideLoading, showLoading} = useLoading({
  message: "加载中.."
})

let protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://'
const message = ref('');
const MyId = 1;


onMounted(() => {


})


function init() {
  initWebSocket(`ws://localhost:8080/im-websocket?token=${getToken()}`);
}


</script>
