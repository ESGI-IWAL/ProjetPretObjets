<script setup lang="ts">
import type { ISearchLendingDto } from '~/dto/lending/search.dto';
import { getLendings, searchLending } from '~/services/lending';
import type { ILending } from '~/types/lending';

const lendings = ref<ILending[]|null>(null)
onMounted(async ()=> {
    try{
        lendings.value = await getLendings()
        console.log(lendings)
    }
    catch{
        lendings.value = []
    }
})
const handleSearch = async (dto : ISearchLendingDto) => {
     lendings.value = await searchLending(dto)
}
</script>

<template>
    <div class="app-page">
        <div class="app-container space-y-6">
            <header class="space-y-2">
                <p class="form-eyebrow">Gestion des prêts</p>
                <h1 class="form-title">Liste des prêts</h1>
                <p class="form-description">Recherche, consultation et suivi des prêts en cours ou terminés.</p>
            </header>

            <div v-if="!lendings" class="surface-card">
                <p class="text-gray-500">Chargement des prêts...</p>
            </div>

            <LendingListContainer
                v-else
                :lendings="lendings"
                @search="handleSearch"
            />
        </div>
    </div>
</template>

<style scoped>
</style>