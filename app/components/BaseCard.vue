<script setup lang="ts">
</script>

<template>
  <div
      class="box-border w-80 rounded-[14px] bg-gray-900 p-6 text-gray-200 shadow-[0_20px_40px_rgba(0,0,0,0.5)] font-sans [&.selected]:shadow-[0_0_0_2px_#86efac,0_20px_40px_rgba(0,0,0,0.5)]"
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