<script setup lang="ts">
defineProps<{
  title: string;
}>();

</script>

<template>
  <Teleport to="body">
    <div class="popin-backdrop">
      <section class="popin-card">
        <header class="popin-header">
          <h1 class="popin-title">{{ title }}</h1>
        </header>

        <main class="popin-content">
          <slot />
        </main>

        <footer v-if="$slots.buttons" class="popin-actions">
          <slot name="buttons" />
        </footer>
      </section>
    </div>
  </Teleport>
</template>