<script setup lang="ts">
import useToaster from '~/composables/useToaster';
import type { ISearchObjectDto } from '~/dto/object/search.dto';
import { searchObject, searchObjectOnList } from '~/services/object';
import { getUsers } from '~/services/user';
import type { IObject } from '~/types/object';

    const users = await getUsers()
    const toaster = useToaster()

    const objects = ref<IObject[]|null>(null)
    const idsObject= ref<number[]>([])

    const handleSearchObjectsOnDate = async (endDate: Date|null, startDate: Date|null) => {
        try{
            objects.value = await searchObject({disponibilityEndDate: endDate, disponibilityStartDate: startDate})
            idsObject.value = objects.value.map(object => object.id) 
        }
        catch {
            objects.value= []
            toaster.show("Erreur lors de la récupération des objects correspondants à cette date", "error", 5000)
        }
    }

    const handleSearchObjects = async (dto: Omit<ISearchObjectDto, "disponibilityEndDate" | "disponibilityStartDate">) => {
        try {
            objects.value = await searchObjectOnList(idsObject.value,  dto)
        }
        catch {
            toaster.show("Erreur lors de la recherche des objets", "error", 5000)
        }
    } 

</script>
<template>
    <div class="app-page">
        <div class="app-container">
            <LendingFormCreation :users="users" :objects="objects" @handleSearchObjectsOnDate="handleSearchObjectsOnDate" @handleSearchObjects="handleSearchObjects"/>
        </div>
    </div>
</template>

<style scoped>
</style>