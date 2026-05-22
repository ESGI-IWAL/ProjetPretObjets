<script setup lang="ts">
import type { ILending } from '~/types/lending';
import { getLendingStatusLabel } from '~/enums/lending/status.enum';
import { computed } from 'vue'

const props = defineProps<{
  lending: ILending
}>()

// normalize possible different property names from API
const l: any = props.lending as any
const borrower = computed(() => l?.borrower ?? l?.borrowedBy)
const objectItem = computed(() => l?.object ?? l?.item)
const startAt = computed(() => l?.startDate ?? l?.startAt ?? l?.start)
const endAt = computed(() => l?.endDate ?? l?.endAt ?? l?.end)

</script>

<template>
  <div class="bg-white p-4 rounded-lg shadow-sm">
    <div class="flex items-start justify-between">
      <div>
        <h1 class="text-2xl font-bold mb-1">Prêt — {{ getLendingStatusLabel(l.status) }}</h1>
        <p class="text-sm text-gray-500">ID: <span class="font-mono">{{ l.id }}</span></p>
      </div>
      <!-- actions area is aligned by ButtonsOptions; keep this space for it -->
      <div class="text-right text-sm text-gray-500">
        <p>Statut: <span class="font-semibold">{{ getLendingStatusLabel(l.status) }}</span></p>
      </div>
    </div>

    <div class="mt-4 grid grid-cols-1 md:grid-cols-3 gap-4">
      <div class="flex items-center gap-3">
        <img :src="borrower?.avatar ?? 'https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png'" alt="Avatar" class="w-16 h-16 rounded-full" />
        <div>
          <h2 class="text-sm font-semibold">Emprunteur</h2>
          <p class="text-sm text-gray-700">{{ borrower?.pseudo ?? borrower?.username ?? '-' }}</p>
        </div>
      </div>

      <div class="flex items-center gap-3 md:col-span-1">
        <div>
          <h2 class="text-sm font-semibold">Objet prêté</h2>
          <div class="flex items-center gap-3">
            <img v-if="objectItem?.images?.length" :src="objectItem.images[0]" alt="Objet" class="w-24 h-24 object-cover rounded" />
            <p class="text-sm text-gray-700">{{ objectItem?.name ?? '-' }}</p>
          </div>
        </div>
      </div>

      <div>
        <h2 class="text-sm font-semibold">Dates</h2>
        <p class="text-sm text-gray-700">Début : {{ startAt ?? '-' }}</p>
        <p class="text-sm text-gray-700">Fin : {{ endAt ?? '-' }}</p>
      </div>
    </div>
  </div>
</template>