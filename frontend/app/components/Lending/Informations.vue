<script setup lang="ts">
import type { ILending } from '~/types/lending';
import { getLendingStatusLabel } from '~/enums/lending/status.enum';

const props = defineProps<{
  lending: ILending
}>()


</script>

<template>
  <div class="bg-white p-4 rounded-lg shadow-sm">
    <div class="flex items-start justify-between">
      <div>
        <h1 class="text-2xl font-bold mb-1">Prêt — {{ getLendingStatusLabel(lending.status) }}</h1>
      </div>
      <div class="text-right text-sm text-gray-500">
        <p>Statut: <span class="font-semibold">{{ getLendingStatusLabel(lending.status) }}</span></p>
      </div>
    </div>

    <div class="mt-4 grid grid-cols-1 md:grid-cols-3 gap-4">
      <div class="flex items-center gap-3">
        <img :src="lending.borrowedBy.avatar ?? 'https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png'" alt="Avatar" class="w-16 h-16 rounded-full" />
        <div>
          <h2 class="text-sm font-semibold">Emprunteur</h2>
          <p class="text-sm text-gray-700">{{ lending.borrowedBy.username }}</p>
        </div>
      </div>

      <div class="flex items-center gap-3 md:col-span-1">
        <div>
          <h2 class="text-sm font-semibold">Objet prêté</h2>
          <div class="flex items-center gap-3">
            <img :src="lending?.object?.images?.[0] ?? '/objectImage.png'" alt="Objet" class="w-24 h-24 object-cover rounded" />
            <p class="text-sm text-gray-700">{{ lending.object.name }}</p>
          </div>
        </div>
      </div>

      <div>
        <h2 class="text-sm font-semibold">Dates</h2>
        <p class="text-sm text-gray-700">Début : {{ lending.startAt }}</p>
        <p v-if=lending.endAt class="text-sm text-gray-700">Fin : {{ lending.endAt}}</p>
        <p v-else=lending.endAt class="text-sm text-gray-700"> Aucune fin déterminée </p>
      </div>
    </div>
  </div>
</template>