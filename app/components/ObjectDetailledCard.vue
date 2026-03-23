<script setup lang="ts">
import BaseCard from "~/components/BaseCard.vue";
import type { Object } from '@/types/object'

const props = defineProps<{
  object: Object
  selected?: boolean
}>()

const emit = defineEmits<{
  (e: 'select', object: Object): void
}>()

function handleClick() {
  emit('select', props.object)
}
</script>

<template>
  <div
      class="click-area"
      @click="handleClick"
  >
    <BaseCard :class="{ selected }">
      <template #header>
        <img
            :src="object.image"
            alt="Avatar utilisateur"
            class="avatar"
        >
        <h2 class="pseudo">{{ object.name }}</h2>
        <h2 class="description">{{ object.description }}</h2>
      </template>
    </BaseCard>
  </div>
</template>

<style scoped>
.click-area {
  display: flex;
  justify-content: center;
  cursor: pointer;
}

.avatar {
  width: 96px;
  height: 96px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 0.75rem;
}

.pseudo {
  margin: 0;
  font-size: 1.25rem;
}

.status {
  margin: 0.75rem 0;
}

.active {
  color: #22c55e;
}

.inactive {
  color: #ef4444;
}
</style>