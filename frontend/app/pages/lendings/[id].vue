<script setup lang="ts">
import useToaster from '~/composables/useToaster';
import type { IUpdateLendingDto } from '~/dto/lending/update.dto';
import { deleteLending, getLendingById, updateLending } from '~/services/lending';
import type { ILending } from '~/types/lending';

const route = useRoute()
const toaster = useToaster()
const lending = ref<ILending | null>(null)
lending.value = await getLendingById(Number(route.params.id))

const handleSubmitUpdate = async(updateLendingDto: IUpdateLendingDto) => {
    try{
        await updateLending(updateLendingDto)
        lending.value = await getLendingById(updateLendingDto.id)
        toaster.show("Votre prêt a bien été modifié", "success")
    }
    catch {
        toaster.show("Erreur lors de la mise a jour de votre prêt", "error", 5000)
    }
}

const handleDelete = async() => {
    try{
        await deleteLending(Number(route.params.id))
        navigateTo('/lendings')
    }
    catch {
        toaster.show("Erreur lors de la suppression de votre prêt", "error", 5000)
    }
}
</script>
<template>
    <div v-if="!lending">
        <p>Ce prêt n'existe pas</p>
    </div>
    <LendingContainer v-else :lending="lending" @handleSubmitUpdate="handleSubmitUpdate" @handleDelete="handleDelete" />
</template>


<style>

</style>