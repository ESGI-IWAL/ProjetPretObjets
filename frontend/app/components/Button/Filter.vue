<script setup lang="ts">

    defineProps<{
        openFilter: () => void,
    }>()

</script>

<template>
    <button @click="() => openFilter()" class="form-button-primary">
        Filtre
    </button>
</template>