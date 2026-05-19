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

const handleClick = (lending : ILending) => {
    navigateTo(`/lending/${lending.id}`)
    }
</script>

<template>
    <LendingFormSearch @search="search" />
    <ButtonCreation :navigation-creation="() => navigateTo('/lending/new')" label="Créer un prêt"/>
    <div v-for="lending in lendings" :key="lending.id">
        <LendingListCard :lending="lending" @click="handleClick(lending)" />
    </div>
</template>


<style>

</style>