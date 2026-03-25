<script setup lang="ts">
import ObjectContainer from "@/components/ObjectContainer.vue";
    definePageMeta({
      middleware: 'auth'
    })
</script>
<template>
  <ObjectContainer />
</template>

<style scoped>
</style>