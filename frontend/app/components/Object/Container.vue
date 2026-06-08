<script setup lang="ts">
import { computed, watch, ref } from "vue";
import type { IUpdateObjectDto } from "~/dto/object/update.dto";
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
  <div>
    <header>
      <h1>{{ object.name }}</h1>
      <ButtonOptions
        :actions="[
          {
            function: () => {
              editMode = true;
            },
            label: 'Modifier',
            svg: '/icons/edit.svg',
          },
          {
            function: () => {
              deleteAsked = true;
            },
            label: 'Supprimer',
            svg: '/icons/delete.svg',
          },
        ]"
      />
    </header>
    <main>
      <div>
        <div class="image-carousel" v-if="images.length">
          <button type="button" class="nav prev" @click="handlePrevImage">
            ←
          </button>
          <img :src="currentImage" alt="Object" />
          <button type="button" class="nav next" @click="handleNextImage">
            →
          </button>
        </div>
        <div class="image-empty" v-else>
          <img src="/objectImage.png" alt="Object" />
        </div>
        <p class="image-counter" v-if="images.length">
          Image {{ indexRenderedImage + 1 }} / {{ images.length }}
        </p>
      </div>

      <ObjectFormModification
        v-if="editMode"
        :object="object"
        @handleSubmitUpdate="handleSubmitUpdate"
        @cancelEdit="handleCancelEdit"
      />

      <div v-else>
        <div class="info-block">
          <p class="block-title">Informations</p>
          <div class="info-row">
            <span>État</span>
            <div>
              <sub v-if="!object.state"> aucun état renseigné</sub>
              <strong v-else>{{ object.state }}</strong>
            </div>
          </div>
          <div class="info-row" v-if="object.weight !== undefined">
            <span>Poids</span>
            <div>
              <sub v-if="!object.weight"> aucun poids renseigné </sub>
              <strong v-else>{{ object.weight }} kg</strong>
            </div>
          </div>
          <div class="info-row">
            <span>Matière</span>
            <div>
              <sub v-if="!object.material">
                aucune matière renseignée
              </sub>
              <strong v-else>{{object.material }}</strong>
            </div>
          </div>
          <div class="info-row">
            <span>Dimensions</span>
            <div>
              <sub v-if="!object.dimensions"> aucune dimension renseignée</sub>
              <strong v-else>{{ object.dimensions }}</strong>
            </div>
          </div>
          <div class="info-row">
            <span>Catégorie</span>
            <div>
              <sub v-if="object.category">
                aucun catégorie renseignée
              </sub>
              <strong v-else >{{ object.category }}</strong>
            </div>
          </div>
        </div>

        <div class="info-block">
          <p class="block-title">Description supplémentaire</p>
          <p v-if="!object.description">Informations supplémentaires sur {{ object.name }}</p>
          <p v-else>{{ object.description }}</p>
        </div>
      </div>
    </main>
  </div>
</template>

<style scoped>
.image-carousel {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}
.image-carousel img {
  max-width: 100%;
  max-height: 360px;
  object-fit: contain;
  border-radius: 0.5rem;
}
.nav {
  border: none;
  background: rgba(0, 0, 0, 0.65);
  color: #fff;
  font-size: 1.5rem;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  cursor: pointer;
}
.nav:hover {
  background: rgba(0, 0, 0, 0.8);
}
.image-counter {
  margin-top: 0.5rem;
  color: #555;
}
.image-empty {
  padding: 1rem;
  color: #666;
}
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
.info-block {
  margin-top: 1.5rem;
  padding: 1rem;
  border-radius: 0.75rem;
  background: #f9fafb;
}
.info-row {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  padding: 0.5rem 0;
  border-bottom: 1px solid #e5e7eb;
}
.info-row:last-child {
  border-bottom: none;
}
.block-title {
  font-weight: 700;
  margin-bottom: 0.75rem;
}
</style>
