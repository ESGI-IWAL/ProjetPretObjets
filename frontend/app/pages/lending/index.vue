<script setup lang="ts">
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { getLendings, searchLending } from '~/services/lending';

// TO DO => lendings initals avec le get, puis changer via la recherche = reactive ? 
// possibilité de descendre la recherche, et de donner le reactive au composant de recherche
const lendings = getLendings()

const handleSearch = (dto : ISearchLendingDto) => {
    const lendings = searchLending(dto)
}
</script>
<template>

</template>

<style scoped>
</style>