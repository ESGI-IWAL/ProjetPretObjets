<script setup lang="ts">
    defineProps<{
        cancelForm: () => void,
        validateForm: () => void,
    }>()
</script>

<template>
    <button @click="() => cancelForm()" type="button">Annuler</button>
    <button @click="() => validateForm()" type="submit">Valider</button>
</template>

<style scoped>
</style>