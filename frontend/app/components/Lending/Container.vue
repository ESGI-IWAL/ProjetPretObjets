<script setup lang="ts">
import type { ILending } from '~/types/lending';

    defineProps<{
        lending: ILending
    }>()

    const editMode = ref(false)
    const deleteAsked = ref(false)

    const emit = defineEmits(['handleSubmitUpdate', 'delete'])

    const handleSubmitUpdate = (endDate : Date) => {
        emit('handleSubmitUpdate', endDate)
        editMode.value = false
    }

    const handleDelete = () => {
        emit('delete')
        deleteAsked.value = false
    }
</script>

<template>
    <div>
        <div v-if="editMode">
            <LendingFormModification :endDate="lending.endDate" @updateLending="handleSubmitUpdate" :editMode="editMode" />
        </div>
        <div v-if="deleteAsked">
            <p>Êtes-vous sûr de vouloir supprimer ce prêt ?</p>
            <button @click="handleDelete">Oui</button>
            <button @click="deleteAsked = false">Non</button>
        </div>
        <ButtonsOptions actions="[
            { function: () => { editMode.value = true }, label: 'Modifier', svg: '/icons/edit.svg' },
            { function: () => { deleteAsked.value = true }, label: 'Supprimer', svg: '/icons/delete.svg' }
        ]" />
        <LendingInformations :lending="lending" />

    </div>
</template>


<style>

</style>