<script setup lang="ts">
defineProps<{
  actions: {
    function: () => void,
    label: string,
    svg?: string
  }[]
}>()
</script>
<template>
  <!-- container aligns buttons to the right (top-right when parent is full width) -->
  <div class="w-full flex justify-end gap-3">
    <button
      v-for="action in actions"
      :key="action.label"
      type="button"
      @click="() => action.function()"
      :title="action.label"
      :aria-label="action.label"
      :class="[
        'inline-flex items-center justify-center w-10 h-10 transition-shadow shadow-sm',
        action.label && action.label.toLowerCase().includes('supprimer')
          ? 'bg-red-600 text-white hover:bg-red-700 rounded-full'
          : 'bg-blue-600 text-white hover:bg-blue-700 rounded-full'
      ]"
    >
      <!-- show provided svg if any, else fallback to inline icons by label -->
      <template v-if="action.svg">
        <img :src="action.svg" alt="" class="w-5 h-5" />
      </template>
      <template v-else>
        <svg v-if="action.label && action.label.toLowerCase().includes('modifier')" xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M12 20h9"/><path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M16.5 3.5a2.121 2.121 0 013 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
        <svg v-else xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M3 6h18"/><path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M8 6v14a2 2 0 002 2h4a2 2 0 002-2V6"/><path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M10 11v6m4-6v6"/></svg>
      </template>
      <!-- keep the text for screen-readers only -->
      <span class="sr-only">{{ action.label }}</span>
    </button>
  </div>
</template>


<style>

</style>