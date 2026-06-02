<script setup lang="ts">
import type { IOption } from '~/components/AutoComplete.vue';
import useToaster from '~/composables/useToaster';
import type { ISearchObjectDto } from '~/dto/object/search.dto';
import { EObjectCategories } from '~/enums/object/categories.enum';
import { EObjectState } from '~/enums/object/state.enum';
import { EObjectMaterial } from '~/enums/object/material.enum';
import { getObjects } from '~/services/object';

const emit = defineEmits(["search"]);
const toaster = useToaster();
const form = reactive({
  name: "",
  category: "",
  disponibilityEndDate: "",
  disponibilityStartDate: "",
  material: "",
  state: "",
});

const objectsIOption = ref<IOption[] | null>(null);
const objectCategoryOptions = Object.values(EObjectCategories);
const objectStateOptions = Object.entries(EObjectState).map(([value, label]) => ({ value, label }));
const objectMaterialOptions = Object.entries(EObjectMaterial).map(([value, label]) => ({ value, label }));

const handleSubmit = () => {
  const dto: ISearchObjectDto = {
    name: form.name,
    category: form.category ? (form.category as EObjectCategories) : null,
    state: form.state ? (form.state as EObjectState) : null,
    material: form.material ? (form.material as EObjectMaterial) : null,
    disponibilityStartDate: form.disponibilityStartDate
      ? new Date(form.disponibilityStartDate)
      : null,
    disponibilityEndDate: form.disponibilityEndDate
      ? new Date(form.disponibilityEndDate)
      : null,
  };

  emit("search", dto);
};

onMounted(async () => {
  try {
    const objects = await getObjects();
    objectsIOption.value = objects.map((objet) => ({ label: objet.name }));
  } catch {
    objectsIOption.value = [];
    toaster.show("Erreur lors de la recherche des noms des objets", "error", 5000);
  }
});
</script>
<template>
  <form class="form-content" @submit.prevent="handleSubmit">
    <div class="form-grid">
      <div class="form-field">
        <label class="form-label" for="objectName">Objet</label>
        <AutoComplete
          id="objectName"
          v-model:model-value="form.name"
          :options="objectsIOption ?? []"
          :placeholder="'Nom de l\'objet'"
        />
      </div>

      <div class="form-field">
        <label class="form-label" for="category">Catégorie</label>
        <AutoComplete
          id="category"
          v-model:model-value="form.category"
          :options="objectCategoryOptions"
          :placeholder="'Catégorie'"
        />
      </div>

      <div class="form-field">
        <label class="form-label" for="startAt">Début</label>
        <input id="startAt" type="date" v-model="form.disponibilityStartDate" class="form-input" />
      </div>

      <div class="form-field">
        <label class="form-label" for="endAt">Fin</label>
        <input id="endAt" type="date" v-model="form.disponibilityEndDate" class="form-input" />
      </div>

      <div class="form-field">
        <label class="form-label" for="state">État</label>
        <select id="state" v-model="form.state" class="form-select">
          <option value="">Tous</option>
          <option v-for="option in objectStateOptions" :key="option.value" :value="option.value">
            {{ option.label }}
          </option>
        </select>
      </div>

      <div class="form-field">
        <label class="form-label" for="material">Matériau</label>
        <select id="material" v-model="form.material" class="form-select">
          <option value="">Tous</option>
          <option v-for="option in objectMaterialOptions" :key="option.value" :value="option.value">
            {{ option.label }}
          </option>
        </select>
      </div>
    </div>

    <div class="flex justify-end">
      <ButtonSearch />
    </div>
  </form>
</template>