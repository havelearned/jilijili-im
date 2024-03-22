<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="卡片名称" prop="cardName">
            <el-input v-model="queryParams.cardName" placeholder="请输入卡片名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="卡片的价格" prop="price">
            <el-input v-model="queryParams.price" placeholder="请输入卡片的价格" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="稀有度" prop="rarity">
            <el-input v-model="queryParams.rarity" placeholder="请输入稀有度" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="卡片状态" prop="cardStatus">
            <el-select v-model="queryParams.cardStatus" placeholder="请选择卡片状态" clearable>
              <el-option v-for="dict in common_status" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['im:card:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate()" v-hasPermi="['im:card:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="['im:card:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['im:card:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="cardList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="id" align="center" prop="id" v-if="true" />
        <el-table-column label="卡片名称" align="center" prop="cardName" />
        <el-table-column label="卡片的价格" align="center" prop="price" />
        <el-table-column label="稀有度" align="center" prop="rarity">
          <template #default="scope">
            <dict-tag :options="im_card_status" :value="scope.row.rarity" />
          </template>
        </el-table-column>
        <el-table-column label="卡片状态" align="center" prop="cardStatus">
          <template #default="scope">
            <dict-tag :options="common_status" :value="scope.row.cardStatus" />
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['im:card:edit']"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['im:card:remove']"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
    </el-card>
    <!-- 添加或修改社交卡片对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="cardFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="卡片名称" prop="cardName">
          <el-input v-model="form.cardName" placeholder="请输入卡片名称" />
        </el-form-item>
        <el-form-item label="卡片的价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入卡片的价格" />
        </el-form-item>
        <el-form-item label="稀有度" prop="rarity">
          <el-input v-model="form.rarity" placeholder="请输入稀有度" />
        </el-form-item>
        <el-form-item label="卡片状态" prop="cardStatus">
          <el-radio-group v-model="form.cardStatus">
            <el-radio v-for="dict in common_status" :key="dict.value" :label="parseInt(dict.value)">{{dict.label}}</el-radio>
          </el-radio-group>
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

<script setup name="Card" lang="ts">
import { listCard, getCard, delCard, addCard, updateCard } from '@/api/im/card';
import { CardVO, CardQuery, CardForm } from '@/api/im/card/types';

const { proxy } = getCurrentInstance() as ComponentInternalInstance;
const { common_status } = toRefs<any>(proxy?.useDict('common_status'));
const { im_card_status } = toRefs<any>(proxy?.useDict('im_card_status'));

const cardList = ref<CardVO[]>([]);
const buttonLoading = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const cardFormRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ''
});

const initFormData: CardForm = {
  id: undefined,
  cardName: undefined,
  price: undefined,
  rarity: undefined,
  cardStatus: undefined,
  remark: undefined
}
const data = reactive<PageData<CardForm, CardQuery>>({
  form: {...initFormData},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    cardName: undefined,
    price: undefined,
    rarity: undefined,
    cardStatus: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "id不能为空", trigger: "blur" }
    ],
    cardName: [
      { required: true, message: "卡片名称不能为空", trigger: "blur" }
    ],
    price: [
      { required: true, message: "卡片的价格不能为空", trigger: "blur" }
    ],
    rarity: [
      { required: true, message: "稀有度不能为空", trigger: "blur" }
    ],
    cardStatus: [
      { required: true, message: "卡片状态不能为空", trigger: "change" }
    ],
    remark: [
      { required: true, message: "备注不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询社交卡片列表 */
const getList = async () => {
  loading.value = true;
  const res = await listCard(queryParams.value);
  cardList.value = res.rows;
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
  cardFormRef.value?.resetFields();
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
const handleSelectionChange = (selection: CardVO[]) => {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset();
  dialog.visible = true;
  dialog.title = "添加社交卡片";
}

/** 修改按钮操作 */
const handleUpdate = async (row?: CardVO) => {
  reset();
  const _id = row?.id || ids.value[0]
  const res = await getCard(_id);
  Object.assign(form.value, res.data);
  dialog.visible = true;
  dialog.title = "修改社交卡片";
}

/** 提交按钮 */
const submitForm = () => {
  cardFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateCard(form.value).finally(() =>  buttonLoading.value = false);
      } else {
        await addCard(form.value).finally(() =>  buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("修改成功");
      dialog.visible = false;
      await getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row?: CardVO) => {
  const _ids = row?.id || ids.value;
  await proxy?.$modal.confirm('是否确认删除社交卡片编号为"' + _ids + '"的数据项？').finally(() => loading.value = false);
  await delCard(_ids);
  proxy?.$modal.msgSuccess("删除成功");
  await getList();
}

/** 导出按钮操作 */
const handleExport = () => {
  proxy?.download('im/card/export', {
    ...queryParams.value
  }, `card_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  getList();
});
</script>
