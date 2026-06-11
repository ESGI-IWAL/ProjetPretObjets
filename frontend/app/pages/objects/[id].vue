<script setup lang="ts">
import useToaster from '~/composables/useToaster';
import type { IUpdateObjectDto } from '~/dto/object/update.dto';

import { deleteObject, getObjectById, updateObject } from '~/services/object';
import type { IObject } from '~/types/object';

const route = useRoute()
const toaster = useToaster()
const object = ref<IObject | null>(null)

    onMounted(async() => {

        object.value = await getObjectById(Number(route.params.id))
    })

const handleSubmitUpdate = async(updateObjectDto: IUpdateObjectDto) => {
    try{
        await updateObject(updateObjectDto)
        object.value = await getObjectById(updateObjectDto.id)
        toaster.show("Votre objet a bien été modifié", "success")
    }
    catch {
        toaster.show("Erreur lors de la mise a jour de votre objet", "error", 5000)
    }
}

const handleDelete = async() => {
    try{
        await deleteObject(Number(route.params.id))
        navigateTo('/objects')
    }
    catch {
        toaster.show("Erreur lors de la suppression de votre objet", "error", 5000)
    }
}
</script>
<template>
    <div class="app-page">
        <div class="app-container">
            <div v-if="!object" class="surface-card text-center" style="color: var(--color-text); opacity: 0.6">
                <p>Cet objet n'existe pas ou a été supprimé.</p>
            </div>
            <ObjectContainer v-else :object="object" @handleSubmitUpdate="handleSubmitUpdate" @handleDelete="handleDelete" />
        </div>
    </div>
</template>


<style scoped>
:deep(.app-page) {
  background-color: var(--color-background) !important;
}
</style>