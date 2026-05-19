<script setup lang="ts">
import type { ILending } from '~/types/lending';

defineProps<{
  lending: ILending,
}>()

</script>
<template>
    <div>
        <div @click="() => navigateTo(`/object/${lending.object.id}`)">
            <p>Object Emprunté</p>
            <img :src="lending.object.image" alt="Objet emprunté" />
            <p>{{ lending.object.name }}</p>
        </div>
        <div @click="() => navigateTo(`/user/${lending.borrower.id}`)">
            <p>Emprunteur</p>
            <img :src="lending.borrower.avatar" alt="Avatar de l'emprunteur" />
            <p>{{ lending.borrower.pseudo }}</p>
        </div>
        <div>
            <p>Durée du prêt</p>
            <p>Du {{ lending.startDate }} au {{ lending.endDate }}</p>
        </div>
        <button @click="() => navigateTo(`/lending/${lending.id}`)">Voir plus</button>
    </div>
</template>


<style>

</style>