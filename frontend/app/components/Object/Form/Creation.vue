<script setup lang="ts">
interface IStep {
  id: number;
  title: string;
  description: string;
}

const currentStep = ref<number>(1);
const steps = ref<IStep[]>([
  {
    id: 1,
    title: "Sélection de l'utilisateur",
    description: "Choisissez l'utilisateur qui emprunte",
  },
  {
    id: 2,
    title: "Sélection de l'objet",
    description: "Choisissez l'objet à emprunter",
  },
  {
    id: 3,
    title: "Dates de prêt",
    description: "Indiquez les dates de début et de fin du prêt.",
  },
]);

</script>

<template>
  <form class="form-card form-content">
    <div class="form-header">
      <h2 class="form-title">{{ steps[currentStep - 1]?.title }}</h2>
      <p class="form-description">{{ steps[currentStep - 1]?.description }}</p>
    </div>

    <div>
      <div v-if="currentStep === 1" class="form-field">

      </div>

      <div v-if="currentStep === 2 && objects" class="form-field">

    </div>

    <div v-if="currentStep === 3" class="form-grid">

    </div>

    <div class="form-actions">
      <ButtonStepsForm
        :nextStep="nextStep"
        :previousStep="previousStep"
        :validateForm="handleValidateForm"
        :finalStep="currentStep === steps.length"
        :firstStep="currentStep === 1"
        :isEntryValid="isEntryValid"
      />
    </div>
  </form>
</template>

<style scoped>
</style>