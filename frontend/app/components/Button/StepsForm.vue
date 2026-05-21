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
        <button @click="previousStep" type="button" class="form-button-secondary"> {{ firstStep ? 'Annuler' : 'Précédent' }}</button>
        <button v-if="finalStep" @click="validateForm" type="button" class="form-button-primary">Valider</button>
        <button v-else @click="nextStep" type="button" :disabled="!isEntryValid" class="form-button-primary">Suivant</button>
    </div>
</template>


<style>

</style>