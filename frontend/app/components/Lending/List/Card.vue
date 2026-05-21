<script setup lang="ts">
import type { ILending } from '~/types/lending';
import { ELendingStatus } from '~/enums/lending/status.enum';

defineProps<{
  lending: ILending,
}>()

const showDetails = ref<boolean>(false)

const handleClick = () => {
    showDetails.value = !showDetails.value
}

const getStatusClass = (status: ELendingStatus) => {
    switch (status) {
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
    <article class="surface-card surface-card-hover space-y-4">
        <div class="flex items-start gap-4">
            <img :src="lending.borrower.avatar" alt="Avatar de l'emprunteur" class="avatar-sm" />

            <div class="min-w-0 flex-1 space-y-2">
                <div class="flex flex-wrap items-center gap-2">
                    <h3 class="truncate text-lg font-semibold text-gray-900">{{ lending.object.name }}</h3>
                    <span :class="getStatusClass(lending.status)">{{ lending.status }}</span>
                </div>

                <p class="text-sm text-gray-500">
                    Emprunté par <span class="font-medium text-gray-700">{{ lending.borrower.userName }}</span>
                </p>
                <p class="text-sm text-gray-500">Jusqu’au {{ lending.endAt }}</p>
            </div>

            <img :src="lending.object.images[0]" alt="Objet prêté" class="thumb-md" />
        </div>

        <div class="flex justify-end">
            <button @click="handleClick" type="button" class="text-sm font-medium text-blue-600 hover:text-blue-700">
                {{ showDetails ? 'Masquer les détails' : 'Voir les détails' }}
            </button>
        </div>

        <div v-if="showDetails" class="pt-2">
            <LendingListDetailledCard :lending="lending" />
        </div>
    </article>
</template>


<style>

</style>