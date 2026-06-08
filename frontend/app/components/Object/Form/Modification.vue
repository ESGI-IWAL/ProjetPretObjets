<script setup lang="ts">
import { reactive, ref, watch } from "vue";
import type { IObject } from "~/types/object";
import type { IUpdateObjectDto } from "~/dto/object/update.dto";
import { EObjectCategories, objectCategoryOptions } from "~/enums/object/categories.enum";
import { EObjectMaterial } from "~/enums/object/material.enum";
import { EObjectState } from "~/enums/object/state.enum";

const props = defineProps<{
  object: IObject;
}>();

const emit = defineEmits(["handleSubmitUpdate", "cancelEdit"]);

const imagesText = ref<string>("");

const editForm = reactive<Omit<IUpdateObjectDto, "id">>({
  images: props.object.images?.slice() ?? [],
  name: props.object.name,
  description: props.object.description,
  category: props.object.category as EObjectCategories,
  weight: props.object.weight,
  dimensions: props.object.dimensions,
  state: props.object.state as EObjectState,
  material: props.object.material as EObjectMaterial,
});

const resetForm = () => {
  editForm.images = props.object.images?.slice() ?? [];
  editForm.name = props.object.name;
  editForm.description = props.object.description;
  editForm.category = props.object.category as EObjectCategories;
  editForm.weight = props.object.weight;
  editForm.dimensions = props.object.dimensions;
  editForm.state = props.object.state as EObjectState;
  editForm.material = props.object.material as EObjectMaterial;
  imagesText.value = editForm.images?.join("\n") ?? "";
};

watch(
  () => props.object,
  () => {
    resetForm();
  },
  { immediate: true },
);

const handleSubmit = () => {
  const parsedImages = imagesText.value
    .split(/\r?\n/)
    .map((item) => item.trim())
    .filter(Boolean);

  emit("handleSubmitUpdate", {
    images: parsedImages.length ? parsedImages : undefined,
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
    <div class="form-group">
      <label for="name">Nom</label>
      <input id="name" v-model.trim="editForm.name" type="text" required />
    </div>

    <div class="form-group">
      <label for="description">Description</label>
      <textarea id="description" v-model.trim="editForm.description" rows="3"></textarea>
    </div>

    <div class="form-row">
      <div class="form-group">
        <label for="state">État</label>
        <select id="state" v-model="editForm.state" required>
          <option v-for="state in Object.values(EObjectState)" :key="state" :value="state">
            {{ state }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="material">Matière</label>
        <select id="material" v-model="editForm.material" required>
          <option v-for="material in Object.values(EObjectMaterial)" :key="material" :value="material">
            {{ material }}
          </option>
        </select>
      </div>
    </div>

    <div class="form-row">
      <div class="form-group">
        <label for="weight">Poids (kg)</label>
        <input id="weight" v-model.number="editForm.weight" type="number" min="0" step="0.01" />
      </div>
      <div class="form-group">
        <label for="dimensions">Dimensions</label>
        <input id="dimensions" v-model.trim="editForm.dimensions" type="text" />
      </div>
    </div>

    <div class="form-group">
      <label for="category">Catégorie</label>
      <select id="category" v-model="editForm.category">
        <option v-for="categorie in Object.values(EObjectCategories)" :key="categorie" :value="categorie">
          {{ categorie }}
        </option>
      </select>
    </div>

    <div class="form-group">
      <label for="images">Images (1 URL par ligne)</label>
      <textarea
        id="images"
        v-model="imagesText"
        rows="4"
        placeholder="https://..."
      ></textarea>
    </div>

    <div class="form-actions">
      <button type="button" class="button-secondary" @click="handleCancel">Annuler</button>
      <button type="submit" class="button-primary">Enregistrer</button>
    </div>
  </form>
</template>

<style scoped>
.edit-form {
  margin-top: 1.5rem;
  display: grid;
  gap: 1rem;
}
.form-group {
  display: grid;
  gap: 0.5rem;
}
.form-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1rem;
}
.form-row .form-group {
  margin: 0;
}
.form-group label {
  font-weight: 600;
}
.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  background: #fff;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.75rem;
}
.button-primary,
.button-secondary {
  border: none;
  padding: 0.75rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
}
.button-primary {
  background: #2563eb;
  color: white;
}
.button-secondary {
  background: #f3f4f6;
  color: #111827;
}
</style>