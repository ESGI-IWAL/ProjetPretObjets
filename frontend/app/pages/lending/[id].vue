<script setup lang="ts">
import { getLendingById, updateLending } from '~/services/lending';
import type { ILending } from '~/types/lending';

const route = useRoute()

const lending = ref<ILending | null>(null)
lending.value = await getLendingById(route.params.id as string)

const handleValidateForm = async(endDate: Date) => {
    await updateLending(route.params.id as string, endDate)
    lending.value = await getLendingById(route.params.id as string)
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