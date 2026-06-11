<script setup lang="ts">
import type { ILending } from '~/types/lending';
import formatDateLong from '~/utils/date';
import {
  ELendingStatus,
  getLendingStatusLabel,
  normalizeLendingStatus
} from '~/enums/lending/status.enum';

defineProps<{
  lending: ILending,
  date: string
}>()

const getStatusClass = (status: string | ELendingStatus | null | undefined) => {
  switch (normalizeLendingStatus(status)) {
    case ELendingStatus.IN_PROGRESS:
      return 'chip-active'
    case ELendingStatus.COMPLETED:
      return 'chip-completed'
    case ELendingStatus.CANCELED:
      return 'chip-canceled'
    case ELendingStatus.PENDING:
      return 'chip-pending'
    case ELendingStatus.REFUSED:
      return 'chip-refused'
    case ELendingStatus.VALIDATED:
      return 'chip-validated'
    default:
      return 'chip-neutral'
  }
}
</script>

<template>
  <div class="surface-card space-y-6" style="background-color: var(--color-surface)">
    <!-- En-tête avec statut à droite -->
    <div class="flex items-start justify-between gap-4">
      <div class="space-y-2">
        <p class="text-sm font-semibold" style="color: var(--color-title)">Objet emprunté</p>
        <div @click="() => navigateTo(`/objects/${lending.object.id}`)" class="card-link flex items-center gap-4">
          <img
              :src="lending?.object?.images?.[0] ?? '/objectImage.png'"
              alt="Objet emprunté"
              class="thumb-md"
          />
          <p class="font-medium" style="color: var(--color-text)">{{ lending.object.name }}</p>
        </div>
      </div>

      <span :class="getStatusClass(lending.status)">
        {{ getLendingStatusLabel(lending.status) }}
      </span>
    </div>

    <!-- Emprunteur -->
    <div class="card-link space-y-3">
      <p class="text-sm font-semibold" style="color: var(--color-title)">Emprunteur</p>
      <div class="flex items-center gap-4">
        <img
            :src="lending?.borrowedBy.avatar ?? 'https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png'"
            alt="Avatar de l'emprunteur"
            class="avatar-md"
        />
        <p class="font-medium" style="color: var(--color-text)">{{ lending.borrowedBy.username }}</p>
      </div>
    </div>

    <div class="space-y-3">
      <p class="text-sm font-semibold" style="color: var(--color-title)">Emprunt</p>

      <p v-if="!lending.endedAt && (lending.startedAt?.split('T')[0] ?? '') <= date" class="text-sm" style="color: var(--color-text); opacity: 0.7">
        Depuis le {{ formatDateLong(lending.startedAt) }}
      </p>
      <p v-else-if="(lending.startedAt?.split('T')[0] ?? '') >= date && !lending.endedAt" class="text-sm" style="color: var(--color-text)">
        Prochain prêt le {{ formatDateLong(lending.startedAt) }} sans fin déterminée
      </p>
      <p v-else-if="(lending.endedAt?.split('T')[0] ?? '') <= date" class="text-sm" style="color: var(--color-text)">
        Terminé le {{ formatDateLong(lending.endedAt) }}
      </p>
      <p v-else-if="(lending.startedAt?.split('T')[0] ?? '') < date && (lending.endedAt?.split('T')[0] ?? '') >= date" class="text-sm" style="color: var(--color-text)">
        Commencé le : {{ formatDateLong(lending.startedAt) }} ; Fini le {{ formatDateLong(lending.endedAt) }}
      </p>
      <p v-else-if="(lending.startedAt?.split('T')[0] ?? '') >= date && (lending.endedAt?.split('T')[0] ?? '') >= date" class="text-sm" style="color: var(--color-text)">
        Prochain prêt le {{ formatDateLong(lending.startedAt) }} finissant le {{ formatDateLong(lending.endedAt) }}
      </p>
    </div>
  </div>
</template>