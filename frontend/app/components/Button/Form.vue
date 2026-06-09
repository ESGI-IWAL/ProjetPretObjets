<script setup lang="ts">
    defineProps<{
        cancelForm: () => void,
        validateForm: () => void,
    }>()
</script>

<template>
    <div class="form-actions">
        <button @click="() => cancelForm()" type="button" class="form-button-secondary">Annuler</button>
        <button @click="() => validateForm()" type="button" class="form-button-primary">Valider</button>
    </div>
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
.form-button-secondary {
  background-color: var(--color-surface);
  color: var(--color-title);
  border: 1px solid #c9c0ae;
  border-radius: 10px;
  padding: 10px 20px;
  font-weight: 600;
  font-size: 0.875rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.2s ease, box-shadow 0.2s ease;
  cursor: pointer;
}
.form-button-secondary:hover {
  background-color: #d9d2c5;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
}
</style>