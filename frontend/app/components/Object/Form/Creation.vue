<script setup lang="ts">
// import type { ICreateObjectDto } from '~/dto/object/create.dto';
// import { EObjectCategories } from '~/enums/object/categories.enum';
// import { EObjectMaterial } from '~/enums/object/material.enum';
// import { EObjectState } from '~/enums/object/state.enum';
// import { createObject } from '~/services/object';

// const router = useRouter();

// const form = reactive<ICreateObjectDto>({
//   images: [],
//   name: '',
//   description: '',
//   category: EObjectCategories.OTHERS,
//   weight: 0,
//   dimensions: '',
//   state: EObjectState.GOOD,
//   material: EObjectMaterial.OTHERS,
// });

// const isSubmitting = ref(false);
// const errorMessage = ref('');

// const categoryOptions = Object.values(EObjectCategories) as EObjectCategories[];
// const stateOfWearOptions = Object.values(EObjectState) as EObjectState[];
// const materialOptions = Object.values(EObjectMaterial) as EObjectMaterial[];

// const resetForm = () => {
//   form.image = '';
//   form.name = '';
//   form.description = '';
//   form.category = EObjectCategories.AUTRES;
//   form.disponibility = EObjectStatus.DISPONIBLE;
//   form.weight = 0;
//   form.dimensions = '';
//   form.stateOfWear = EObjectState.PEU_UTILISE;
//   form.material = EObjectMaterial.AUTRES;
// };

// const handleCancel = async () => {
//   await router.push('/object');
// };

// const handleSubmit = async () => {
//   if (isSubmitting.value) {
//     return;
//   }

//   errorMessage.value = '';
//   isSubmitting.value = true;

//   try {
//     await createObject({
//       ...form,
//       weight: Number(form.weight),
//       disponibility: EObjectStatus.DISPONIBLE,
//     });

//     resetForm();
//     await router.push('/object');
//   } catch (error) {
//     errorMessage.value = 'Impossible de créer l’objet pour le moment. Vérifie les champs et réessaie.';
//   } finally {
//     isSubmitting.value = false;
//   }
// };
</script>

<template>
  <!-- <form class="form-content" @submit.prevent="handleSubmit">
    <div class="form-grid">
      <div class="form-field sm:col-span-2">
        <label for="image" class="form-label">Image</label>
        <input
          id="image"
          v-model.trim="form.image"
          type="url"
          class="form-input"
          placeholder="https://exemple.com/image.jpg"
          required
        />
        <p class="form-help">Pour l’instant, on utilise une URL d’image. Tu pourras brancher un upload ensuite.</p>
      </div>

      <div class="form-field sm:col-span-2">
        <label for="name" class="form-label">Nom</label>
        <input
          id="name"
          v-model.trim="form.name"
          type="text"
          class="form-input"
          placeholder="Nom de l’objet"
          required
        />
      </div>

      <div class="form-field sm:col-span-2">
        <label for="description" class="form-label">Description</label>
        <textarea
          id="description"
          v-model.trim="form.description"
          class="form-textarea"
          placeholder="Décris l’objet, son état, ses particularités..."
          required
        ></textarea>
      </div>

      <div class="form-field">
        <label for="category" class="form-label">Catégorie</label>
        <select id="category" v-model="form.category" class="form-select" required>
          <option v-for="category in categoryOptions" :key="category" :value="category">
            {{ category }}
          </option>
        </select>
      </div>

      <div class="form-field">
        <label for="stateOfWear" class="form-label">État d’usure</label>
        <select id="stateOfWear" v-model="form.stateOfWear" class="form-select" required>
          <option v-for="state in stateOfWearOptions" :key="state" :value="state">
            {{ state }}
          </option>
        </select>
      </div>

      <div class="form-field">
        <label for="material" class="form-label">Matière</label>
        <select id="material" v-model="form.material" class="form-select" required>
          <option v-for="material in materialOptions" :key="material" :value="material">
            {{ material }}
          </option>
        </select>
      </div>

      <div class="form-field">
        <label for="weight" class="form-label">Poids</label>
        <input
          id="weight"
          v-model.number="form.weight"
          type="number"
          min="0"
          step="0.01"
          class="form-input"
          placeholder="0"
          required
        />
      </div>

      <div class="form-field sm:col-span-2">
        <label for="dimensions" class="form-label">Dimensions</label>
        <input
          id="dimensions"
          v-model.trim="form.dimensions"
          type="text"
          class="form-input"
          placeholder="Ex. 30 x 20 x 10 cm"
          required
        />
      </div>
    </div>

    <div class="rounded-xl bg-blue-50 px-4 py-3 text-sm text-blue-900 ring-1 ring-blue-100">
      Disponibilité initiale : <strong>DISPONIBLE</strong>
    </div>

    <p v-if="errorMessage" class="form-error">{{ errorMessage }}</p>

    <ButtonForm :cancelForm="handleCancel" :validateForm="handleSubmit" />
  </form> -->
</template>

<style scoped>
</style>