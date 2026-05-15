<script setup lang="ts">
  import ObjectListContainer from "@/components/Object/List/Container.vue";
  import type { ISearchObjectDto } from "~/dto/object/search.dto";
  import { getObjects, searchObjects } from "~/services/object";

  definePageMeta({
    middleware: 'auth'
  })

  const objects = await getObjects();

  const handleSearchSubmit = (dto: ISearchObjectDto) => {
      const objects = searchObjects(dto)
  }

</script>
<template>
  <ObjectListContainer />
</template>

<style scoped>
</style>