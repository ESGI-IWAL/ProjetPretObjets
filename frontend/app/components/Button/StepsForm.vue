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
    <div v-if="!firstStep">
        <button @click="previousStep" type="button" :disabled="firstStep"> Précedent </button>
    </div>
    <div v-if="finalStep">
        <button @click="validateForm" type="submit"> Valider </button>
    </div>
    <div v-else>
        <button @click="nextStep" type="button" :disabled="!isEntryValid"> Suivant </button>
    </div>
</template>


<style>

</style>