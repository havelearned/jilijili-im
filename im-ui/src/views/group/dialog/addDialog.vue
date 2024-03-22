<template>
  <q-dialog v-model="flag" persistent>
    <q-card style="width: 700px; max-width: 80vw;" class="global-dark">
      <q-form @submit="addGroupChatSubmit">
        <q-card-section>
          <div class="text-h6">新建群聊</div>
        </q-card-section>
        <q-card-section class="q-pt-none">

          <q-stepper class="global-dark" v-model="step" ref="stepper"
                     animated>
            <q-step :name="1" title="取个名字" icon="settings"
                    :done="done1" class="text-teal">
              <q-input bg-color="white" v-model="groupChatFrom.groupName" filled label="群聊名称"
                       :rules="[(val)=>isNotEmpty(val) || val.length<1]"
              ></q-input>

              <q-stepper-navigation v-if="groupChatFrom.groupName">
                <q-btn @click="() => { done1 = true; step = 2 ;setup2()}" color="primary" label="下一步"/>
              </q-stepper-navigation>
            </q-step>

            <q-step :name="2"
                    title="选择您的好朋友"
                    icon="create_new_folder"
                    :done="done2">

              <ImSelect v-model="groupChatFrom.userIds" :options="friendList"
                        :option-value="'userId'" :option-label="'nickName'"></ImSelect>

              <q-stepper-navigation>
                <q-btn v-if="groupChatFrom.userIds.length>0" @click="() => { done2 = true; step = 3 }" color="primary"
                       label="下一步"/>
                <q-btn flat @click="step = 1;setup2()" color="primary" label="上一步" class="q-ml-sm"/>
              </q-stepper-navigation>
            </q-step>

            <q-step
              :name="3"
              title="备注"
              icon="add_comment"
              :done="done3"
            >
              <q-input label="备注" bg-color="white" filled v-model="groupChatFrom.remark" type="textarea"></q-input>

              <q-stepper-navigation>
                <q-btn color="primary" @click="done3 = true" label="确定"/>
                <q-btn flat @click="step = 2" color="primary" label="上一步" class="q-ml-sm"/>
              </q-stepper-navigation>
            </q-step>
          </q-stepper>

        </q-card-section>

        <q-card-actions align="right" class=" text-teal global-dark">
          <q-btn flat label="取消" v-close-popup/>
          <q-btn type="submit" flat label="创建" v-if="done3"/>
        </q-card-actions>
      </q-form>
    </q-card>
  </q-dialog>
</template>
<script lang="ts" setup>
import {ChatGroupForm} from "@/api/im/chatGroup/type";
import ImSelect from "@/components/ImSelect.vue";
import {addChatGroup} from "@/api/im/chatGroup";
import {isNotEmpty} from "@/utils/verify";
import {useGroupStore} from "@/stores/modules/group";
import {useFriendStore} from "@/stores/modules/friend";
import {listFriend} from "@/api/im/friend";


const {setCurrGroupInfo, fatchGroupChatList} = useGroupStore()

const step = ref(1)
const done1 = ref(false)
const done2 = ref(false)
const done3 = ref(false)
const flag = ref(false)
const friendList = ref([]);

let groupChatFrom: {} = reactive<ChatGroupForm>({
  id: '',
  groupLeaderId: '',
  groupName: '',
  remark: '',
  userIds: [],
  userName: '',
  pageNum: 0,
  pageSize: 0
})

function reset() {
  done1.value = false
  done2.value = false
  done3.value = false
  step.value = 1
    groupChatFrom = {}
}


function setup2() {
  listFriend({relationStatus: 2, pageNum: 1, pageSize: 100}).then((res:any)=>{
    if(res.code === 200){
      friendList.value = res.rows
    }
  })
}

async function addGroupChatSubmit() {
  let res = await addChatGroup(groupChatFrom)
  if (res.code === 200) {
    flag.value = false
    reset()
    groupChatFrom = {}
    setCurrGroupInfo(res.data)
    await fatchGroupChatList()
  }
}

defineExpose(
  {
    show() {
      flag.value = true
    }

  }
)
</script>


<style scoped>

</style>
