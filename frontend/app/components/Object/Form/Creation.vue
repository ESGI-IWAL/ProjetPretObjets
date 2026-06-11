<script setup lang="ts">
import { objectCategoryOptions } from "~/enums/object/categories.enum";
import { EObjectState, objectStateOptions } from "~/enums/object/state.enum";
import {
  objectMaterialOptions,
} from "~/enums/object/material.enum";
import type { ICreateObjectDto } from "~/dto/object/create.dto";
import { createObject } from "~/services/object";
interface IStep {
  id: number;
  title: string;
  description: string;
}
const toaster = useToaster();
const initalValues: ICreateObjectDto = {
  name: "",
  images: [],
  dimensions: "",
  category: null,
  material: null,
  state: null,
  weight: 0,
  description: "",
};

const form = reactive<ICreateObjectDto>(initalValues);

const resetForm = () => Object.assign(form, initalValues);

const dimensions = ref({
  longueur: "",
  largeur: "",
  epaisseur: "",
});

watch(
  dimensions,
  (val) => {
    form.dimensions = `${val.longueur}x${val.largeur}x${val.epaisseur}`;
  },
  { deep: true },
);

const isEntryValid = computed(() => {
  switch (currentStep.value) {
    case 1:
      return !!form.name;
    case 3:
      return !!form.material && !!form.state && !!form.category;
    case 4:
      return !!form.weight && !!form.dimensions && !!form.description;
    default:
      return true;
  }
});

const currentStep = ref<number>(1);

const steps = ref<IStep[]>([
  {
    id: 1,
    title: "Nommage de l'objet",
    description: "Indiquez le nom de l'objet",
  },
  {
    id: 2,
    title: "Sélection des photos",
    description: "Insérer les photos de l'objet",
  },
  {
    id: 3,
    title: "Donnez les spécifications",
    description: "Indiquez la catégorie, la matière et l'état de votre objet.",
  },
  {
    id: 4,
    title: "Donnez les caractéristiques",
    description:
      "Indiquez le poids, les dimensions et une rapide description de l'objet",
  },
]);

const nextStep = () => {
  if (currentStep.value < steps.value.length) {
    currentStep.value++;
  }
};
const previousStep = () => {
  if (currentStep.value > 1) {
    currentStep.value--;
  } else {
    resetForm();
    navigateTo("/objects");
  }
};

const handleValidateForm = async () => {
  try {
    if (form.images.length === 0) {
      form.images = ["/objectImage.png"];
    }
    await createObject(form);
    resetForm();
    navigateTo("/objects");
    toaster.show("L'objet a bien été créé");
  } catch {
    resetForm();
    navigateTo("/objects");
    toaster.show("Erreur lors de la création de votre objet", "error", 5000);
  }
};
</script>

<template>
  <form
    class="form-card form-content"
    @submit.prevent="handleValidateForm"
    @keydown.enter.prevent="nextStep"
  >
    <div class="form-header">
      <h2 class="form-title" style="color: var(--color-title)">{{ steps[currentStep - 1]?.title }}</h2>
      <p class="form-description" style="color: var(--color-text); opacity: 0.7">{{ steps[currentStep - 1]?.description }}</p>
    </div>
    <div>
      <div v-if="currentStep === 1" class="form-field">
        <label class="form-label" for="name">Nom de l'objet</label>
        <input id="name" v-model.trim="form.name" type="text" required class="form-input" placeholder="Ex. Perceuse Bosch…" />
      </div>

      <div v-if="currentStep === 2" class="form-field">
        <PhotoPicker
          :max-photos="8"
          v-model="form.images"
          @update:modelValue="form.images = $event"
          :objectName="form.name"
        />
      </div>

      <div v-if="currentStep === 3" class="form-grid">
        <div class="form-field">
          <label class="form-label" for="material">Matière</label>
          <select id="material" v-model="form.material" required class="form-select">
            <option v-for="option in objectMaterialOptions" :key="option.value" :value="option.value">{{ option.label }}</option>
          </select>
        </div>
        <div class="form-field">
          <label class="form-label" for="state">État</label>
          <select id="state" v-model="form.state" required class="form-select">
            <option v-for="option in objectStateOptions" :key="option.value" :value="option.value">{{ option.label }}</option>
          </select>
        </div>
        <div class="form-field">
          <label class="form-label" for="category">Catégorie</label>
          <select id="category" v-model="form.category" class="form-select">
            <option v-for="option in objectCategoryOptions" :key="option.value" :value="option.value">{{ option.label }}</option>
          </select>
        </div>
      </div>

      <div v-if="currentStep === 4" class="form-grid">
        <div class="form-field">
          <label class="form-label">Dimensions (cm)</label>
          <div class="dimensions-inputs">
            <input v-model.number="dimensions.longueur" type="number" min="0" placeholder="L" title="Longueur" class="form-input" />
            <span class="separator">×</span>
            <input v-model.number="dimensions.largeur" type="number" min="0" placeholder="l" title="Largeur" class="form-input" />
            <span class="separator">×</span>
            <input v-model.number="dimensions.epaisseur" type="number" min="0" placeholder="é" title="Épaisseur" class="form-input" />
          </div>
        </div>
        <div class="form-field">
          <label class="form-label" for="weight">Poids (kg)</label>
          <input id="weight" v-model.number="form.weight" type="number" min="0" step="0.01" class="form-input" />
        </div>
        <div class="form-field">
          <label class="form-label" for="description">Description</label>
          <textarea id="description" v-model.trim="form.description" rows="3" class="form-textarea"></textarea>
        </div>
      </div>
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
.dimensions-inputs {
  display: flex;
  align-items: center;
  gap: 8px;
}
.dimensions-inputs .form-input {
  text-align: center;
  flex: 1;
}
.separator {
  font-weight: 700;
  color: var(--color-title);
  opacity: 0.5;
  flex-shrink: 0;
}
</style>
