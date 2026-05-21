<script setup lang="ts">
    const emit = defineEmits(['handleDelete', 'cancelEdit'])

    const handleDeletion = () => {
        emit('cancelEdit')
    }

    const handleDelete = () => {
        emit('handleDelete')
    }
</script>

<template>
    <PopIn :title="'Suppression du prêt'">

        <p>Êtes-vous sûr de vouloir supprimer ce prêt ?</p>
        <template #buttons>
            <ButtonForm :cancel-form="() => handleDeletion()" :validate-form="() => handleDelete()"/>
        </template>
    </PopIn>
</template>