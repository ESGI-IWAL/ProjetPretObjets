<script setup lang="ts">
import { reactive, ref, watch } from "vue";

import { objectStateOptions } from "../../../enums/object/state.enum";
import { objectMaterialOptions } from "../../../enums/object/material.enum";
import { objectCategoryOptions } from "../../../enums/object/categories.enum";
import type { IObject } from "~/types/object";
import type { IUpdateObjectDto } from "~/dto/object/update.dto";


const props = defineProps<{
  object: IObject;
}>();

const emit = defineEmits(["handleSubmitUpdate", "cancelEdit"]);

const imagesText = ref<string>("");
const newImageUrl = ref<string>("");

const dimensions = ref({
  longueur: "",
  largeur: "",
  epaisseur: "",
});

const splitDimensions = (dimensionsOneString: string) => {
  const [l, la, e] = dimensionsOneString.split("x");
  dimensions.value = {
    longueur: l ?? "",
    largeur: la ?? "",
    epaisseur: e ?? "",
  };
};

// Recompose la string à chaque changement des dimensions
watch(
  dimensions,
  (val) => {
    editForm.dimensions = `${val.longueur}x${val.largeur}x${val.epaisseur}`;
  },
  { deep: true },
);

const editForm = reactive<Omit<IUpdateObjectDto, "id">>({
  images: [...(props.object.images ?? [])],
  name: props.object.name,
  description: props.object.description,
  category: props.object.category,
  weight: props.object.weight,
  dimensions: props.object.dimensions,
  state: props.object.stateOfWear,
  material: props.object.material,
});

const resetForm = () => {
  editForm.images = [...(props.object.images ?? [])];
  editForm.name = props.object.name;
  editForm.description = props.object.description;
  editForm.category = props.object.category;
  editForm.weight = props.object.weight;
  editForm.dimensions = props.object.dimensions;
  editForm.state = props.object.stateOfWear;
  editForm.material = props.object.material;
  imagesText.value = editForm.images?.join("\n") ?? "";

  props.object.dimensions && splitDimensions(props.object.dimensions);
};

watch(
    () => props.object,
    () => {
      resetForm();
    },
    { immediate: true },
);

const handleSubmit = () => {
  // On récupère directement le tableau d'images mis à jour par le PhotoPicker
  const finalImages = editForm.images && editForm.images.length > 0
      ? editForm.images
      : [];

  emit("handleSubmitUpdate", {
    images: finalImages, //  On envoie les vraies images !
    name: editForm.name,
    description: editForm.description,
    category: editForm.category,
    weight: editForm.weight,
    dimensions: editForm.dimensions,
    state: editForm.state,
    material: editForm.material,
  });
};

const handleCancel = () => {
  resetForm();
  emit("cancelEdit");
};

</script>

<template>
  <form class="edit-form" @submit.prevent="handleSubmit">

    <div class="form-field">
      <label class="form-label" for="name">Nom</label>
      <input id="name" v-model.trim="editForm.name" type="text" required class="form-input" />
    </div>

    <div class="form-field">
      <label class="form-label" for="description">Description</label>
      <textarea id="description" v-model.trim="editForm.description" rows="3" class="form-textarea"></textarea>
    </div>

    <div class="form-row">
      <div class="form-field">
        <label class="form-label" for="state">État</label>
        <select id="state" v-model="editForm.state" required class="form-select">
          <option v-for="option in objectStateOptions" :key="option.value" :value="option.value">{{ option.label }}</option>
        </select>
      </div>
      <div class="form-field">
        <label class="form-label" for="material">Matière</label>
        <select id="material" v-model="editForm.material" required class="form-select">
          <option v-for="option in objectMaterialOptions" :key="option.value" :value="option.value">{{ option.label }}</option>
        </select>
      </div>
    </div>

    <div class="form-row">
      <div class="form-field">
        <label class="form-label" for="weight">Poids (kg)</label>
        <input id="weight" v-model.number="editForm.weight" type="number" min="0" step="0.01" class="form-input" />
      </div>
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
    </div>

    <div class="form-field">
      <label class="form-label" for="category">Catégorie</label>
      <select id="category" v-model="editForm.category" class="form-select">
        <option v-for="option in objectCategoryOptions" :key="option.value" :value="option.value">{{ option.label }}</option>
      </select>
    </div>

    <div class="form-field">
      <PhotoPicker
        :max-photos="8"
        v-model="editForm.images"
        @update:modelValue="editForm.images = $event"
        :object-name="editForm.name"
      />
    </div>

    <div class="form-actions">
      <button type="button" class="btn-cancel" @click="handleCancel">Annuler</button>
      <button type="submit" class="btn-save">Enregistrer</button>
    </div>
  </form>
</template>

<style scoped>
.edit-form {
  margin-top: 1.5rem;
  display: grid;
  gap: 1.1rem;
}
.form-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1rem;
}
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
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
  padding-top: 0.5rem;
}
.btn-save {
  background-color: var(--color-primary);
  color: var(--color-background);
  border: none;
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  cursor: pointer;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
}
.btn-save:hover {
  background-color: var(--color-secondary);
  box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}
.btn-cancel {
  background-color: var(--color-surface);
  color: var(--color-title);
  border: 1px solid #c9c0ae;
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  cursor: pointer;
  transition: background-color 0.2s ease;
}
.btn-cancel:hover {
  background-color: #d9d2c5;
}
</style>
