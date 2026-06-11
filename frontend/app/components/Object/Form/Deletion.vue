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
    <PopIn :title="'Suppression de l\'objet'">
        <p>Êtes-vous sûr de vouloir supprimer cet objet ?</p>
        <template #buttons>
            <ButtonForm :cancel-form="() => handleDeletion()" :validate-form="() => handleDelete()"/>
        </template>
    </PopIn>
</template>