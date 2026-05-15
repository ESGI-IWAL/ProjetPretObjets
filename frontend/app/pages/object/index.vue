<script setup lang="ts">
import ObjectListContainer from "@/components/Object/List/Container.vue";
import { getObjects } from "~/services/object";
    definePageMeta({
      middleware: 'auth'
    })

const objects = await getObjects();

</script>
<template>
  <ObjectListContainer />
</template>

<style scoped>
</style>