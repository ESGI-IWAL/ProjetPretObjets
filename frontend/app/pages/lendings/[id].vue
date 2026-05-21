<script setup lang="ts">
import { errorMessages } from 'vue/compiler-sfc';
import type { IUpdateLendingDto } from '~/dto/lending/update.dto';
import { deleteLending, getLendingById, updateLending } from '~/services/lending';
import type { ILending } from '~/types/lending';

const route = useRoute()

const lending = ref<ILending | null>(null)
lending.value = await getLendingById(Number(route.params.id))

const handleValidateForm = async(updateLendingDto: IUpdateLendingDto) => {
    await updateLending(updateLendingDto)
    lending.value = await getLendingById(updateLendingDto.id)
}

const handleDelete = async() => {
    await deleteLending(Number(route.params.id))
    navigateTo('/lendings')
}
</script>
<template>
    <div v-if="!lending">
        <p>Ce prêt n'existe pas</p>
    </div>
    <LendingContainer v-else :lending="lending" @handleSubmitUpdate="handleValidateForm" @handleDelete="handleDelete" />
</template>


<style>

</style>