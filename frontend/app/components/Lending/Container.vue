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
        <div v-if="editMode">
            <LendingFormModification
                :startAt="lending.startAt"
                :endAt="lending.endAt"
                @handleSubmitUpdate="handleSubmitUpdate"
                @cancelEdit="editMode = false"
                :editMode="editMode"
            />
        </div>
        <div v-if="deleteAsked">
            <LendingFormDeletion @cancelEdit="() => deleteAsked = !deleteAsked" @handleDelete="handleDelete"/>
        </div>
        <ButtonOptions :actions="[
            { function: () => { editMode = true }, label: 'Modifier', svg: '/icons/edit.svg' },
            { function: () => { deleteAsked = true }, label: 'Supprimer', svg: '/icons/delete.svg' }
        ]" />
        <LendingInformations :lending="lending" />

    </div>
</template>


<style>

</style>