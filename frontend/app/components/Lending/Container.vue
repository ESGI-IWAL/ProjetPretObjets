<script setup lang="ts">
import type { IUpdateLendingDto } from '~/dto/lending/update.dto';
import type { ILending } from '~/types/lending';

    const props = defineProps<{
        lending: ILending
    }>()

    const editMode = ref(false)
    const deleteAsked = ref(false)

    const emit = defineEmits(['handleSubmitUpdate', 'handleDelete'])

    const handleSubmitUpdate = (updateLending : Omit<IUpdateLendingDto, "id">) => {
        emit('handleSubmitUpdate', {...updateLending, id: props.lending.id})
        editMode.value = false
    }

    const handleDelete = () => {
        emit('handleDelete')
        deleteAsked.value = false
    }
</script>

<template>
    <div>
       
        <ButtonOptions :actions="[
            { function: () => { editMode = true }, label: 'Modifier', svg: '/icons/edit.svg' },
            { function: () => { deleteAsked = true }, label: 'Supprimer', svg: '/icons/delete.svg' }
        ]" />
        <LendingInformations :lending="lending" />

    </div>
</template>


<style>

</style>