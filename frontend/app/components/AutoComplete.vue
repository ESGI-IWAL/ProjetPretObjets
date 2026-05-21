<script setup lang="ts">
import { ref, computed, watch, onMounted, onBeforeUnmount } from "vue"

const props = defineProps<{
  id?: string
  modelValue: string
  options: string[]
  placeholder?: string
}>()

const emit = defineEmits(["update:modelValue"])

const input = ref<HTMLInputElement | null>(null)
const open = ref(false)
const highlightedIndex = ref(-1)

const query = computed({
  get: () => props.modelValue,
  set: (val) => emit("update:modelValue", val),
})

const filteredOptions = computed(() => {
  if (!query.value) return props.options
  return props.options.filter(option =>
    option
      .toLowerCase()
      .startsWith(query.value.toLowerCase())
  )
})

const selectOption = (option: string) => {
  query.value = option
  open.value = false
  highlightedIndex.value = -1
}

watch(query, (value) => {
  if (value) {
    open.value = true
  }
  highlightedIndex.value = -1
})

const onKeyDown = (e: KeyboardEvent) => {
  if (!open.value) open.value = true

  const optionCount = filteredOptions.value.length
  if (optionCount === 0) return

  if (e.key === "ArrowDown") {
    highlightedIndex.value =
      (highlightedIndex.value + 1 + optionCount) % optionCount
  }

  if (e.key === "ArrowUp") {
    highlightedIndex.value =
      (highlightedIndex.value - 1 + optionCount) % optionCount
  }

  if (e.key === "Enter") {
    const item = filteredOptions.value[highlightedIndex.value]
    if (item) selectOption(item)
  }

  if (e.key === "Escape") {
    open.value = false
  }
}

const onClickOutside = (e: MouseEvent) => {
  if (!input.value?.contains(e.target as Node)) {
    open.value = false
  }
}

onMounted(() => {
  document.addEventListener("click", onClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener("click", onClickOutside)
})
</script>

<template>
  <div class="autocomplete">
    <input
      ref="input"
      :id="props.id"
      v-model="query"
      :placeholder="placeholder"
      class="form-input"
      @focus="open = true"
      @keydown="onKeyDown"
    />

    <ul v-if="open && filteredOptions.length" class="dropdown">
      <li
        v-for="(option, index) in filteredOptions"
        :key="option"
        :class="{ active: index === highlightedIndex }"
        @mousedown.prevent="selectOption(option)"
      >
        {{ option }}
      </li>
    </ul>
  </div>
</template>

<style scoped>
.autocomplete {
  position: relative;
}

.dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  border: 1px solid #ddd;
  background: white;
  max-height: 200px;
  overflow-y: auto;
  z-index: 10;
}

.dropdown li {
  padding: 8px;
  cursor: pointer;
}

.dropdown li.active {
  background: #eee;
}
</style>