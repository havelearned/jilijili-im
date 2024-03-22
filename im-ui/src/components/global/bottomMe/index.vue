
<template>
    <q-list class="absolute-bottom bg-brown">
        <q-item class="q-pa-xs">
            <q-item-section side>
                <q-avatar rounded size="lg" no-ripple>
                    <img :src="userStore.currUser?.avatar"/>
                </q-avatar>
            </q-item-section>
            <q-item-section>
                <q-item-label>
                    {{ userStore.currUser?.nickName }}
                </q-item-label>
                <q-item-label caption>
                    {{ userStore.currUser?.userName }}
                </q-item-label>
            </q-item-section>
            <q-item-section side>
                <q-bar  class="rounded-borders">
                    <q-btn icon="keyboard_voice" dense flat color="white" v-if="audioStatus" @click="">
                        <q-tooltip>关闭语音</q-tooltip>
                    </q-btn>
                    <q-btn icon="mic_off" dense flat color="red" v-else @click="">
                        <q-tooltip>开启语音</q-tooltip>
                    </q-btn>

                    <q-btn icon="headset" dense flat color="white" v-if="musicStatus" @click="">
                        <q-tooltip>关闭声音</q-tooltip>
                    </q-btn>
                    <q-btn icon="headset_off" color="red" dense flat v-else @click="">
                        <q-tooltip>开启声音</q-tooltip>
                    </q-btn>
                    <q-btn flat dense icon="settings">
                        <q-tooltip>个人资料</q-tooltip>
                    </q-btn>

                </q-bar>

            </q-item-section>
        </q-item>

    </q-list>

</template>
<script lang="ts" setup>
import {useAppBoxs} from "@/stores/modules/appBox";
import useUserStore from "@/stores/modules/user";


const userStore = useUserStore()
const audioStatus = ref(false)
const musicStatus = ref(true)
</script>


<style scoped>

</style>
