<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="通知类型" prop="noticeType">
            <el-select v-model="queryParams.noticeType" placeholder="请选择通知类型" clearable>
              <el-option v-for="dict in im_notice_notice_type" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="用户昵称" prop="title">
            <el-input v-model="queryParams.nickName" placeholder="请输入用户昵称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="通知标题" prop="title">
            <el-input v-model="queryParams.title" placeholder="请输入通知标题" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="是否已读" prop="readStatus">
            <el-select v-model="queryParams.readStatus" placeholder="请选择是否已读" clearable>
              <el-option v-for="dict in im_read_status" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </transition>
    <el-card shadow="never">
      <template #header>
        <el-row :gutter="10" class="mb8">
<!--          <el-col :span="1.5">-->
<!--            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['im:notice:add']">新增</el-button>-->
<!--          </el-col>-->
<!--          <el-col :span="1.5">-->
<!--            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['im:notice:edit']">修改</el-button>-->
<!--          </el-col>-->
<!--          <el-col :span="1.5">-->
<!--            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['im:notice:remove']"-->
<!--              >删除</el-button-->
<!--            >-->
<!--          </el-col>-->
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['im:notice:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <!--        <el-table-column label="id" align="center" prop="id" v-if="true" />-->
        <!--        <el-table-column label="用户id" align="center" prop="userId" />-->
        <el-table-column label="接收得的用户" align="center" prop="userId">
          <template #default="scope">
            <div>{{scope.row.nickName}}</div>
          </template>
        </el-table-column>
        <el-table-column label="通知类型" align="center" prop="noticeType">
          <template #default="scope">
            <dict-tag :options="im_notice_notice_type" :value="scope.row.noticeType" />
          </template>
        </el-table-column>
        <el-table-column label="通知标题" align="center" prop="title" />
        <el-table-column label="是否已读" align="center" prop="readStatus">
          <template #default="scope">
            <dict-tag :options="im_read_status" :value="scope.row.readStatus" />
          </template>
        </el-table-column>
        <el-table-column label="通知内容" align="center" prop="content" />
        <el-table-column label="来源url" align="center" prop="fromUrl" />
        <!--        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
        <!--          <template #default="scope">-->
        <!--            <el-tooltip content="修改" placement="top">-->
        <!--              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['im:notice:edit']"></el-button>-->
        <!--            </el-tooltip>-->
        <!--            <el-tooltip content="删除" placement="top">-->
        <!--              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['im:notice:remove']"></el-button>-->
        <!--            </el-tooltip>-->
        <!--          </template>-->
        <!--        </el-table-column>-->
      </el-table>

      <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改用户通知对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="noticeFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="通知类型:1精选推送 2用户消息 3有人@你" prop="noticeType">
          <el-select v-model="form.noticeType" placeholder="请选择通知类型:1精选推送 2用户消息 3有人@你">
            <el-option v-for="dict in im_notice_notice_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="通知标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入通知标题" />
        </el-form-item>
        <el-form-item label="是否已读:1已读 0未读" prop="readStatus">
          <el-select v-model="form.readStatus" placeholder="请选择是否已读:1已读 0未读">
            <el-option v-for="dict in im_read_status" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="通知内容">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="来源url,可前往查看" prop="fromUrl">
          <el-input v-model="form.fromUrl" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Notice" lang="ts">
import { listNotice, getNotice, delNotice, addNotice, updateNotice } from '@/api/im/notification';
import { NoticeVO, NoticeQuery, NoticeForm } from '@/api/im/notification/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { im_notice_notice_type, im_read_status } = toRefs<any>(proxy?.useDict('im_notice_notice_type', 'im_read_status'));

const noticeList = ref<NoticeVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const noticeFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: NoticeForm = {
  id: undefined,
  userId: undefined,
  noticeType: undefined,
  title: undefined,
  readStatus: undefined,
  content: undefined,
  fromUrl: undefined,
}
const data = reactive<PageData<NoticeForm, NoticeQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    nickName: undefined,
    noticeType: undefined,
    title: undefined,
    readStatus: undefined,
    content: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "id不能为空", trigger: "blur" }
    ],
    title: [
      { required: true, message: "通知标题不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "通知内容不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询用户通知列表 */
const getList = async () => {
  loading.value = true;
  const res = await listNotice(queryParams.value);
  noticeList.value = res.rows;
  total.value = res.total;
  loading.value = false;
}

/** 取消按钮 */
const cancel = () => {
  reset();
  dialog.visible = false;
}

/** 表单重置 */
const reset = () => {
  form.value = {...initFormData};
  noticeFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 多选框选中数据 */
const handleSelectionChange = (selection: NoticeVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  dialog.visible = true;
  dialog.title = "添加用户通知";
  nextTick(() => {
    reset();
  });
}

/** 修改按钮操作 */
const handleUpdate = (row?: NoticeVO) => {
  loading.value = true
  dialog.visible = true;
  dialog.title = "修改用户通知";
  nextTick(async () => {
    reset();
    const _id = row?.id || ids.value[0]
    const res = await getNotice(_id);
    loading.value = false;
    Object.assign(form.value, res.data);
  });
}

/** 提交按钮 */
const submitForm = () => {
  noticeFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateNotice(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addNotice(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: NoticeVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除用户通知编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delNotice(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('im/notice/export', {
    ...queryParams.value
  }, `notice_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
