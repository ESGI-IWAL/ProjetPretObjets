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
  <div class="surface-card bg-gray-50 space-y-6">
    <!-- En-tête avec statut à droite -->
    <div class="flex items-start justify-between gap-4">
      <div class="space-y-2">
        <p class="text-sm font-semibold text-gray-700">Objet emprunté</p>
        <div @click="() => navigateTo(`/object/${lending.object.id}`)" class="card-link flex items-center gap-4">
          <img
              :src="lending?.object?.images?.[0] ?? '/objectImage.png'"
              alt="Objet emprunté"
              class="thumb-md"
          />
          <p class="font-medium text-gray-900">{{ lending.object.name }}</p>
        </div>
      </div>

      <span :class="getStatusClass(lending.status)">
        {{ getLendingStatusLabel(lending.status) }}
      </span>
    </div>

    <!-- Emprunteur -->
    <div @click="() => navigateTo(`/users/${lending.borrowedBy.id}`)" class="card-link space-y-3">
      <p class="text-sm font-semibold text-gray-700">Emprunteur</p>
      <div class="flex items-center gap-4">
        <img
            :src="lending?.borrowedBy.avatar ?? 'https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png'"
            alt="Avatar de l'emprunteur"
            class="avatar-md"
        />
        <p class="font-medium text-gray-900">{{ lending.borrowedBy.username }}</p>
      </div>
    </div>

    <!-- Durée + bouton -->
    <div class="space-y-3">
      <p class="text-sm font-semibold text-gray-700">Emprunt</p>
      <p v-if="lending.endAt" class="text-sm text-gray-600">
        Début {{ formatDateLong(lending.startAt) }} jusqu’au {{ formatDateLong(lending.endAt) }}
      </p>
      <p v-else class="text-sm text-gray-600">
        Depuis le {{ formatDateLong(lending.startAt) }}
      </p>

      <div class="flex justify-end">
        <button
            @click="() => navigateTo(`/lendings/${lending.id}`)"
            type="button"
            class="form-button-primary w-fit"
        >
          Voir plus
        </button>
      </div>
    </div>
  </div>
</template>