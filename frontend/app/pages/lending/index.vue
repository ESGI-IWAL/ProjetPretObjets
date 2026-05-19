<script setup lang="ts">
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { getLendings, searchLending } from '~/services/lending';
import type { ILending } from '~/types/lending';

// TO DO => lendings initals avec le get, puis changer via la recherche = reactive ? 
// possibilité de descendre la recherche, et de donner le reactive au composant de recherche
const lendings = ref<ILending[]>([])
lendings.value = await getLendings()

const handleSearch = async (dto : ISearchLendingDto) => {
     lendings.value = await searchLending(dto)
}
</script>
<template>
    <LengingContainer :lendings="lendings" />
</template>

<style scoped>
</style>