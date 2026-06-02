<script setup lang="ts">
    defineProps<{
        cancelForm: () => void,
        validateForm: () => void,
    }>()
</script>

<template>
    <div class="form-actions">
        <button @click="() => cancelForm()" type="button" class="form-button-secondary">Annuler</button>
        <button @click="() => validateForm()" type="button" class="form-button-primary">Valider</button>
    </div>
</template>

<style scoped>
</style>