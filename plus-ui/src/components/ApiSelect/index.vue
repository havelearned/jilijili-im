<!--
  @FileDescription: 下拉选择框,
  @Date: 2024-01-4,
  @version: 0.0.1,
  @Author: 罗家炬,
 -->
<template>
  <el-select
    v-model="selected"
    :multiple="multiples"
    :filterable="filterable"
    :allow-create="allowCreate"
    :reserve-keyword="false"
    :placeholder="placeholder"
    @change="selectChange"
    collapse-tags
    clearable
    default-first-option
    v-bind="$props"
  >
    <el-option v-for="option in options"
               :key="getOptionKey(option)"
               :label="getOptionLabel(option)"
               :value="getOptionValue(option)" />
  </el-select>
</template>

<script lang="ts" setup>
import { defineEmits, defineProps, ref } from "vue";
// 定义组件的属性
const props = defineProps({
  // 选项数组,数据源列如:[{},{},{},{}]
  options: {
    type: Array,
    required: true
  },
  // 标签字段,显示的标签
  labelField: {
    type: String,
    default: "label"
  },
  // 值字段,返回的值,如果多个值,则为数组,单个值则为字符串
  valueField: {
    type: String,
    default: "value"
  },
  // 提示占位符
  placeholder: {
    type: String,
    default: ""
  },
  // 是否多选
  multiples: {
    type: Boolean,
    default: false
  },
  // 是否可过滤筛选
  filterable: {
    type: Boolean,
    default: false
  },
  // 是否允许创建新值
  allowCreate: {
    type: Boolean,
    default: false
  }
});


// 定义组件的事件
const emit = defineEmits(["update:options", "update:modelValue"]);

// 组件内部状态
const selected = ref(null);

// 获取选项的键、标签和值
const getOptionKey = (option) => option[props.valueField];
const getOptionLabel = (option) => option[props.labelField];
const getOptionValue = (option) => option[props.valueField];

// 选择发生改变的事件处理函数
const selectChange = (value) => {
  selected.value = value;
  emit("update:modelValue", value);
  emit("update:options", props.options);
};
</script>

<!--使用示例-->
<!--
  <template>
    <ApiSelect v-model="selectedTags"
               :options="options"
               :multiples="true"
               :allow-create="true"
               :filterable="true"
               label-field="name"
               value-field="type"
               placeholder="请选择"/>
    <el-button @click="console.log(JSON.stringify(selectedTags))">查看已选</el-button>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import ApiSelect from '@/component/apiSelect/ApiSelect.vue'
const   selectedTags =ref([])
const options =
    [
      {
        "name": "软件工程",
        "type": "17"
      },
      {
        "name": "后端",
        "type": "16"
      },
      {
        "name": "Java",
        "type": "15"
      },
      {
        "name": "JavaScript",
        "type": "14"
      },
      {
        "name": "PHP",
        "type": "13"
      },
      {
        "name": "Python",
        "type": "12"
      },
      {
        "name": "区块链",
        "type": "11"
      },
      {
        "name": "大数据",
        "type": "8"
      },
      {
        "name": "移动开发",
        "type": "9"
      },
      {
        "name": "嵌入式",
        "type": "8"
      },
      {
        "name": "开发工具",
        "type": "7"
      },
      {
        "name": "结构与算法",
        "type": "6"
      },
      {
        "name": "微软技术",
        "type": "5"
      },
      {
        "name": "测试",
        "type": "4"
      },
      {
        "name": "游戏",
        "type": "3"
      },
      {
        "name": "网络",
        "type": "2"
      },
      {
        "name": "运维",
        "type": "1"
      }
    ]
</script>
-->
