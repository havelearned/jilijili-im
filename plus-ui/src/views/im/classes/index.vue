<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <el-form-item label="分类名称" prop="classesName">
            <el-input v-model="queryParams.classesName" placeholder="请输入分类名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd()" v-hasPermi="['im:classes:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="Sort" @click="handleToggleExpandAll">展开/折叠</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>
      <el-table
        v-loading="loading"
        :data="classesList"
        row-key="id"
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        ref="classesTableRef"
      >
        <!--        <el-table-column label="上级分类" prop="parentId"></el-table-column>-->
        <el-table-column label="分类名称" align="center" prop="classesName" />
        <el-table-column label="分类icon" align="center" prop="icon">
          <template #default="scope">
            <div v-html="scope.row.icon"></div>
          </template>
        </el-table-column>
        <el-table-column label="应用简介" align="center" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['im:classes:edit']" />
            </el-tooltip>
            <el-tooltip content="新增" placement="top">
              <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)" v-hasPermi="['im:classes:add']" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['im:classes:remove']" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加或修改应用分类对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="classesFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级分类" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="classesOptions"
            :props="{ value: 'id', label: 'classesName', children: 'children' }"
            value-key="id"
            placeholder="请选择上级分类"
            check-strictly
          />
        </el-form-item>
        <el-form-item label="分类名称" prop="classesName">
          <el-input v-model="form.classesName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类icon" prop="icon">
          <el-input v-model="form.icon" placeholder="请输入分类icon" />
        </el-form-item>
        <el-form-item label="应用简介" prop="remark">
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

<script setup name="Classes" lang="ts">
import { listClasses, getClasses, delClasses, addClasses, updateClasses } from "@/api/im/classes";
import { ClassesVO, ClassesQuery, ClassesForm } from '@/api/im/classes/types';

type ClassesOption = {
  id: number;
  classesName: string;
  children?: ClassesOption[];
}

const { proxy } = getCurrentInstance() as ComponentInternalInstance;;


const classesList = ref<ClassesVO[]>([]);
const classesOptions = ref<ClassesOption[]>([]);
const buttonLoading = ref(false);
const showSearch = ref(true);
const isExpandAll = ref(true);
const loading = ref(false);

const queryFormRef = ref<ElFormInstance>();
const classesFormRef = ref<ElFormInstance>();
const classesTableRef = ref<ElTableInstance>()

const dialog = reactive<DialogOption>({
    visible: false,
    title: ''
});


const initFormData: ClassesForm = {
    id: undefined,
    parentId: undefined,
    classesName: undefined,
    icon: undefined,
    remark: undefined,
}

const data = reactive<PageData<ClassesForm, ClassesQuery>>({
  form: {...initFormData},
  queryParams: {
    parentId: undefined,
    classesName: undefined,
    icon: undefined,
    params: {
    }
  },
  rules: {
    id: [
      { required: true, message: "id不能为空", trigger: "blur" }
    ],
    parentId: [
      { required: true, message: "上级分类不能为空", trigger: "change" }
    ],
    classesName: [
      { required: true, message: "分类名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询应用分类列表 */
const getList = async () => {
  loading.value = true;
  const res = await listClasses(queryParams.value);
  const data = proxy?.handleTree<ClassesVO>(res.data, "id", "parentId");
  if (data) {
    classesList.value = data;
    loading.value = false;
  }
}

/** 查询应用分类下拉树结构 */
const getTreeselect = async () => {
  const res = await listClasses();
  classesOptions.value = [];
  const data: ClassesOption = { id: 0, classesName: '顶级节点', children: [] };
  data.children = proxy?.handleTree<ClassesOption>(res.data, "id", "parentId");
  classesOptions.value.push(data);
}

// 取消按钮
const cancel = () => {
  reset();
  dialog.visible = false;
}

// 表单重置
const reset = () => {
  form.value = {...initFormData}
  classesFormRef.value?.resetFields();
}

/** 搜索按钮操作 */
const handleQuery = () => {
  getList();
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/** 新增按钮操作 */
const handleAdd = (row?: ClassesVO) => {
  dialog.visible = true;
  dialog.title = "添加应用分类";
  nextTick(() => {
    reset();
    getTreeselect();
    if (row != null && row.id) {
      form.value.parentId = row.parentId;
    } else {
      form.value.parentId = 0;
    }
  });
}

/** 展开/折叠操作 */
const handleToggleExpandAll = () => {
  isExpandAll.value = !isExpandAll.value;
  toggleExpandAll(classesList.value, isExpandAll.value)
}

/** 展开/折叠操作 */
const toggleExpandAll = (data: ClassesVO[], status: boolean) => {
  data.forEach((item) => {
    classesTableRef.value?.toggleRowExpansion(item, status)
    if (item.children && item.children.length > 0) toggleExpandAll(item.children, status)
  })
}

/** 修改按钮操作 */
const handleUpdate = (row: ClassesVO) => {
  loading.value = true;
  dialog.visible = true;
  dialog.title = "修改应用分类";
  nextTick(async () => {
    reset();
    await getTreeselect();
    if (row != null) {
      form.value.parentId = row.id;
    }
    const res = await getClasses(row.id);
    loading.value = false;
    Object.assign(form.value, res.data);
  });
}

/** 提交按钮 */
const submitForm = () => {
  classesFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateClasses(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addClasses(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      getList();
    }
  });
}

/** 删除按钮操作 */
const handleDelete = async (row: ClassesVO) => {
  await proxy?.$modal.confirm('是否确认删除应用分类编号为"' + row.id + '"的数据项？');
  loading.value = true;
  await delClasses(row.id).finally(() => loading.value = false);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
}

onMounted(() => {
  getList();
});
</script>
