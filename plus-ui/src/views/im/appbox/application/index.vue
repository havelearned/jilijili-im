<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="search" v-show="showSearch">
        <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
          <!--          <el-form-item label="同级查询" prop="parentId">-->
          <!--            <api-select-->
          <!--              :apiUrl="'im/appboxapplication/list'"-->
          <!--              v-model="queryParams.parentId"-->
          <!--              :query="queryParams"-->
          <!--              labelField="appName"-->
          <!--              valueField="parentId"-->
          <!--            >-->
          <!--            </api-select>-->
          <!--          </el-form-item>-->

          <el-form-item label="名称" prop="appName">
            <el-input v-model="queryParams.appName" placeholder="请输入名称" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="热度" prop="popularity">
            <el-input v-model="queryParams.popularity" placeholder="请输入热度" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="访问量" prop="pv">
            <el-input v-model="queryParams.pv" placeholder="请输入页面访问量" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label=" 独立访客" prop="uv">
            <el-input v-model="queryParams.uv" placeholder="请输入 独立访客数" clearable style="width: 240px" @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="频道状态" prop="channelStatus">
            <el-select v-model="queryParams.channelStatus" placeholder="请选择频道状态" clearable>
              <el-option v-for="dict in im_channel_status" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间" style="width: 308px">
            <el-date-picker
              v-model="dateRangeCreateTime"
              value-format="YYYY-MM-DD HH:mm:ss"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="[new Date(2000, 1, 1, 0, 0, 0), new Date(2000, 1, 1, 23, 59, 59)]"
            />
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd()" v-hasPermi="['im:appboxapplication:add']"> 新增 </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="Sort" @click="handleToggleExpandAll">展开/折叠</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>
      <el-table
        v-loading="loading"
        :data="appboxapplicationList"
        row-key="id"
        :default-expand-all="isExpandAll"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        ref="appboxapplicationTableRef"
      >
        <el-table-column label="名称" align="center" prop="appName" />
        <el-table-column label="封面" align="center" prop="cover">
          <template #default="scope">
            <ImagePreview
              v-if="scope.row.channelStatus === 1"
              :width="200"
              :height="100"
              :src="scope.row.coverUrl"
              :preview-src-list="[scope.row.coverUrl]"
            />
          </template>
        </el-table-column>
        <el-table-column label="图标" align="center" prop="iconUrl">
          <template #default="{ row }">
            <ImagePreview width="50" :height="50" v-if="row.parentId === 0" :src="row.iconUrl"></ImagePreview>
            <div v-else v-html="row.iconUrl"></div>
          </template>
        </el-table-column>
        <el-table-column label="热度" align="center" prop="popularity" />
        <el-table-column label="页面访问量" align="center" prop="pv" />
        <el-table-column label=" 独立访客数" align="center" prop="uv" />
        <el-table-column label="频道状态" align="center" prop="channelStatus">
          <template #default="scope">
            <dict-tag :options="im_channel_status" :value="scope.row.channelStatus" />
          </template>
        </el-table-column>
        <el-table-column label="应用简介" align="center" prop="remark" show-tooltip-when-overflow />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-tooltip content="修改" placement="top">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['im:appboxapplication:edit']" />
            </el-tooltip>
            <el-tooltip content="添加子频道频道" placement="top">
              <el-button link type="primary" icon="Plus" @click="handleAdd(scope.row)" v-hasPermi="['im:appboxapplication:add']" />
            </el-tooltip>
            <el-tooltip content="配置固定用户" placement="top" v-if="scope.row.channelStatus === 1">
              <el-button link type="primary" icon="Finished" @click="handleConfig(scope.row)" v-hasPermi="['im:appboxapplication:add']" />
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['im:appboxapplication:remove']" />
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 添加或修改创建应用or频道对话框 -->
    <el-dialog :title="dialog.title" v-model="dialog.visible" width="500px" append-to-body>
      <el-form ref="appboxapplicationFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="选中应用" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="appboxapplicationOptions"
            :props="{ value: 'id', label: 'appName', children: 'children' }"
            value-key="id"
            placeholder="请选择上级id"
            check-strictly
          />
        </el-form-item>
        <el-form-item label="选择分类" prop="classesId" :rules="rules">
          <el-tree-select
            v-model="form.classesId"
            :data="classesOptions"
            :props="{ value: 'id', label: 'classesName', children: 'children' }"
            value-key="id"
            placeholder="请选择上级id"
            check-strictly
          />
        </el-form-item>
        <el-form-item label="选中配置" prop="configId">
          <el-input v-model="form.configId" placeholder="请输入配置id" />
        </el-form-item>
        <el-form-item label="名称" prop="appName">
          <el-input v-model="form.appName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="封面">
          <el-space>
            <imageUpload v-model="form.cover" :limit="1" />
          </el-space>
        </el-form-item>
        <el-form-item label="图标" prop="iconUrl">
          <el-input v-model="form.iconUrl" />
        </el-form-item>
        <el-form-item label="热度" prop="popularity">
          <el-input v-model="form.popularity" placeholder="请输入热度" />
        </el-form-item>
        <el-form-item label="频道状态" prop="channelStatus">
          <el-select v-model="form.channelStatus" placeholder="请选择频道状态">
            <el-option v-for="dict in im_channel_status" :key="dict.value" :label="dict.label" :value="parseInt(dict.value)"></el-option>
          </el-select>
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
    <!-- 配置设置对话框 -->
    <el-dialog title="配置" v-model="configDialog.visible" width="500px" append-to-body>
      <el-form ref="fixedAppUserConfigRef" :model="configForm" label-width="80px">
        <el-form-item label="选中用户" prop="userIds">
          <ApiSelect
            v-model="configForm.userIds"
            :options="userOption"
            :multiples="true"
            :value="userOption"
            :filterable="true"
            value-field="userId"
            label-field="nickName"
            placeholder="select"
          >
          </ApiSelect>
        </el-form-item>
        <el-form-item label="标识" prop="identity">
          <el-select v-model="configForm.identity" placeholder="请选择标识">
            <el-option
              v-for="dict in im_appbox_fixed_app_user_config_identity"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="头像小图标" prop="iconUrl">
          <el-input v-model="configForm.iconUrl" placeholder="请输入头像小图标" />
        </el-form-item>
        <el-form-item label="应用简介" prop="remark">
          <el-input v-model="configForm.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button :loading="buttonLoading" type="primary" @click="configSubmitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Appboxapplication" lang="ts">
