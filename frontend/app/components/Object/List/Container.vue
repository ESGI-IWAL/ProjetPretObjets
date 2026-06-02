<script setup lang="ts">
import type { ISearchObjectDto } from "~/dto/object/search.dto";
import type { IObjectInfos } from "~/pages/objects/index.vue";
import type { IObject } from "~/types/object";

defineProps<{
  objects: IObject[];
  objectsInfo?: Map<number, IObjectInfos>;
}>();

const emit = defineEmits(["handleSearch"]);

const handleSearch = (dto: ISearchObjectDto) => {
  emit("handleSearch", dto);
};


const filterIsOpen = ref<boolean>(false);

</script>
<template>
  <div v-if="filterIsOpen">
    <section class="surface-card space-y-4">
      <ObjectFormSearch @handleSearch="handleSearch" />
    </section>
  </div>
  <div class="flex justify-end gap-4">
    <ButtonCreation
      :navigation-creation="() => navigateTo('/objects/new')"
      label="Créer un objet"
    />
    <ButtonFilter :open-filter="() => (filterIsOpen = !filterIsOpen)" />
  </div>

  <div
    v-if="objects.length === 0"
    class="surface-card text-center text-gray-500"
  >
    Aucun objet trouvé.
  </div>

  <div v-else class="list-grid">
    <div v-for="object in objects" :key="object.id">
      <div v-if=" objectsInfo">
        <ObjectListCard
          :object="object"
          :objectInfo="objectsInfo.get(object.id)"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
