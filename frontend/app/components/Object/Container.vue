<script setup lang="ts">
import { computed, watch, ref } from "vue";
import type { IUpdateObjectDto } from "~/dto/object/update.dto";
import { EObjectCategories } from "~/enums/object/categories.enum";
import { EObjectMaterial } from "~/enums/object/material.enum";
import { EObjectState } from "~/enums/object/state.enum";
import type { IObject } from "~/types/object";

const props = defineProps<{
  object: IObject;
}>();

const editMode = ref<boolean>(false);
const deleteAsked = ref<boolean>(false);
const indexRenderedImage = ref<number>(0);

const images = computed(() => props.object.images ?? []);
const currentImage = computed(
  () => images.value[indexRenderedImage.value] ?? "",
);

watch(
  () => props.object.images,
  () => {
    indexRenderedImage.value = 0;
  },
);

const handleNextImage = () => {
  if (!images.value.length) return;
  indexRenderedImage.value =
    (indexRenderedImage.value + 1) % images.value.length;
};

const handlePrevImage = () => {
  if (!images.value.length) return;
  indexRenderedImage.value =
    (indexRenderedImage.value - 1 + images.value.length) % images.value.length;
};

const emit = defineEmits(["handleSubmitUpdate", "handleDelete"]);

const handleSubmitUpdate = (updateObject: Omit<IUpdateObjectDto, "id">) => {
  emit("handleSubmitUpdate", { ...updateObject, id: props.object.id });
  editMode.value = false;
};

const handleDelete = () => {
  emit("handleDelete");
  deleteAsked.value = false;
};

const handleCancelEdit = () => {
  editMode.value = false;
};
</script>
<template>
  <div v-if="deleteAsked">
    <ObjectFormDeletion
      @cancelEdit="() => (deleteAsked = !deleteAsked)"
      @handleDelete="handleDelete"
    />
  </div>

  <div class="app-page">
    <div class="app-container space-y-6">

      <!-- Header -->
      <header class="object-header">
        <h1 class="object-title">{{ object.name }}</h1>
        <ButtonOptions
          :actions="[
            { function: () => { editMode = true }, label: 'Modifier', svg: '/icons/edit.svg' },
            { function: () => { deleteAsked = true }, label: 'Supprimer', svg: '/icons/delete.svg' },
          ]"
        />
      </header>

      <main class="object-main">

        <!-- Carousel -->
        <div class="object-media">
          <div class="image-carousel" v-if="images.length">
            <button type="button" class="nav" @click="handlePrevImage">←</button>
            <img :src="currentImage" alt="Object" class="carousel-img" />
            <button type="button" class="nav" @click="handleNextImage">→</button>
          </div>
          <div class="image-empty" v-else>
            <img src="/objectImage.png" alt="Object" class="carousel-img" />
          </div>
          <p class="image-counter" v-if="images.length">
            {{ indexRenderedImage + 1 }} / {{ images.length }}
          </p>
        </div>

        <!-- Formulaire de modification -->
        <ObjectFormModification
          v-if="editMode"
          :object="object"
          @handleSubmitUpdate="handleSubmitUpdate"
          @cancelEdit="handleCancelEdit"
        />

        <!-- Infos en lecture -->
        <div v-else class="object-info-section">

          <div class="info-block">
            <p class="block-title">Informations</p>
            <div class="info-row">
              <span class="info-label">État</span>
              <span v-if="!object.stateOfWear" class="info-empty">Non renseigné</span>
              <strong v-else class="info-value">{{ EObjectState[object.stateOfWear] }}</strong>
            </div>
            <div class="info-row">
              <span class="info-label">Poids</span>
              <span v-if="!object.weight" class="info-empty">Non renseigné</span>
              <strong v-else class="info-value">{{ object.weight }} kg</strong>
            </div>
            <div class="info-row">
              <span class="info-label">Matière</span>
              <span v-if="!object.material" class="info-empty">Non renseignée</span>
              <strong v-else class="info-value">{{ EObjectMaterial[object.material] }}</strong>
            </div>
            <div class="info-row">
              <span class="info-label">Dimensions</span>
              <span v-if="!object.dimensions" class="info-empty">Non renseignées</span>
              <strong v-else class="info-value">{{ object.dimensions }}</strong>
            </div>
            <div class="info-row">
              <span class="info-label">Catégorie</span>
              <span v-if="!object.category" class="info-empty">Non renseignée</span>
              <strong v-else class="info-value">{{ EObjectCategories[object.category] }}</strong>
            </div>
          </div>

          <div class="info-block">
            <p class="block-title">Description</p>
            <p v-if="!object.description" class="info-empty">Aucune description pour {{ object.name }}.</p>
            <p v-else class="info-description">{{ object.description }}</p>
          </div>

        </div>
      </main>
    </div>
  </div>
</template>

<style scoped>
/* ── Layout ── */
:deep(.app-page) { background-color: var(--color-background) !important; }

.object-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
}

.object-title {
  font-size: 1.75rem;
  font-weight: 700;
  letter-spacing: -0.02em;
  color: var(--color-title);
  margin: 0;
}

.object-main {
  display: grid;
  gap: 1.5rem;
}

/* ── Carousel ── */
.object-media {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.image-carousel {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  width: 100%;
}

.carousel-img {
  flex: 1;
  width: 100%;
  max-height: 380px;
  object-fit: contain;
  border-radius: var(--border-radius);
  background-color: var(--color-surface);
  border: 1px solid #d4ccc0;
}

.nav {
  flex-shrink: 0;
  background-color: var(--color-surface);
  color: var(--color-title);
  border: 1px solid #c9c0ae;
  font-size: 1.2rem;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav:hover {
  background-color: #d9d2c5;
  box-shadow: 0 2px 6px rgba(0,0,0,0.08);
}

.image-counter {
  font-size: 0.8rem;
  color: var(--color-text);
  opacity: 0.5;
  margin: 0;
}

/* ── Info blocks ── */
.object-info-section {
  display: grid;
  gap: 1rem;
}

.info-block {
  background-color: var(--color-surface);
  border: 1px solid #d4ccc0;
  border-radius: var(--border-radius);
  padding: 1.25rem 1.5rem;
}

.block-title {
  font-weight: 700;
  font-size: 0.85rem;
  text-transform: uppercase;
  letter-spacing: 0.08em;
  color: var(--color-accent);
  margin: 0 0 0.875rem;
}

.info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  padding: 0.5rem 0;
  border-bottom: 1px solid #e2d9cd;
}

.info-row:last-child { border-bottom: none; }

.info-label {
  font-size: 0.875rem;
  color: var(--color-text);
  opacity: 0.7;
}

.info-value {
  font-size: 0.875rem;
  font-weight: 600;
  color: var(--color-title);
}

.info-empty {
  font-size: 0.8rem;
  color: var(--color-text);
  opacity: 0.4;
  font-style: italic;
}

.info-description {
  font-size: 0.875rem;
  color: var(--color-text);
  line-height: 1.6;
  margin: 0;
}
</style>