import {
  addAppboxapplication,
  delAppboxapplication,
  getAppboxapplication,
  listAppboxapplication,
  updateAppboxapplication
} from "@/api/im/appbox/appliction";
import { AppboxapplicationForm, AppboxapplicationQuery, AppboxapplicationVO } from "@/api/im/appbox/appliction/types";
import { addFixedAppUserConfig, getFixedAppUserConfig } from "@/api/im/appbox/fixedAppUserConfig";
import { FixedAppUserConfigForm } from "@/api/im/appbox/fixedAppUserConfig/types";
import { toRefs } from "vue";
import { listUser } from "@/api/system/user";
import { UserQuery, UserVO } from "@/api/system/user/types";
import ApiSelect from "@/components/ApiSelect/index.vue";
import ImagePreview from "@/components/ImagePreview/index.vue";
import { ClassesVO } from "@/api/im/classes/types";
import { listClasses } from "@/api/im/classes";


type AppboxapplicationOption = {
  id: number;
  appName: string;
  children?: AppboxapplicationOption[];
}

type ClassesOption = {
  id: number;
  classesName: string;
  children?: ClassesOption[];
}

const { proxy } = getCurrentInstance() as ComponentInternalInstance;

const { im_channel_status } = toRefs<any>(proxy?.useDict("im_channel_status"));

