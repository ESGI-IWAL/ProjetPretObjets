<script setup lang="ts">
</script>

<template>
  <div class="base-card">
    <header class="base-card__header">
      <slot name="header" />
    </header>

    <section class="base-card__content">
      <slot />
    </section>
  </div>
</template>

<style scoped>
.base-card {
  box-sizing: border-box;
  background-color: #111827;
  border-radius: 14px;
  padding: 1.5rem;
  width: 320px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.5);
  color: #e5e7eb;
  font-family: system-ui, sans-serif;
}

/* Sélection : anneau parfaitement aligné sur la card (inclut padding) */
.base-card.selected {
  box-shadow:
      0 0 0 2px #86efac,
      0 20px 40px rgba(0, 0, 0, 0.5);
}

.base-card__header {
  margin-bottom: 1rem;
  text-align: center;
}

.base-card__content {
  text-align: center;
}
</style>