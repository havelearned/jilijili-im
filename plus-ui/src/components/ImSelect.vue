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

<script>

import {ref} from "vue";
import {isNotEmpty} from "@/utils/verify";

export default {
  methods: {isNotEmpty},
  props: {
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
  },
  emits: ['update:modelValue'],
  setup() {
    const model = ref(null)
    return {
      model
    }
  },
  watch: {
    model: {
      modelValue: true,
      handler(val) {
        this.$emit('update:modelValue', val)
        this.model = val
        console.log(this.model)
      }
    }
  },
  mounted() {
    this.model = this.modelValue
  }
}
</script>
