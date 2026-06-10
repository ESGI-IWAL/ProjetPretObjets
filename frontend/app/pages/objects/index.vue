<script setup lang="ts">
import useToaster from "~/composables/useToaster";
import type {
  ISearchObjectWithDatesDto,
} from "~/dto/object/search.dto";

import { searchLendingWithObjectsIds } from "~/services/lending";
import { getObjectsOfConnectedUser, searchObject} from "~/services/object";
import type { IObject } from "~/types/object";

export interface IObjectInfos {
  nextLendingDate: Date | null;
  endCurrentLending: Date | null;
  currentLendingStart: Date | null;
}

const objects = ref<IObject[] | null>(null);
const toaster = useToaster();

onMounted(async () => {
  try {
    objects.value = await getObjectsOfConnectedUser();
    getObjectsDateInfo(objects.value, null)
  } catch {
    toaster.show("Erreur lors de la récupération des objets", "error", 5000);
  }
});
const objectsInfo = ref<Map<number, IObjectInfos> | null>(null);

const getObjectsDateInfo = async (objects: IObject[], disponibilityDate: string|null) => {
  try {

    let lendingsObjectInfo = await searchLendingWithObjectsIds({
      disponibilityDate: disponibilityDate ?? new Date().toISOString().slice(0, 10),
      idsObject: objects.map((object) => object.id),
    });
    objectsInfo.value = new Map(
      lendingsObjectInfo.map((lendingInfo) => [
        lendingInfo.id,
        {
          nextLendingDate: lendingInfo.nextLending,
          endCurrentLending: lendingInfo.endCurrentLending,
          currentLendingStart: lendingInfo.currentLendingStart ?? null,
        },
      ]),
    );
  }
  catch {
        toaster.show("Erreur lors de la récupération des objets", "error", 5000);

  }
  
}
const handleSearch = async (dto: ISearchObjectWithDatesDto) => {
  try {
    objects.value = await searchObject({category: dto.category, material: dto.material, name: dto.name, stateOfWear: dto.state});

    getObjectsDateInfo(objects.value, dto.disponibilityDate)
  } catch {
    toaster.show("Erreur lors de la recherche des objets", "error", 5000);
  }
};
</script>
<template>
  <div class="app-page">
    <div class="app-container space-y-6">
      <header class="space-y-2">
        <p class="form-eyebrow">Gestion des objets</p>
        <h1 class="form-title">Liste des objets</h1>
        <p class="form-description">
          Recherche, consultation et suivi des objets possédés.
        </p>
      </header>

      <div v-if="!objects || !objectsInfo" class="surface-card">
        <p class="text-gray-500">Chargement des objets...</p>
      </div>

      <ObjectListContainer
        v-else
        :objects="objects"
        :objectsInfo="objectsInfo"
        @handleSearch="handleSearch"
      />
    </div>
  </div>
</template>

<style scoped></style>
