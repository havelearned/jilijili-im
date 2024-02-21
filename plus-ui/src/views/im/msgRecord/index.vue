<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="发送者" prop="senderId">
            <el-input v-model="queryParams.senderId" placeholder="请输入发送者" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="接收者" prop="toId">
            <el-input v-model="queryParams.toId" placeholder="请输入接收者" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="群聊" prop="toGroupId">
            <el-input v-model="queryParams.toGroupId" placeholder="请输入群聊" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="频道" prop="toAppId">
            <el-input v-model="queryParams.toAppId" placeholder="请输入频道" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="内容类型" prop="msgType">
            <el-select v-model="queryParams.msgType" placeholder="请选择内容类型" clearable>
              <el-option v-for="dict in im_msg_type" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="消息类型" prop="toType">
            <el-select v-model="queryParams.toType" placeholder="请选择消息类型" clearable>
              <el-option v-for="dict in im_msg_to_type" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="消息状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="请选择消息状态" clearable>
              <el-option v-for="dict in common_status" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="读取状态" prop="unread">
            <el-input v-model="queryParams.unread" placeholder="请输入读取状态" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="引用|回复消息id" prop="refMsgId">
            <el-input v-model="queryParams.refMsgId" placeholder="请输入引用|回复消息id" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['im:msgRecord:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['im:msgRecord:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['im:msgRecord:remove']"
              >删除</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['im:msgRecord:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="msgRecordList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="id消息" align="center" prop="id" v-if="true" />
        <el-table-column label="发送者" align="center" prop="senderId" />
        <el-table-column label="接收者" align="center" prop="toId" />
        <el-table-column label="群聊" align="center" prop="toGroupId" />
        <el-table-column label="频道" align="center" prop="toAppId" />
        <el-table-column label="消息内容" align="center" prop="content" />
        <el-table-column label="内容类型" align="center" prop="msgType">
          <template #default="scope">
            <dict-tag :options="im_msg_type" :value="scope.row.msgType" />
          </template>
        </el-table-column>
        <el-table-column label="消息类型" align="center" prop="toType">
          <template #default="scope">
            <dict-tag :options="im_msg_to_type" :value="scope.row.toType" />
          </template>
        </el-table-column>
        <el-table-column label="消息状态" align="center" prop="status">
          <template #default="scope">
            <dict-tag :options="common_status" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="读取状态" align="center" prop="unread">
          <template #default="scope">
            <dict-tag :options="common_status" :value="scope.row.unread" />
          </template>
        </el-table-column>
        <el-table-column label="引用|回复消息id" align="center" prop="refMsgId" />
        <el-table-column label="备注" align="center" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['im:msgRecord:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['im:msgRecord:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改聊天消息记录对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="msgRecordFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="发送者" prop="senderId">
          <el-input v-model="form.senderId" placeholder="请输入发送者" />
        </el-form-item>
        <el-form-item label="接收者" prop="toId">
          <el-input v-model="form.toId" placeholder="请输入接收者" />
        </el-form-item>
        <el-form-item label="群聊" prop="toGroupId">
          <el-input v-model="form.toGroupId" placeholder="请输入群聊" />
        </el-form-item>
        <el-form-item label="频道" prop="toAppId">
          <el-input v-model="form.toAppId" placeholder="请输入频道" />
        </el-form-item>
        <el-form-item label="消息内容">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="内容类型" prop="msgType">
          <el-select v-model="form.msgType" placeholder="请选择内容类型">
            <el-option v-for="dict in im_msg_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="消息类型" prop="toType">
          <el-select v-model="form.toType" placeholder="请选择消息类型">
            <el-option v-for="dict in im_msg_to_type" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="消息状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in common_status" :key="dict.value" :label="parseInt(dict.value)">{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="读取状态" prop="unread">
          <el-input v-model="form.unread" placeholder="请输入读取状态" />
        </el-form-item>
        <el-form-item label="引用|回复消息id" prop="refMsgId">
          <el-input v-model="form.refMsgId" placeholder="请输入引用|回复消息id" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="MsgRecord" lang="ts">
import { listMsgRecord, getMsgRecord, delMsgRecord, addMsgRecord, updateMsgRecord } from '@/api/im/msgRecord';
import { MsgRecordVO, MsgRecordQuery, MsgRecordForm } from '@/api/im/msgRecord/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { im_msg_to_type, common_status, im_msg_type } = toRefs<any>(proxy?.useDict('im_msg_to_type', 'common_status', 'im_msg_type'));

const msgRecordList = ref<MsgRecordVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const msgRecordFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: MsgRecordForm = {
  id: undefined,
  senderId: undefined,
  toId: undefined,
  toGroupId: undefined,
  toAppId: undefined,
  content: undefined,
  msgType: undefined,
  toType: undefined,
  status: undefined,
  unread: undefined,
  refMsgId: undefined,
  remark: undefined
}
const data = reactive<PageData<MsgRecordForm, MsgRecordQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    senderId: undefined,
    toId: undefined,
    toGroupId: undefined,
    toAppId: undefined,
    content: undefined,
    msgType: undefined,
    toType: undefined,
    status: undefined,
    unread: undefined,
    refMsgId: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "id消息不能为空", trigger: "blur" }
    ],
    senderId: [
      { required: true, message: "发送者不能为空", trigger: "blur" }
    ],
    toId: [
      { required: true, message: "接收者不能为空", trigger: "blur" }
    ],
    toGroupId: [
      { required: true, message: "群聊不能为空", trigger: "blur" }
    ],
    toAppId: [
      { required: true, message: "频道不能为空", trigger: "blur" }
    ],
    content: [
      { required: true, message: "消息内容不能为空", trigger: "blur" }
    ],
    msgType: [
      { required: true, message: "内容类型不能为空", trigger: "change" }
    ],
    toType: [
      { required: true, message: "消息类型不能为空", trigger: "change" }
    ],
    status: [
      { required: true, message: "消息状态不能为空", trigger: "change" }
    ],
    unread: [
      { required: true, message: "读取状态不能为空", trigger: "blur" }
    ],
    refMsgId: [
      { required: true, message: "引用|回复消息id不能为空", trigger: "blur" }
    ],
    remark: [
      { required: true, message: "备注不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询聊天消息记录列表 */
const getList = async () => {
  loading.value = true;
  const res = await listMsgRecord(queryParams.value);
  msgRecordList.value = res.rows;
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
  msgRecordFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: MsgRecordVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加聊天消息记录";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: MsgRecordVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getMsgRecord(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改聊天消息记录";
}

/** 提交按钮 */
const submitForm = () => {
  msgRecordFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateMsgRecord(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addMsgRecord(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: MsgRecordVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除聊天消息记录编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delMsgRecord(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('im/msgRecord/export', {
    ...queryParams.value
  }, `msgRecord_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
