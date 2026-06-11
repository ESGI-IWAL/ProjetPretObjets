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
    <div class="form-actions" >
        <button @click="previousStep" type="button" class="form-button-secondary"> {{ firstStep ? 'Annuler' : 'Précédent' }}</button>
        <button v-if="finalStep" @click="validateForm" type="button" :disabled="!isEntryValid" class="form-button-primary">Valider</button>
        <button v-else @click="nextStep"      type="button" :disabled="!isEntryValid" class="form-button-primary">Suivant</button>
    </div>
</template>


<style scoped>
.form-button-primary {
  background-color: var(--color-primary);
  color: var(--color-background);
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
  border: none;
}
.form-button-primary:hover:not(:disabled) {
  background-color: var(--color-secondary);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
.form-button-primary:disabled {
  opacity: 0.45;
  cursor: not-allowed;
}
.form-button-secondary {
  background-color: #FFFFFF;
  color: var(--color-title);
  border: 1px solid #c9c0ae;
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
}
.form-button-secondary:hover {
  background-color: #d9d2c5;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
}
</style>