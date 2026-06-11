<script setup lang="ts">

import type { IObject } from '~/types/object';

const props = defineProps<{
    object: IObject
    selected: boolean
}>()

const emit = defineEmits(["handleSelection"])

const handleSelection = () => {
    emit('handleSelection', props.object.id)
}

</script> 

<template>
    <div 
        @click="handleSelection" 
        :class="[
            'relative cursor-pointer rounded-xl border-2 transition-all duration-200 overflow-hidden',
            props.selected 
                ? 'shadow-md' 
                : 'hover:shadow-md'
        ]"
        :style="props.selected
            ? 'border-color: var(--color-primary); background-color: #dff0de;'
            : 'border-color: #c9c0ae; background-color: var(--color-surface);'"
    >
        <!-- Checkbox indicator -->
        <div class="absolute top-3 right-3 z-10">
            <div 
                class="w-6 h-6 rounded-full border-2 flex items-center justify-center transition-all duration-200"
                :style="props.selected
                    ? 'background-color: var(--color-primary); border-color: var(--color-primary);'
                    : 'border-color: #c9c0ae; background-color: var(--color-surface);'"
            >
                <svg v-if="props.selected" class="w-4 h-4" style="color: var(--color-background)" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7" />
                </svg>
            </div>
        </div>

        <!-- Card content -->
        <div class="p-4">
            <img 
                :src="object?.images?.[0] ?? '/objectImage.png'" 
                alt="Objet"
                class="w-full h-48 object-cover rounded-lg mb-3"
            />
            <p class="font-semibold truncate" style="color: var(--color-title)">{{ object.name }}</p>
        </div>
    </div>
</template>

<style scoped>
/* Smooth transitions for interactive elements */
div {
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 150ms;
}
</style>