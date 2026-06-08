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
            'relative cursor-pointer rounded-lg border-2 transition-all duration-200 overflow-hidden hover:shadow-lg',
            props.selected 
                ? 'border-green-500 bg-green-50 shadow-md' 
                : 'border-gray-200 bg-white hover:border-gray-300'
        ]"
    >
        <!-- Checkbox indicator -->
        <div class="absolute top-3 right-3 z-10">
            <div 
                :class="[
                    'w-6 h-6 rounded-full border-2 flex items-center justify-center transition-all duration-200',
                    props.selected 
                        ? 'bg-green-500 border-green-500' 
                        : 'border-gray-300 bg-white hover:border-gray-400'
                ]"
            >
                <svg v-if="props.selected" class="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M5 13l4 4L19 7" />
                </svg>
            </div>
        </div>

        <!-- Card content -->
        <div class="p-4">
            <img 
                :src="object?.images?.[0] ?? '/objectImage.png'" 
                alt="Objet"
                class="w-full h-48 object-cover rounded-md mb-3"
            />
            <p class="font-semibold text-gray-800 truncate">{{ object.name }}</p>
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