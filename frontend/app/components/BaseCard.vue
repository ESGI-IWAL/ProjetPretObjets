<script setup lang="ts">
</script>

<template>
  <div
      class="base-card"
  >
    <header class="mb-4 text-center">
      <slot name="header" />
    </header>

    <section class="text-center">
      <slot />
    </section>
  </div>
</template>

<style scoped>

</style>