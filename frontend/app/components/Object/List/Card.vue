<script setup lang="ts">
import BaseCard from '~/components/BaseCard.vue'
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
  <div class="card-click-area" @click="handleClick">
    <BaseCard :class="{ selected }">
      <template #header>
        <img
            :src="object.image"
            class="card-avatar"
            alt="Image de l'objet"
        >
        <h2 class="card-title">{{ object.name }}</h2>
      </template>
    </BaseCard>
  </div>
</template>

<style scoped>

</style>