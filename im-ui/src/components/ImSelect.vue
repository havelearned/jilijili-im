<template>
  <q-select
    label="选择用户"
    filled
    v-model="model"
    :options="options"
    :option-value="optionValue"
    :option-label="optionLabel"
    option-disable="inactive"
    emit-value
    map-options
    multiple
    use-chips
    use-input
    :rules="[val=>isNotEmpty(val)]"
  />
</template>

<script setup>
import {isNotEmpty} from "@/utils/verify";

const props = defineProps( {
    options: {
        type: Array,
    default: []
    },
    optionValue: {
        type: String,
    default: 'id'
    },
    optionLabel: {
        type: String,
    default: 'name'
    },
    modelValue: {
        type: Array,
    default: []
    }
})
const emit = defineEmits(['update:modelValue'])
const model = ref(null)

watch(()=>model.value,(val,old)=>{
    this.$emit('update:modelValue', val)
    this.model = val
    console.log(this.model)
})
</script>
