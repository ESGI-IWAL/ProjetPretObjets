<script setup lang="ts">
    defineProps({
        navigationCreation: {
            type: Function,
            required: true
        },
        label: {
            type: String,
            required: true
        }
    })
</script>
<template>
    <button @click="() => navigationCreation()" class="form-button-primary">
        {{ label }}
    </button>
</template>

<style scoped>
.form-button-primary {
  background-color: var(--color-primary);
  color: var(--color-background);
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
  border: none;
}
.form-button-primary:hover {
  background-color: var(--color-secondary);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>