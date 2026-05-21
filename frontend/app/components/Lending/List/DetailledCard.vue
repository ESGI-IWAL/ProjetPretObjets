<script setup lang="ts">
import type { ILending } from '~/types/lending';
import formatDateLong from '~/utils/date';
defineProps<{
  lending: ILending,
}>()

</script>
<template>
    <div class="surface-card bg-gray-50 space-y-4">
        <div class="grid gap-4 md:grid-cols-3">
            <div @click="() => navigateTo(`/object/${lending.object.id}`)" class="card-link space-y-2">
                <p class="text-sm font-semibold text-gray-700">Objet emprunté</p>
                <img :src="lending?.object?.images?.[0] ?? '/objectImage.png'" alt="Objet emprunté" class="thumb-lg" />
                <p class="font-medium text-gray-900">{{ lending.object.name }}</p>
            </div>

            <div @click="() => navigateTo(`/users/${lending.borrowedBy.id}`)" class="card-link space-y-2">
                <p class="text-sm font-semibold text-gray-700">Emprunteur</p>
                <img :src="lending?.borrowedBy.avatar ?? 'https://w7.pngwing.com/pngs/205/731/png-transparent-default-avatar-thumbnail.png'" alt="Avatar de l'emprunteur" class="avatar-md" />
                <p class="font-medium text-gray-900">{{ lending.borrowedBy.username }}</p>
            </div>

            <div class="space-y-2">
                <p class="text-sm font-semibold text-gray-700">Durée du prêt</p>
                <p v-if="lending.endAt" class="text-sm text-gray-600">Du {{ formatDateLong(lending.startAt) }} au {{ formatDateLong(lending.endAt) }}</p>
                <p v-else> Depuis le {{ formatDateLong(lending.startAt) }}</p>
                <button @click="() => navigateTo(`/lendings/${lending.id}`)" type="button" class="form-button-primary w-fit">
                    Voir plus
                </button>
            </div>
        </div>
    </div>
</template>


<style>

</style>