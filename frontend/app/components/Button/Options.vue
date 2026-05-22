<script setup lang="ts">
    defineProps<{
        actions: {
            function: () => void, 
            label: string,
            svg: string
        }[]
    }>()
</script>
<template>
  <div class="flex flex-wrap gap-3">
    <button
        v-for="action in actions"
        :key="action.label"
        type="button"
        @click="action.function"
        :class="[
        'inline-flex items-center gap-2 rounded-xl px-4 py-2.5 text-sm font-semibold transition shadow-sm',
        action.label === 'Supprimer'
          ? 'bg-red-600 text-white hover:bg-red-700 hover:shadow-md'
          : 'bg-blue-600 text-white hover:bg-blue-700 hover:shadow-md'
      ]"
    >
      <span>{{ action.label }}</span>
    </button>
  </div>
</template>


<style>

</style>