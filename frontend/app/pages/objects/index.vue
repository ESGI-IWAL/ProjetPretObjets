<script setup lang="ts">
import useToaster from "~/composables/useToaster";
import type {
  IObjectInfoDisponibilityDto,
  ISearchLendingWithIdsObjectsDto,
  ISearchObjectDto,
} from "~/dto/object/search.dto";
import { ELendingStatus } from "~/enums/lending/status.enum";
import { searchLending, searchLendingWithObjectsIds } from "~/services/lending";
import { getObjects, searchObject } from "~/services/object";
import type { ILending } from "~/types/lending";
import type { IObject } from "~/types/object";

export interface IObjectInfos {
  nextLendingDate: Date | null;
  endCurrentLending: Date | null;
}

const objects = ref<IObject[] | null>(null);
const toaster = useToaster();

onMounted(async () => {
  try {
    objects.value = await getObjects();
  } catch {
    toaster.show("Erreur lors de la récupération des objets", "error", 5000);
  }
});
const objectsInfo = ref<Map<number, IObjectInfos> | null>(null);

const handleSearch = async (dto: ISearchObjectDto) => {
  try {
    let objectForSearchLendings = await searchObject(dto);
    let lendingsObjectInfo = await searchLendingWithObjectsIds({
      disponibilityEndDate: dto.disponibilityEndDate,
      disponibilityStartDate: dto.disponibilityStartDate ?? new Date(),
      idsObject: objectForSearchLendings.map((object) => object.id),
    });
    objectsInfo.value = new Map(
      lendingsObjectInfo.map((lendingInfo) => [
        lendingInfo.id,
        {
          nextLendingDate: lendingInfo.nextLending,
          endCurrentLending: lendingInfo.endCurrentLending,
        },
      ]),
    );
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
