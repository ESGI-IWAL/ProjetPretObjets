<script setup lang="ts">
    defineProps<{
        nextStep: () => void,
        previousStep: () => void,
        validateForm: () => void,
        finalStep: boolean,
        firstStep: boolean,
        isEntryValid: boolean
    }>()
</script>
<template>
    <div class="form-actions">
        <button v-if="!firstStep" @click="previousStep" type="button" :disabled="firstStep" class="form-button-secondary">Précédent</button>
        <button v-if="finalStep" @click="validateForm" type="button" class="form-button-primary">Valider</button>
        <button v-else @click="nextStep" type="button" :disabled="!isEntryValid" class="form-button-primary">Suivant</button>
    </div>
</template>


<style>

</style>