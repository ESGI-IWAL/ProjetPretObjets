<script setup lang="ts">
    defineProps({
        navigationCreation: {
            type: Function,
            required: true
        },
        label: {
            type: String,
            required: true
        }
    })
</script>
<template>
    <button @click="() => navigationCreation()" class="form-button-primary">
        {{ label }}
    </button>
</template>

<style scoped>
</style>