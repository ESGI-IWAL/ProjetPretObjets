<script setup lang="ts">
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import type { ILending } from '~/types/lending'

defineProps<{
  lendings: ILending[],
}>()

const emit = defineEmits(['search'])
function search(dto : ISearchLendingDto) {
    emit('search', dto)
}
const filterIsOpen = ref<boolean>(false)


</script>

<template>
    <div class="space-y-6">
        <div v-if=filterIsOpen>
            <section class="surface-card space-y-4">
                <LendingFormSearch @search="search" />
            </section>
        </div>
        <div class="flex justify-end">
            <ButtonCreation :navigation-creation="() => navigateTo('/lendings/new')" label="Créer un prêt"/>
            <ButtonFilter :open-filter="() => filterIsOpen = !filterIsOpen" />
        </div>

        <div v-if="lendings.length === 0" class="surface-card text-center text-gray-500">
            Aucun prêt trouvé.
        </div>

        <div v-else class="list-grid">
            <div v-for="lending in lendings" :key="lending.id">
                <LendingListCard :lending="lending" />
            </div>
        </div>
    </div>
</template>


<style>

</style>