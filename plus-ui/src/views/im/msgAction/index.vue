<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="聊天id" prop="msgId">
            <el-input v-model="queryParams.msgId" placeholder="请输入聊天id" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="赞同量" prop="endorseCount">
            <el-input v-model="queryParams.endorseCount" placeholder="请输入赞同量" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="满分量" prop="fullCount">
            <el-input v-model="queryParams.fullCount" placeholder="请输入满分量" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="点赞量" prop="likeCount">
            <el-input v-model="queryParams.likeCount" placeholder="请输入点赞量" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['im:msgAction:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['im:msgAction:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['im:msgAction:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['im:msgAction:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="msgActionList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="id" align="center" prop="id" v-if="true" />
        <el-table-column label="聊天id" align="center" prop="msgId" />
        <el-table-column label="赞同量" align="center" prop="endorseCount" />
        <el-table-column label="满分量" align="center" prop="fullCount" />
        <el-table-column label="点赞量" align="center" prop="likeCount" />
        <el-table-column label="备注" align="center" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['im:msgAction:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['im:msgAction:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="total>0"
          :total="total"
          v-model:page="queryParams.pageNum"
          v-model:limit="queryParams.pageSize"
          @pagination="getList"
      />
    </el-card>
    <!-- 添加或修改聊天动作对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="msgActionFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="聊天id" prop="msgId">
          <el-input v-model="form.msgId" placeholder="请输入聊天id" />
        </el-form-item>
        <el-form-item label="赞同量" prop="endorseCount">
          <el-input v-model="form.endorseCount" placeholder="请输入赞同量" />
        </el-form-item>
        <el-form-item label="满分量" prop="fullCount">
          <el-input v-model="form.fullCount" placeholder="请输入满分量" />
        </el-form-item>
        <el-form-item label="点赞量" prop="likeCount">
          <el-input v-model="form.likeCount" placeholder="请输入点赞量" />
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

<script setup name="MsgAction" lang="ts">
import { listMsgAction, getMsgAction, delMsgAction, addMsgAction, updateMsgAction } from '@/api/im/msgAction';
import { MsgActionVO, MsgActionQuery, MsgActionForm } from '@/api/im/msgAction/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const msgActionList = ref<MsgActionVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const msgActionFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: MsgActionForm = {
  id: undefined,
  msgId: undefined,
  endorseCount: undefined,
  fullCount: undefined,
  likeCount: undefined,
  remark: undefined
}
const data = reactive<PageData<MsgActionForm, MsgActionQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    msgId: undefined,
    endorseCount: undefined,
    fullCount: undefined,
    likeCount: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "id不能为空", trigger: "blur" }
    ],
    msgId: [
      { required: true, message: "聊天id不能为空", trigger: "blur" }
    ],
    endorseCount: [
      { required: true, message: "赞同量不能为空", trigger: "blur" }
    ],
    fullCount: [
      { required: true, message: "满分量不能为空", trigger: "blur" }
    ],
    likeCount: [
      { required: true, message: "点赞量不能为空", trigger: "blur" }
    ],
    remark: [
      { required: true, message: "备注不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询聊天动作列表 */
const getList = async () => {
  loading.value = true;
  const res = await listMsgAction(queryParams.value);
  msgActionList.value = res.rows;
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
  msgActionFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: MsgActionVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加聊天动作";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: MsgActionVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getMsgAction(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改聊天动作";
}

/** 提交按钮 */
const submitForm = () => {
  msgActionFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateMsgAction(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addMsgAction(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: MsgActionVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除聊天动作编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delMsgAction(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('im/msgAction/export', {
    ...queryParams.value
  }, `msgAction_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
