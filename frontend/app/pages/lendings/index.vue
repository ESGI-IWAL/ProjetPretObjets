<script setup lang="ts">
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { getLendings, searchLending } from '~/services/lending';
import type { ILending } from '~/types/lending';

const lendings = ref<ILending[]|null>(null)
lendings.value = await getLendings()

const handleSearch = async (dto : ISearchLendingDto) => {
     lendings.value = await searchLending(dto)
}
</script>

<template>
    <div v-if="!lendings">
        <p>Aucun prêt trouvé</p>
    </div>
    <LendingListContainer v-else :lendings="lendings" />
</template>

<style scoped>
</style>