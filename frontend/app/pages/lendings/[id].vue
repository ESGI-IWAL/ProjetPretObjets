<script setup lang="ts">
import type { IUpdateLendingDto } from '~/dto/lending/update.dto';
import { getLendingById, updateLending } from '~/services/lending';
import type { ILending } from '~/types/lending';

const route = useRoute()

const lending = ref<ILending | null>(null)
lending.value = await getLendingById(Number(route.params.id))

const handleValidateForm = async(updateLendingDto:IUpdateLendingDto) => {
    await updateLending(updateLendingDto)
    lending.value = await getLendingById(updateLendingDto.id)
}
</script>
<template>
    <div v-if="!lending">
        <p>Ce prêt n'existe pas</p>
    </div>
    <LendingContainer v-else :lending="lending" @handleSubmitUpdate="handleValidateForm" />
</template>


<style>

</style>