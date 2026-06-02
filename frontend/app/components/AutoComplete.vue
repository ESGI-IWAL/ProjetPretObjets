<script setup lang="ts">
import { ref, computed, watch, onMounted, onBeforeUnmount } from "vue"

export type IOption =
  | string
  | {
      id?: string | number
      label: string
    }

const props = defineProps<{
  id?: string
  modelValue?: string
  options: IOption[]
  placeholder?: string
}>()

const emit = defineEmits(["update:modelValue", "update:selectedId"] as const)

const input = ref<HTMLInputElement | null>(null)
const open = ref(false)
const highlightedIndex = ref(-1)

const query = computed({
  get: () => props.modelValue,
  set: (val: string) => emit("update:modelValue", val),
})

const normalizedOptions = computed(() =>
  props.options.map(option =>
    typeof option === "string" ? { label: option } : option
  )
)

const uniqueOptions = computed(() => {
  const seen = new Map<string, { id?: string | number; label: string }>()
  for (const option of normalizedOptions.value) {
    const label = option.label.trim()
    if (!seen.has(label)) {
      seen.set(label, { ...option, label })
    }
  }
  return Array.from(seen.values())
})

const filteredOptions = computed(() => {
  const queryValue = props.modelValue?.toLowerCase() ?? ""
  if (!queryValue) return uniqueOptions.value

  return uniqueOptions.value.filter(option =>
    option.label.toLowerCase().startsWith(queryValue)
  )
})

const selectOption = (option: { id?: string | number; label: string }) => {
  query.value = option.label
  if (option.id !== undefined) {
    emit("update:selectedId", option.id)
  }
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
      :placeholder="props.placeholder"
      class="form-input"
      @focus="open = true"
      @keydown="onKeyDown"
    />

    <ul v-if="open && filteredOptions.length" class="dropdown">
      <li
        v-for="(option, index) in filteredOptions"
        :key="option.label"
        :class="{ active: index === highlightedIndex }"
        @mousedown.prevent="selectOption(option)"
      >
        {{ option.label }}
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