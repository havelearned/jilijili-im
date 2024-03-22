<template>
    <q-list>

        <q-item v-for="(user, index) in userList" :key="user.userId">
            <q-item-section avatar>
                <q-avatar>
                    <img v-if="user && user.avatar" :src="user.avatar" alt=""/>
                    <img v-else src="@/assets/def_0.png" alt="">
                    <dotOnline class="absolute-bottom-right" :title="'在线'" v-if="user.online"></dotOnline>
                    <dotOnline :color="'grey'" class="absolute-bottom-right" :title="'离线'" v-else></dotOnline>
                </q-avatar>
            </q-item-section>

            <q-item-section>
                <q-item-label lines="1">
                    <span class="">{{ user.userName }}</span>
                    <q-icon v-if="user.sex === '0'" name="man" color="blue"></q-icon>
                    <q-icon v-else-if="user.sex === '1'" name="woman" color="pink"></q-icon>
                    <q-icon v-else name="?" color="grey"></q-icon>
                </q-item-label>
                <q-item-label class="conversation__summary" caption>
                    <q-icon name="check"/>
                    {{ user ? user.userType : '' }}
                </q-item-label>
            </q-item-section>

            <q-item-section side>
                <q-btn-dropdown dense v-if="user.userId !== currUserId" flat>
                    <q-list class="global-dark">
                        <q-item v-if="handelShow(user.userId)">
                            <q-item-section>
                                <q-btn flat dense class="text-green-100" label="添加好友"
                                       @click="add(user.userId)"></q-btn>
                            </q-item-section>
                        </q-item>

                        <q-item @click="">
                            <q-item-section>
                                <q-btn flat dense class="text-red-400" label="屏蔽此人发言"
                                       @click=""></q-btn>
                            </q-item-section>
                        </q-item>

                        <q-item v-if="currUserId === groupLoaderId ">
                            <q-item-section>
                                <q-btn flat dense class="text-red-400" label="劝退此人"
                                       @click="clearOneUser(user.userId)"></q-btn>
                            </q-item-section>
                        </q-item>
                    </q-list>
                </q-btn-dropdown>
            </q-item-section>

        </q-item>
    </q-list>
</template>
<script lang="ts" setup>
import {SysUserVo} from "@/api/login/login.data";
import dotOnline from "@/components/dot/online/index.vue"
import useUserStore from "@/stores/modules/user";
import {useGroupStore} from "@/stores/modules/group";
import {useFriendStore} from "@/stores/modules/friend";
import {exitGroup} from "@/api/im/chatGroup";
import {confirm} from "@/boot/dialog";

const props = defineProps({
    userList: {type: Array<SysUserVo>, default: [] as Array<SysUserVo>},
    show: {type: Boolean, default: false},
    title: {type: String, default: '用户列表' as string}
})


const {handelShieldFriend, add, currFriendList} = useFriendStore()


// 当前用户id
const currUserId = ref<any>()
// 当前群聊的群主id
const groupLoaderId = ref<any>(null)
const groupId = ref<any>(null)


function handelShow(userId: string | number) {
    let item = currFriendList.find(friend => friend.userId === userId)
    return !item;

}

async function clearOneUser(userId: string | number) {
    groupId.value = useGroupStore().currGroupInfo.id

    confirm("确定吗", "", async () => {
        await exitGroup(groupId.value, userId)
         props.userList.forEach(user=>{
             if(user.userId === userId){
                 props.userList.splice(props.userList.findIndex(user => user.userId === userId), 1)
             }
         })
    })


}


onMounted(() => {
    currUserId.value = useUserStore().currUser?.userId
    groupLoaderId.value = useGroupStore().currGroupInfo.groupLeaderId
})


</script>


<style scoped>

</style>