const { im_appbox_fixed_app_user_config_identity } = toRefs<any>(proxy?.useDict("im_appbox_fixed_app_user_config_identity"));

const appboxapplicationList = ref<AppboxapplicationVO[]>([]);
const appboxapplicationOptions = ref<AppboxapplicationOption[]>([]);
const classesOptions = ref<ClassesOption[]>([]);
const buttonLoading = ref(false);
const showSearch = ref(true);
const isExpandAll = ref(true);
const loading = ref(false);

const queryFormRef = ref<ElFormInstance>();
const appboxapplicationFormRef = ref<ElFormInstance>();
const appboxapplicationTableRef = ref<ElTableInstance>();
const fixedAppUserConfigRef = ref<ElFormInstance>();

const dialog = reactive<DialogOption>({
  visible: false,
  title: ""
});
const configDialog = reactive<DialogOption>({
  visible: false,
  title: ""
});


const dateRangeCreateTime = ref<[DateModelType, DateModelType]>(["", ""]);

const initFormData: AppboxapplicationForm = {
  cover: "",
  id: undefined,
  parentId: undefined,
  configId: undefined,
  appName: undefined,
  iconUrl: undefined,
  popularity: undefined,
  pv: undefined,
  uv: undefined,
  channelStatus: undefined,
  remark: undefined
};
const initConfigFormData: FixedAppUserConfigForm = {
  id: undefined,
  appId: undefined,
  userIds: undefined,
  identity: undefined,
  iconUrl: undefined,
  remark: undefined
};

