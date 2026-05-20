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
    <div class="flex gap-4">
        <button v-for="action in actions" :key="action.label" @click="action.function" class="flex items-center gap-2 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 transition-colors">
            <img :src="action.svg" alt="" class="w-4 h-4">
            {{ action.label }}
        </button>
    </div>
</template>


<style>

</style>