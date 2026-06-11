<script setup lang="ts">
import type { IObjectInfos } from '~/pages/objects/index.vue';
import type { IObject } from '~/types/object';
import formatDateLong from '~/utils/date';

const route = useRouter()
defineProps<{
    object: IObject
    objectInfo?: IObjectInfos
}>()

</script>

<template>
    <div @click="route.push(`/objects/${object.id}`)" class="object-card card-link">
        <img :src="object?.images?.[0] ?? '/objectImage.png'" alt="Objet" class="object-card-img" />
        <div class="object-card-body">
            <p class="object-card-name">{{ object.name }}</p>
            <p v-if="objectInfo?.nextLendingDate" class="object-card-status">
                Disponible jusqu'au {{ formatDateLong(objectInfo.nextLendingDate) }}
            </p>
            <p v-else-if="objectInfo?.currentLendingStart" class="object-card-status object-card-status--active">
                Prêt en cours depuis le {{ formatDateLong(objectInfo.currentLendingStart) }}
            </p>
            <p v-else-if="objectInfo?.endCurrentLending" class="object-card-status">
                Fin du prêt le {{ formatDateLong(objectInfo.endCurrentLending) }}
            </p>
            <p v-else class="object-card-status object-card-status--free">Disponible</p>
        </div>
    </div>
</template>

<style scoped>
.object-card {
  background-color: var(--color-surface);
  border-radius: var(--border-radius);
  box-shadow: 0 4px 6px rgba(0,0,0,0.05);
  overflow: hidden;
  transition: box-shadow 0.2s ease, transform 0.2s ease;
  cursor: pointer;
}
.object-card:hover {
  box-shadow: 0 8px 16px rgba(0,0,0,0.08);
  transform: translateY(-2px);
}
.object-card-img {
  width: 100%;
  height: 160px;
  object-fit: cover;
}
.object-card-body {
  padding: 0.875rem 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}
.object-card-name {
  font-weight: 600;
  font-size: 0.95rem;
  color: var(--color-title);
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.object-card-status {
  font-size: 0.78rem;
  color: var(--color-text);
  opacity: 0.65;
  margin: 0;
}
.object-card-status--active {
  color: var(--color-secondary);
  opacity: 1;
  font-weight: 500;
}
.object-card-status--free {
  color: var(--color-primary);
  opacity: 1;
  font-weight: 500;
}
</style>