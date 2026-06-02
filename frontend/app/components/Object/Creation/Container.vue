<script setup lang="ts">
defineProps<{
  title: string
  subtitle?: string
}>()
</script>

<template>
  <section class="app-card space-y-6">
	<header class="form-header">
	  <p class="form-eyebrow">Nouvel objet</p>
	  <h1 class="form-title">{{ title }}</h1>
	  <p v-if="subtitle" class="form-description">{{ subtitle }}</p>
	</header>

	<div>
	  <slot />
	</div>
  </section>
</template>

<style scoped>
</style>