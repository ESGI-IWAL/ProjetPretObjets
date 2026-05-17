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
</script>

<template>
    <LendingSearchForm @search="search" />
    <div v-for="lending in lendings" :key="lending.id">
        <LendingCard :lending="lending"/>
    </div>
</template>


<style>

</style>