const data = reactive<PageData<AppboxapplicationForm, AppboxapplicationQuery>>({
  form: { ...initFormData },
  configForm: { ...initConfigFormData },
  queryParams: {
    parentId: undefined,
    configId: undefined,
    appName: undefined,
    iconUrl: undefined,
    popularity: undefined,
    pv: undefined,
    uv: undefined,
    channelStatus: undefined,
    params: {
      createTime: undefined
    }
  },
  rules: {
    id: [
      { required: true, message: "id不能为空", trigger: "blur" }
    ],
    parentId: [
      { required: true, message: "上级id不能为空", trigger: "blur" }
    ],
    configId: [
      { required: false, message: "配置id不能为空", trigger: "blur" }
    ],
    appName: [
      { required: true, message: "名称不能为空", trigger: "blur" }
    ],
    iconUrl: [
      { required: false, message: "图标不能为空", trigger: "blur" }
    ],
    popularity: [
      { required: false, message: "热度不能为空", trigger: "blur" }
    ],
    pv: [
      { required: false, message: "页面访问量不能为空", trigger: "blur" }
    ],
    uv: [
      { required: false, message: " 独立访客数不能为空", trigger: "blur" }
    ],
    channelStatus: [
      { required: false, message: "频道状态", trigger: "change" }
    ],
    remark: [
      { required: false, message: "应用简介不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, configForm, rules } = toRefs(data);

/** 查询创建应用or频道列表 */
const getList = async () => {
  loading.value = true;
  queryParams.value.params = {};
  proxy?.addDateRange(queryParams.value, dateRangeCreateTime.value, "CreateTime");
  const res = await listAppboxapplication(queryParams.value);
  const data = proxy?.handleTree<AppboxapplicationVO>(res.data, "id", "parentId");
  if (data) {
    appboxapplicationList.value = data;
    loading.value = false;
  }
};

/** 查询创建应用or频道下拉树结构 */
const getTreeselect = async () => {
  const res = await listAppboxapplication();
  appboxapplicationOptions.value = [];
  const data: AppboxapplicationOption = { id: 0, appName: "顶级节点", children: [] };
  data.children = proxy?.handleTree<AppboxapplicationOption>(res.data, "id", "parentId");
  appboxapplicationOptions.value.push(data);

  const res2 = await listClasses();
  classesOptions.value = [];
  const data2: ClassesOption = { id: 0, classesName: "顶级节点", children: [] };
  data2.children = proxy?.handleTree<ClassesOption>(res2.data, "id", "parentId");
  classesOptions.value.push(data2);
};


// 取消按钮
const cancel = () => {
  reset();
  dialog.visible = false;
  configDialog.visible = false;
};

// 表单重置
const reset = () => {
  form.value = { ...initFormData };
  configForm.value = { ...initConfigFormData };
  appboxapplicationFormRef.value?.resetFields();
  fixedAppUserConfigRef.value?.resetFields();
};

/** 搜索按钮操作 */
const handleQuery = () => {
  getList();
};

/** 重置按钮操作 */
const resetQuery = () => {
  dateRangeCreateTime.value = ["", ""];
  queryFormRef.value?.resetFields();
  handleQuery();
};

/** 新增按钮操作 */
const handleAdd = (row?: AppboxapplicationVO) => {
  dialog.visible = true;
  dialog.title = "添加 创建应用或者频道频道";
  nextTick(() => {
    reset();
    getTreeselect();
    console.log(row);
    if (row != null && row.id) {
      form.value.parentId = row.id;
    } else {
      form.value.parentId = 0;
    }
  });
};

/** 展开/折叠操作 */
const handleToggleExpandAll = () => {
  isExpandAll.value = !isExpandAll.value;
  toggleExpandAll(appboxapplicationList.value, isExpandAll.value);
};

/** 展开/折叠操作 */
const toggleExpandAll = (data: AppboxapplicationVO[], status: boolean) => {
  data.forEach((item) => {
    appboxapplicationTableRef.value?.toggleRowExpansion(item, status);
    if (item.children && item.children.length > 0) toggleExpandAll(item.children, status);
  });
};

/** 修改按钮操作 */
const handleUpdate = (row: AppboxapplicationVO) => {
  loading.value = true;
  dialog.visible = true;
  dialog.title = "修改 创建应用or频道";
  nextTick(async () => {
    reset();
    await getTreeselect();
    if (row != null) {
      form.value.parentId = row.id;
    }
    const res = await getAppboxapplication(row.id);
    loading.value = false;
    Object.assign(form.value, res.data);
  });
};

const userOption = ref<UserVO[]>([]);
const getUserList = async () => {
  const res = await listUser({} as UserQuery);
  userOption.value = res.rows;
};
/** 配置按钮 */
const handleConfig = (row: AppboxapplicationVO) => {
  loading.value = false;
  configDialog.visible = true;
  nextTick(async () => {
    reset();
    await getUserList();
    configForm.value.appId = row.id;
    const res = await getFixedAppUserConfig(row.id);
    if (res.data) {
      configForm.value = res.data;
    }
  });
};

/** 提交按钮 */
const submitForm = () => {
  appboxapplicationFormRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      if (form.value.id) {
        await updateAppboxapplication(form.value).finally(() => buttonLoading.value = false);
      } else {
        await addAppboxapplication(form.value).finally(() => buttonLoading.value = false);
      }
      proxy?.$modal.msgSuccess("操作成功");
      dialog.visible = false;
      getList();
    }
  });
};

/** 配置提交 */
const configSubmitForm = () => {
  fixedAppUserConfigRef.value?.validate(async (valid: boolean) => {
    if (valid) {
      buttonLoading.value = true;
      console.log("提交的配置表单=>", configForm.value);
      await addFixedAppUserConfig(configForm.value).finally(() => {
        dialog.visible = false;
        configDialog.visible = false;
        buttonLoading.value = false;
      });
      proxy?.$modal.msgSuccess("操作成功");
      getList();
    }
  });

};

/** 删除按钮操作 */
const handleDelete = async (row: AppboxapplicationVO) => {
  await proxy?.$modal.confirm("是否确认删除创建应用or频道编号为\"" + row.id + "\"的数据项？");
  loading.value = true;
  await delAppboxapplication(row.id).finally(() => loading.value = false);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
};

onMounted(() => {
  getList();
});
</script>
