<script setup lang="ts">
import type { IOption } from '~/components/AutoComplete.vue';
import useToaster from '~/composables/useToaster';
import type { ISearchObjectWithDatesDto } from '~/dto/object/search.dto';
import { EObjectCategories, objectCategoryOptions } from '~/enums/object/categories.enum';
import { EObjectState, objectStateOptions } from '~/enums/object/state.enum';
import { EObjectMaterial, objectMaterialOptions } from '~/enums/object/material.enum';
import { getObjects } from '~/services/object';

defineProps<{
  optionDisponibilityDate: boolean
}>()
const emit = defineEmits(["handleSearch"]);
const toaster = useToaster();
const form = reactive<ISearchObjectWithDatesDto>({
  name: "",
  category: null,
  disponibilityDate: null,
  material: null,
  state: null,
});

const objectsIOption = ref<IOption[] | null>(null);


const handleSubmit = () => {
  const dto: ISearchObjectWithDatesDto = {
    name: form.name,
    category: form.category ? (form.category as EObjectCategories) : null,
    state: form.state ? (form.state as EObjectState) : null,
    material: form.material ? (form.material as EObjectMaterial) : null,
    disponibilityDate: form.disponibilityDate || null,
  };

  emit("handleSearch", dto);
};

const handleResetForm = () => {
  form.name = "";
  form.category = null;
  form.disponibilityDate = null;
  form.material = null;
  form.state = null;
  emit("handleSearch", form)
}

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
         <select id="category" v-model="form.category" class="form-select">
          <option value="null">Tous</option>
          <option v-for="option in objectCategoryOptions" :key="option.value" :value="option.value">
            {{ option.label }}
          </option>
        </select>
      </div>

      
      <div class="form-field">
        <label class="form-label" for="state">État</label>
        <select id="state" v-model="form.state" class="form-select">
          <option value="null">Tous</option>
          <option v-for="option in objectStateOptions" :key="option.value" :value="option.value">
            {{ option.label }}
          </option>
        </select>
      </div>
      
      <div class="form-field">
        <label class="form-label" for="material">Matériau</label>
        <select id="material" v-model="form.material" class="form-select">
          <option value="null">Tous</option>
          <option v-for="option in objectMaterialOptions" :key="option.value" :value="option.value">
            {{ option.label }}
          </option>
        </select>
      </div>
      
      <div class="form-field" v-if="optionDisponibilityDate">
        <label class="form-label" for="disponibilityDate">Date de disponibilité</label>
        <input id="disponibilityDate" type="date" v-model="form.disponibilityDate" class="form-input" />
      </div>
    </div>

    <div class="flex justify-end">
      <ButtonSearch :reset-form="() => handleResetForm()"/>
    </div>
  </form>
</template>