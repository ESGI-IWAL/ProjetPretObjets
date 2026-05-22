<script setup lang="ts">
defineProps<{
  title: string;
}>();

defineEmits<{
  close: [];
}>();
</script>

<template>
  <Teleport to="body">
    <div class="popin-backdrop" @click="$emit('close')">
      <section class="popin-card" @click.stop>
        <header class="popin-header">
          <h1 class="popin-title">{{ title }}</h1>
          <button class="popin-close" type="button" @click="$emit('close')">✕</button>
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