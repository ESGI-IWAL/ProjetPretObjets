<script setup lang="ts">
    
    defineProps<{
        resetForm: () => void
    }
    >()
</script>
<template>
    <button type="reset" @click="resetForm()">
        Annuler
    </button>
    <button type="submit" class="form-button-primary">
        Rechercher
    </button>
</template>

<style scoped>
</style>