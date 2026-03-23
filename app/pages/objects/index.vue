<script setup lang="ts">
import ObjectContainer from "@/components/ObjectContainer.vue";
import { useActiveObject } from '@/composables/useActiveObject'
const activeObject = useActiveObject();
</script>
<template>
  <ObjectContainer v-model="activeObject" />
</template>

<style scoped>
</style>