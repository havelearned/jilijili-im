<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter"
                :leave-active-class="proxy?.animate.searchAnimate.leave"></transition>
    <el-card shadow="never">
      <template #header>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="warning" @click="handlePublishAll" v-hasPermi="['im:notice:add']">通知所有人
            </el-button>
          </el-col>
        </el-row>
      </template>
      <el-form ref="noticeFormRef" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="选择用户" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入用户" />-->
<!--        </el-form-item>-->
        <el-form-item label="通知类型" prop="noticeType">
          <el-select v-model="form.noticeType" placeholder="请选择通知类型" disabled>
            <el-option v-for="dict in im_notice_notice_type" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="通知标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入通知标题" />
        </el-form-item>
        <el-form-item label="通知内容">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="配置url" prop="fromUrl">
          <el-input v-model="form.fromUrl" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup name="Notice" lang="ts">
import { addNotice } from "@/api/im/notification";
import { NoticeForm, NoticeQuery } from "@/api/im/notification/types";

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const {
  im_notice_notice_type,
  im_read_status
} = toRefs<any>(proxy?.useDict("im_notice_notice_type", "im_read_status"));

const buttonLoading = ref(false);
const loading = ref(true);


const noticeFormRef = ref<ElFormInstance>();


const initFormData: NoticeForm = {
  id: undefined,
  userId: undefined,
  noticeType: undefined,
  title: undefined,
  readStatus: undefined,
  content: undefined,
  fromUrl: undefined,
  nickName: undefined
};
const data = reactive<PageData<NoticeForm, NoticeQuery>>({
  form: { ...initFormData },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    nickName: undefined,
    noticeType: undefined,
    title: undefined,
    readStatus: undefined,
    content: undefined,
    params: {}
  },
  rules: {
    title: [
      { required: true, message: "通知标题不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "通知内容不能为空", trigger: "blur" }
    ]
  }
});

const { form, rules } = toRefs(data);


/** 取消按钮 */
const cancel = () => {
  reset();
};

/** 表单重置 */
const reset = () => {
  form.value = { ...initFormData };
  noticeFormRef.value?.resetFields();
};

/** 提交按钮 */
const submitForm = () => {
  noticeFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await notifyAll(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
    }
  });
};

/**
 * 对应发布
 */
const handlePublishAll = () => {
};

onMounted(() => {
  form.value.noticeType = 1

});
</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
