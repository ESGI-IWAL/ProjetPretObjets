<script setup lang="ts">
import ObjectListContainer from "@/components/Object/List/Container.vue";
    definePageMeta({
      middleware: 'auth'
    })
</script>
<template>
  <ObjectListContainer />
</template>

<style scoped>
</style>