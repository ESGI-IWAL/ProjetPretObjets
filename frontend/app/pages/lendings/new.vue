<script setup lang="ts">
import useToaster from '~/composables/useToaster';
import type { ISearchObjectDto } from '~/dto/object/search.dto';
import { getObjectsOfConnectedUser, searchObject } from '~/services/object';
import { getUsersExceptCurrentUser } from '~/services/user';
import type { IObject } from '~/types/object';
import type { IUser } from '~/types/user';

const toaster = useToaster()
const users = ref<IUser[]|null>(null)
const objects = ref<IObject[]|null>(null)

onMounted(async() => {
        users.value = await getUsersExceptCurrentUser()
        objects.value = await getObjectsOfConnectedUser()
    })
    const handleSearchObjects = async (dto: ISearchObjectDto) => {
        try {
            objects.value = await searchObject(dto)
        }
        catch {
            toaster.show("Erreur lors de la recherche des objets", "error", 5000)
        }
    } 

</script>
<template>
    <div class="app-page">
        <div class="app-container">
            <div v-if="!users || !objects" class="surface-card text-center" style="color: var(--color-text); opacity: 0.6">
                Chargement...
            </div>
            <div v-else> 
                <LendingFormCreation :users="users" :objects="objects"  @handleSearchObjects="handleSearchObjects"/>
            </div>
        </div>
    </div>
</template>

<style scoped>
:deep(.app-page) {
  background-color: var(--color-background) !important;
}
</style>